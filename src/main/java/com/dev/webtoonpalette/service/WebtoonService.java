package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.WebtoonResponseDTO;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WebtoonService {
    PageResponseDTO<WebtoonResponseDTO> getList(PageRequestDTO pageRequestDTO);
    WebtoonResponseDTO get(Long id);

    default WebtoonResponseDTO entityToDto(Webtoon webtoon){
        WebtoonResponseDTO webtoonResponseDTO = WebtoonResponseDTO.builder()
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
        return webtoonResponseDTO;
    }

    default WebtoonResponseDTO entityToDtoFavoriteId(Webtoon webtoon, Long favoriteId){
        WebtoonResponseDTO webtoonResponseDTO = WebtoonResponseDTO.builder()
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
                .favoriteId(favoriteId)
                .build();
        return webtoonResponseDTO;
    }

}

