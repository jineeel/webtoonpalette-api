package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import com.dev.webtoonpalette.dto.WebtoonResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WebtoonService {
    PageResponseDTO<WebtoonResponse> getList(PageRequestDTO pageRequestDTO);

    WebtoonResponse get(Long id);

    default WebtoonResponse entityToDto(Webtoon webtoon){
        WebtoonResponse webtoonResponse = WebtoonResponse.builder()
                .id(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor())
                .url(webtoon.getUrl())
                .img(webtoon.getImg())
                .platform(webtoon.getPlatform())
                .updateDay(webtoon.getUpdateDay())
                .rest(webtoon.getRest())
                .adult(webtoon.getAdult())
                .genre(webtoon.getGenre())
                .likeCount(webtoon.getLikeCount())
                .searchKeyword(webtoon.getSearchKeyword())
                .fanCount(webtoon.getFanCount())
                .build();
        return webtoonResponse;
    }

}

