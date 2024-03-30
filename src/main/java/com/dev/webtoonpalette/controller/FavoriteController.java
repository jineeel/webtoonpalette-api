package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.dto.*;
import com.dev.webtoonpalette.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 좋아요 추가
     */
    @PostMapping("/")
    public FavoriteDTO addFavorite(@RequestBody FavoriteDTO favoriteDTO){
        return favoriteService.addFavorite(favoriteDTO);
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable("id")Long id){

        favoriteService.deleteFavorite(id);
    }

    /**
     * 좋아요 조회
     */
    @GetMapping("/")
    public Map<String, Long> getFavorite(FavoriteDTO favoriteDTO){

        Long result = favoriteService.getFavorite(favoriteDTO);

        return Map.of("result", result);
    }

    /**
     * 회원이 좋아요 누른 웹툰 조회
     */
    @GetMapping("/{memberId}")
    public PageResponseDTO<WebtoonResponseDTO> getMemberFavoriteWebtoon(@PathVariable("memberId") Long memberId, PageRequestDTO pageRequestDTO){
        return favoriteService.getMemberFavoriteWebtoon(memberId, pageRequestDTO);
    }

    //TODO 웹툰 좋아요 수 조회
    @GetMapping("/webtton/{webtoonId}")
    public int getWebtoonFavorite(){
        return 0;
    }

    //TODO 팔레트 좋아요 조회
    @GetMapping("/palette/{paletteId}")
    public int getPaletteFavorite(){
        return 0;
    }
}
