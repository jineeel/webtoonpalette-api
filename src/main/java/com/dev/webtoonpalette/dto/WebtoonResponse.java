package com.dev.webtoonpalette.dto;


import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.domain.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebtoonResponse {

    private Long id;

    private String title;

    private String author;

    private String url;

    private String img;

    private String platform;

    private String updateDay;

    private String rest;

    private String adult;

    private Genre genre;

    private Long likeCount;

    private String searchKeyword;

    private int fanCount;

    public WebtoonResponse(Webtoon webtoon) {
        this.id = webtoon.getId();
        this.title = webtoon.getTitle();
        this.author = webtoon.getAuthor();
        this.url = webtoon.getUrl();
        this.img = webtoon.getImg();
        this.platform = webtoon.getPlatform();
        this.updateDay = webtoon.getUpdateDay();
        this.rest = webtoon.getRest();
        this.adult = webtoon.getAdult();
        this.genre = webtoon.getGenre();
        this.likeCount = webtoon.getLikeCount();
        this.searchKeyword = webtoon.getSearchKeyword();
        this.fanCount = webtoon.getFanCount();
    }
}
