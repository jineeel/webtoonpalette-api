package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.MemberImage;
import com.dev.webtoonpalette.dto.MemberDTO;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    MemberDTO getMember(Long id);
    MemberDTO modify(MemberDTO memberDTO, String uploadFileName, String oldFileName, boolean isChangeImage);

    default MemberDTO entityToDTO(Member member){
        MemberDTO memberDTO = MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .role(member.getRole())
                .social(member.getSocial())
                .providerId(member.getProviderId())
                .genreNames(member.getLikeGenreList())
                .build();

        MemberImage memberImage = member.getMemberImage();

        if(memberImage == null){
            return memberDTO;
        }

        memberDTO.setUploadFileName(memberImage.getFileName());

        return memberDTO;
    }
}
