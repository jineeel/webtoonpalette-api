package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.dto.PageResponseDTO;
import com.dev.webtoonpalette.dto.WebtoonResponse;
import com.dev.webtoonpalette.repository.WebtoonRepository;
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
     * 조건에 따른 웹툰 List를 반환 메서드
     */
    @Override
    public PageResponseDTO<WebtoonResponse> getList(PageRequestDTO pageRequestDTO){
        Page<Webtoon> result = webtoonRepository.searchList(pageRequestDTO);

        List<WebtoonResponse> dtoList = result
                .get()
                .map(webtoon -> entityToDto(webtoon))
                .collect(Collectors.toList());

        PageResponseDTO<WebtoonResponse> responseDTO =
                PageResponseDTO.<WebtoonResponse>withAll()
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
    public WebtoonResponse get(Long id){
        Webtoon webtoon = webtoonRepository.findById(id).orElseThrow();
        return entityToDto(webtoon);
    }


    /**
     * 검색 기능을 통해 검색된 웹툰 리스트를 반환하는 메서드
     */
    @Override
    public PageResponseDTO<WebtoonResponse> getSearch(PageRequestDTO pageRequestDTO) {
        Page<Webtoon> result = webtoonRepository.search(pageRequestDTO);

        List<WebtoonResponse> dtoList = result
                .get()
                .map(webtoon -> entityToDto(webtoon))
                .collect(Collectors.toList());

        PageResponseDTO<WebtoonResponse> responseDTO =
                PageResponseDTO.<WebtoonResponse>withAll()
                        .dtoList(dtoList)
                        .pageRequestDTO(pageRequestDTO)
                        .total(result.getTotalElements())
                        .build();

        return responseDTO;
    }

}
