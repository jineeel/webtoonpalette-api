package com.dev.webtoonpalette.dto;

import com.dev.webtoonpalette.constant.PaletteRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaletteRequestDTO {

    private Long memberId;

    private String title;

    private String content;

    private LocalDate dueDate;

    private PaletteRole role;
}
