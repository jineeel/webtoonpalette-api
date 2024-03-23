package com.dev.webtoonpalette.dto;

import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.constant.Role;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.dto.oauth.CustomOAuth2User;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Getter
@Setter
@ToString
@Log4j2
@NoArgsConstructor
public class MemberDTO{

    private Long id;

    private String username;

    private String nickname;

    private Role role;

    private String social;

    private String providerId;

    private String uploadFileName;

    private MultipartFile file;

    private boolean changeImage;

    private List<Genre> genreNames = new ArrayList<>();

    @Builder
    public MemberDTO(Long id, String username, String nickname, Role role, String social, String providerId, String uploadFileName, List<Genre> genreNames) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.social = social;
        this.providerId = providerId;
        this.uploadFileName = uploadFileName;
        this.genreNames = genreNames;
    }

    public Map<String, Object> getClaims(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        dataMap.put("username", username);
        dataMap.put("nickname", nickname);
        dataMap.put("role", role);
        dataMap.put("social", social);
        dataMap.put("providerId", providerId);
        dataMap.put("uploadFileName", uploadFileName);
        dataMap.put("genreNames",genreNames);
        return dataMap;
    }

}
