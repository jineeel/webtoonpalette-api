package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.MemberImage;
import com.dev.webtoonpalette.dto.MemberDTO;
import com.dev.webtoonpalette.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberImageService memberImageService;

    /**
     * 회원 정보 가져오는 메서드
     */
    @Override
    public MemberDTO getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent()){
            MemberDTO memberDTO = entityToDTO(member.get());
            return memberDTO;
        }
        return null;
    }

    /**
     * 회원 정보를 수정하는 메서드
     */
    @Override
    public MemberDTO modify(MemberDTO memberDTO, String uploadFileName, String oldFileName, boolean isChangeImage) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow();
        member.changeNickname(memberDTO.getNickname());
        member.clearGenre();

        List<Genre> genreNames = memberDTO.getGenreNames();
        genreNames.forEach(member::addGenre);

        boolean uploadNameNull = uploadFileName.equals("null");

        if(isChangeImage){
            // 사용자가 이전 프로필 사진이 없는 상황에서 새로 등록할 경우
            if(!uploadNameNull && oldFileName == null){
                MemberImage memberImage = memberImageService.saveMemberImage(member, uploadFileName);
                member.changeMemberImage(memberImage);
            }
            //사용자가 이전 프로필 사진이 있는 상황에서 사진을 변경할 경우 업데이트
            else if (!uploadNameNull && oldFileName != null){
                MemberImage memberImage = memberImageService.modifyMemberImage(member.getMemberImage().getId(), uploadFileName);
                member.changeMemberImage(memberImage);
            }
            //사용자가 프로필 사진을 삭제한 경우
            else if(uploadNameNull){
                member.changeMemberImage(null);
                memberRepository.save(member);
                memberImageService.deleteMemberImage(oldFileName);
            }
        }

        memberRepository.save(member);
        MemberDTO resultDTO = entityToDTO(member);

        return resultDTO;
    }
}
