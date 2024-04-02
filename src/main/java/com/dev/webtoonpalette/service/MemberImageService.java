package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.MemberImage;
import com.dev.webtoonpalette.repository.MemberImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberImageService {

    private final MemberImageRepository memberImageRepository;

    /**
     * 프로필 사진 이미지 이름을 새로 저장하는 메서드
     */
    public MemberImage saveMemberImage(Member member, String uploadFileName) {
        MemberImage memberImage = MemberImage.builder()
                .fileName(uploadFileName)
                .member(member).build();

         return memberImageRepository.save(memberImage);

    }

    /**
     * 프로필 사진 이미지 이름을 수정하는 메서드
     */
    public MemberImage modifyMemberImage(Long id, String uploadFileName ){
        MemberImage memberImage = memberImageRepository.findById(id).orElseThrow();
        memberImage.changeMemberImage(uploadFileName);

        return memberImageRepository.save(memberImage);
    }

    /**
     * 프로필 사진 이미지 이름을 삭제하는 메서드
     */
    public void deleteMemberImage(String oldFileName){

        memberImageRepository.deleteByFileName(oldFileName);

    }


}
