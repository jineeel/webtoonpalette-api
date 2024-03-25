package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.FavoriteDTO;
import com.dev.webtoonpalette.repository.FavoriteRepository;
import com.dev.webtoonpalette.repository.MemberRepository;
import com.dev.webtoonpalette.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;

    @Override
    public FavoriteDTO addFavorite(FavoriteDTO favoriteDTO) {
        Member member = memberRepository.findById(favoriteDTO.getMemberId()).orElseThrow();

        Webtoon webtoon = webtoonRepository.findById(favoriteDTO.getWebtoonId()).orElseThrow();

        Favorite favorite = Favorite.builder()
                .member(member)
                .webtoon(webtoon)
                .build();

        Favorite result = favoriteRepository.save(favorite);

        return entityToDTO(result);
    }

    @Override
    public Long getFavorite(FavoriteDTO favoriteDTO) {

        Optional<Favorite> favorite = favoriteRepository.findByMemberIdAndWebtoonId(favoriteDTO.getMemberId(), favoriteDTO.getWebtoonId());

        if(favorite.isPresent()){
            return favorite.get().getId();
        }else{
            return 0L;
        }
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }


}
