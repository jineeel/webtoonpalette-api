package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import com.dev.webtoonpalette.dto.WebtoonResponse;
import com.dev.webtoonpalette.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/api/webtoon")
@RequiredArgsConstructor
@RestController
public class WebtoonController {

    private final WebtoonService webtoonService;

    /**
     * 웹툰 리스트
     */
    @GetMapping("/list")
    public PageResponseDTO<WebtoonResponse> getList(PageRequestDTO pageRequestDTO){
        return webtoonService.getList(pageRequestDTO);
    }

    /**
     * 웹툰 상세 페이지
     */
    @GetMapping("/{id}")
    public WebtoonResponse get(@PathVariable("id") Long id){
        return webtoonService.get(id);
    }


}