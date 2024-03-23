package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.MemberImage;
import com.dev.webtoonpalette.repository.MemberImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberImageService {

    private final MemberImageRepository memberImageRepository;

    public MemberImage saveMemberImage(Member member, String uploadFileName) {
        MemberImage memberImage = MemberImage.builder()
                .fileName(uploadFileName)
                .member(member).build();

         return memberImageRepository.save(memberImage);

    }

    public MemberImage modifyMemberImage(Long id, String uploadFileName ){
        MemberImage memberImage = memberImageRepository.findById(id).orElseThrow();
        memberImage.changeMemberImage(uploadFileName);

        return memberImageRepository.save(memberImage);
    }

    public void deleteMemberImage(String oldFileName){

        memberImageRepository.deleteByFileName(oldFileName);

    }


}
