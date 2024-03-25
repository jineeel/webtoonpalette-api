package com.dev.webtoonpalette.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDTO {

    private Long id;

    private Long memberId;

    private Long webtoonId;

    public FavoriteDTO(Long memberId, Long webtoonId) {
        this.memberId = memberId;
        this.webtoonId = webtoonId;
    }
}
