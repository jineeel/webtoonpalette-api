package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.WebtoonDTO;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import com.dev.webtoonpalette.repository.WebtoonRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class WebtoonServiceImpl implements WebtoonService{

    private final WebtoonRepository webtoonRepository;


    /**
     * 조건에 따른 웹툰 List를 반환 하는 메서드 - 비회원
     */
    @Override
    public PageResponseDTO<WebtoonDTO> getList(PageRequestDTO pageRequestDTO){
        Page<Webtoon> result = webtoonRepository.searchList(pageRequestDTO);

        List<WebtoonDTO> dtoList = result.get()
                .map(webtoon -> entityToDto(webtoon)).collect(Collectors.toList());

        PageResponseDTO<WebtoonDTO> responseDTO =
                PageResponseDTO.<WebtoonDTO>withAll()
                        .dtoList(dtoList)
                        .pageRequestDTO(pageRequestDTO)
                        .total(result.getTotalElements())
                        .build();

        return responseDTO;
    }

    /**
     * 조건에 따른 웹툰 List를 반환하는 메서드 - 회원
     */
    @Override
    public PageResponseDTO<WebtoonDTO> getListMember(PageRequestDTO pageRequestDTO){
        Page<Tuple> result = webtoonRepository.searchListMember(pageRequestDTO);

        List<WebtoonDTO> dtoList = result.getContent().stream()
                .map(tuple -> {
                    Webtoon webtoon = tuple.get(0, Webtoon.class);
                    Long favoriteId = tuple.get(1, Long.class);
                    return entityToDtoMember(webtoon,favoriteId);
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

    /**
     * 웹툰 하나만 조회하는 메서드
     */
    @Override
    public WebtoonDTO get(Long id){
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow();
        return entityToDto(webtoon);
    }


}
