package com.dev.webtoonpalette.domain;

import com.dev.webtoonpalette.constant.PaletteRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Palette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDate dueDate;

    @Enumerated(value = EnumType.STRING)
    @Column
    private PaletteRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "webtoon_id")
    private List<Webtoon> webtoonList;
}
