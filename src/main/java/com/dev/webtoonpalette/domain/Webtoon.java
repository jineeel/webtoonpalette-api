package com.dev.webtoonpalette.domain;


import com.dev.webtoonpalette.constant.Genre;
import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="webtoon")
public class Webtoon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String url;

    private String img;

    private String platform;

    private String updateDay;

    private String rest;

    private String adult;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Long likeCount;

    private String searchKeyword;

    private int fanCount;

    @Builder
    public Webtoon(String title, String author, String url, String img, String platform, String updateDay,
                   String adult, String searchKeyword, String rest) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.img = img;
        this.platform = platform;
        this.updateDay = updateDay;
        this.rest = rest;
        this.adult = adult;
        this.searchKeyword = searchKeyword;
    }

    public void changeFanCount(int fanCount){
        this.fanCount = fanCount;
    }
}
