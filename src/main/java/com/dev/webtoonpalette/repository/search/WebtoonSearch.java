package com.dev.webtoonpalette.repository.search;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WebtoonSearch {
    Page<Tuple> searchList(PageRequestDTO pageRequestDTO);
    Page<Tuple> searchFavorite(List<Long> webtoonIds, PageRequestDTO pageRequestDTO, Long memberId);
}
