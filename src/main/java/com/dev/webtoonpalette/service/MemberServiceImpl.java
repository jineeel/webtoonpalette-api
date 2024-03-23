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

    @Override
    public MemberDTO getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent()){
            MemberDTO memberDTO = entityToDTO(member.get());
            return memberDTO;
        }
        return null;
    }

    @Override
    public MemberDTO modify(MemberDTO memberDTO, String uploadFileName, String oldFileName, boolean isChangeImage) {

        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow();
        member.changeNickname(memberDTO.getNickname());
        member.clearGenre();

        List<Genre> genreNames = memberDTO.getGenreNames();
        genreNames.forEach(member::addGenre);

        boolean uploadNameNull = uploadFileName.equals("null");

        if(isChangeImage){

            if(!uploadNameNull && oldFileName == null){
                MemberImage memberImage = memberImageService.saveMemberImage(member, uploadFileName);
                member.changeMemberImage(memberImage);
            }
            else if (!uploadNameNull && oldFileName != null){
                MemberImage memberImage = memberImageService.modifyMemberImage(member.getMemberImage().getId(), uploadFileName);
                member.changeMemberImage(memberImage);
            }
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
