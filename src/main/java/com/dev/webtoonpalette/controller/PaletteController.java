package com.dev.webtoonpalette.controller;

import com.dev.webtoonpalette.dto.PaletteDTO;
import com.dev.webtoonpalette.dto.PaletteRequestDTO;
import com.dev.webtoonpalette.service.PaletteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/palette")
@Log4j2
public class PaletteController {

    private final PaletteService paletteService;

    @PostMapping("/")
    public ResponseEntity<?> createPalette(@RequestBody PaletteRequestDTO paletteRequestDTO) {

        return ResponseEntity.ok(paletteService.createPalette(paletteRequestDTO));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PaletteDTO>> getPalette(@PathVariable(name = "memberId") Long memberId) {
        log.info("/////////// " + memberId);

        return ResponseEntity.ok(paletteService.getPalette(memberId));
    }
}
