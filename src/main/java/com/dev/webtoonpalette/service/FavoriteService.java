package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.MemberImage;
import com.dev.webtoonpalette.dto.FavoriteDTO;
import com.dev.webtoonpalette.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FavoriteService {

    FavoriteDTO addFavorite(FavoriteDTO favoriteDTO);
    Long getFavorite(FavoriteDTO favoriteDTO);
    void deleteFavorite(Long id);

    default FavoriteDTO entityToDTO(Favorite favorite){

        FavoriteDTO favoriteDTO = FavoriteDTO.builder()
                .id(favorite.getId())
                .memberId(favorite.getMember().getId())
                .webtoonId(favorite.getWebtoon().getId())
                .build();

        return favoriteDTO;
    }

}
