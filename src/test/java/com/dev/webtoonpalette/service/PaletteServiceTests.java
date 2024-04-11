package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.constant.PaletteRole;
import com.dev.webtoonpalette.dto.PaletteDTO;
import com.dev.webtoonpalette.dto.PaletteRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class PaletteServiceTests {

    @Autowired
    PaletteService paletteService;

    @Test
    public void testCreate() {

        Long memberId = 26L;

        PaletteRequestDTO paletteRequestDTO =
                PaletteRequestDTO.builder()
                        .title("팔레트리스트 title")
                        .content("팔레트리스트 content")
                        .memberId(70L)
                        .dueDate(LocalDate.now())
                        .role(PaletteRole.PUBLIC)
                        .build();

        PaletteDTO paletteDTO = paletteService.createPalette(paletteRequestDTO);

        Assertions.assertThat(paletteDTO);

        log.info(paletteDTO);
    }
}
