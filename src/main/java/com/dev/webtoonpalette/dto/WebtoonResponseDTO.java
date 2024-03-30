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
public class WebtoonResponseDTO {
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

    private Long favoriteId;

}
