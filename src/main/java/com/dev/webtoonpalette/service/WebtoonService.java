package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.WebtoonDTO;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WebtoonService {
    PageResponseDTO<WebtoonDTO> getListMember(PageRequestDTO pageRequestDTO);
    PageResponseDTO<WebtoonDTO> getList(PageRequestDTO pageRequestDTO);
    WebtoonDTO get(Long id);

    default WebtoonDTO entityToDto(Webtoon webtoon){
        WebtoonDTO webtoonDTO = WebtoonDTO.builder()
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
        return webtoonDTO;
    }

    default WebtoonDTO entityToDtoMember(Webtoon webtoon, Long favoriteId){
        WebtoonDTO webtoonDTO = WebtoonDTO.builder()
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
        return webtoonDTO;
    }

}

