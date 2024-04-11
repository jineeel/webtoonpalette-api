package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.Palette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaletteRepository extends JpaRepository<Palette, Long> {

    @Query("select p from Palette p where p.member.id = :memberId")
    List<Palette> findByMemberId(Long memberId);

}
