package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.domain.Palette;
import com.dev.webtoonpalette.dto.PaletteDTO;
import com.dev.webtoonpalette.dto.PaletteRequestDTO;
import com.dev.webtoonpalette.repository.MemberRepository;
import com.dev.webtoonpalette.repository.PaletteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaletteServiceImpl implements PaletteService{

    private final PaletteRepository paletteRepository;

    private final MemberRepository memberRepository;

    @Override
    public PaletteDTO createPalette(PaletteRequestDTO paletteRequestDTO) {

        Optional<Member> member = memberRepository.findById(paletteRequestDTO.getMemberId());

        Palette palette = Palette.builder()
                .title(paletteRequestDTO.getTitle())
                .content(paletteRequestDTO.getContent())
                .member(member.orElseThrow())
                .dueDate(paletteRequestDTO.getDueDate())
                .role(paletteRequestDTO.getRole())
                .build();

        return entityToDTO(paletteRepository.save(palette));
    }

    @Override
    public List<PaletteDTO> getPalette(Long memberId) {
        List<Palette> palettes = paletteRepository.findByMemberId(memberId);

        return palettes.stream().map(this::entityToDTO).toList();
    }
}
