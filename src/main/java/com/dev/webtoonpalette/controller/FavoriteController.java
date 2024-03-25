package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.dto.FavoriteDTO;
import com.dev.webtoonpalette.repository.FavoriteRepository;
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

    @PostMapping("/")
    public FavoriteDTO addFavorite(@RequestBody FavoriteDTO favoriteDTO){
        log.info("----------addFavorite------------");
        log.info("favoriteDTO.getWebtoonId():"+favoriteDTO.getWebtoonId());
        log.info("favoriteDTO.getMemberId():"+favoriteDTO.getMemberId());
        return favoriteService.addFavorite(favoriteDTO);
    }

    //좋아요 취소
    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable("id")Long id){
        log.info("----------deleteFavorite------------");
        log.info("id:"+id);

        favoriteService.deleteFavorite(id);
    }

    @GetMapping("/")
    public Map<String, Long> getFavorite(FavoriteDTO favoriteDTO){
        log.info("----------getFavorite------------");
        log.info("favoriteDTO.getWebtoonId():"+favoriteDTO.getWebtoonId());
        log.info("favoriteDTO.getMemberId():"+favoriteDTO.getMemberId());
        Long result = favoriteService.getFavorite(favoriteDTO);

        return Map.of("result", result);
    }
    // 고객 별 좋아요 조회
    @GetMapping("/member/{memberId}")
    public int getMemberFavorite(){
        return 0;
    }

    // 웹툰 좋아요 수 조회
    @GetMapping("/webtton/{webtoonId}")
    public int getWebtoonFavorite(){
        return 0;
    }

    // 팔레트 좋아요 수 조회
    @GetMapping("/palette/{paletteId}")
    public int getPaletteFavorite(){
        return 0;
    }
}
