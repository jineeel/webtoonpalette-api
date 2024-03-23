package com.dev.webtoonpalette.domain;


import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "likeGenreList")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // email

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String social;

    @Column(unique = true)
    private String providerId;

    @OneToOne
    @JoinColumn(name="member_image_id")
    private MemberImage memberImage;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Genre> likeGenreList = new ArrayList<>();

    public void addGenre(Genre genre){
        likeGenreList.add(genre);
    }

    public void clearGenre(){
        likeGenreList.clear();
    }

    @Builder
    public Member(Long id, String username, String nickname, Role role, String social, String providerId){
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.social = social;
        this.providerId = providerId;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeMemberImage(MemberImage memberImage){
        this.memberImage = memberImage;
    }

}
