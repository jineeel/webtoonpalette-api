package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.dto.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FavoriteService {

    FavoriteDTO addFavorite(FavoriteDTO favoriteDTO);
    Long getFavorite(FavoriteDTO favoriteDTO);
    void deleteFavorite(Long id);
    PageResponseDTO<WebtoonDTO> getMemberFavoriteWebtoon(Long memberId, PageRequestDTO pageRequestDTO);

    default FavoriteDTO entityToDTO(Favorite favorite){

        FavoriteDTO favoriteDTO = FavoriteDTO.builder()
                .id(favorite.getId())
                .memberId(favorite.getMember().getId())
                .webtoonId(favorite.getWebtoon().getId())
                .build();

        return favoriteDTO;
    }
}
