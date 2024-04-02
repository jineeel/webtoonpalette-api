package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.*;
import com.dev.webtoonpalette.repository.FavoriteRepository;
import com.dev.webtoonpalette.repository.MemberRepository;
import com.dev.webtoonpalette.repository.WebtoonRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonService webtoonService;

    /**
     * 좋아요를 추가하는 메서드
     */
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
    /**
     * 좋아요 조회하는 메서드
     */
    @Override
    public Long getFavorite(FavoriteDTO favoriteDTO) {

        Optional<Favorite> favorite = favoriteRepository.findByMemberIdAndWebtoonId(favoriteDTO.getMemberId(), favoriteDTO.getWebtoonId());
        log.info("ㅇdpd엥~~~~~~~~~");
        if(favorite.isPresent()){
            log.info("dndndndndssssssssssssss");
            return favorite.get().getId();
        }else{
            return 0L;
        }
//        return 0L;
    }

    /**
     * 좋아요를 삭제하는 메서드
     */
    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    /**
     * 회원이 좋아요를 누른 웹툰 리스트를 조회하는 메서드
     */
    @Override
    public PageResponseDTO<WebtoonDTO> getMemberFavoriteWebtoon(Long memberId, PageRequestDTO pageRequestDTO) {
        List<Favorite> favoriteList = favoriteRepository.findByMemberId(memberId);

        List<Long> webtoonIds = favoriteList.stream()
                .map(favorite -> favorite.getWebtoon().getId())
                .collect(Collectors.toList());

        pageRequestDTO.setSize(10);

        Page<Tuple> result = webtoonRepository.searchFavorite(webtoonIds, pageRequestDTO, memberId);

        List<WebtoonDTO> dtoList = result.getContent().stream()
                .map(tuple -> {
                    Webtoon webtoon = tuple.get(0, Webtoon.class);
                    Long favoriteId = tuple.get(1, Long.class);
                    return webtoonService.entityToDtoMember(webtoon,favoriteId);
                })
                .collect(Collectors.toList());

        PageResponseDTO<WebtoonDTO> responseDTO =
                PageResponseDTO.<WebtoonDTO>withAll()
                        .dtoList(dtoList)
                        .pageRequestDTO(pageRequestDTO)
                        .total(result.getTotalElements())
                        .build();

        return responseDTO;

    }

}
