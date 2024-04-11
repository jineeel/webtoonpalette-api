package com.dev.webtoonpalette.dto;

import com.dev.webtoonpalette.constant.PaletteRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaletteDTO {

    private Long id;

    private String title;

    private String content;

    private PaletteRole role;

    private LocalDate dueDate;
}
