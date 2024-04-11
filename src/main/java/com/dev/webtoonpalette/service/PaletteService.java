package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Palette;
import com.dev.webtoonpalette.dto.PaletteDTO;
import com.dev.webtoonpalette.dto.PaletteRequestDTO;

import java.util.List;

public interface PaletteService {

    PaletteDTO createPalette(PaletteRequestDTO paletteRequestDTO);

    List<PaletteDTO> getPalette(Long memberId);

    default PaletteDTO entityToDTO(Palette palette) {

        return PaletteDTO.builder()
                .id(palette.getId())
                .title(palette.getTitle())
                .content(palette.getContent())
                .role(palette.getRole())
                .dueDate(palette.getDueDate())
                .build();
    }
}
