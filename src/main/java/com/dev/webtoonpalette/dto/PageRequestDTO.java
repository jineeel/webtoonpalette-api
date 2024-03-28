package com.dev.webtoonpalette.dto;

import com.dev.webtoonpalette.constant.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 60;

    private String updateDay;

    private Genre genre;

    private boolean fin;

    private String platform;

    private String value;

    private Long id;

    private String searchKeyword;
}
