package com.dev.webtoonpalette.repository.search;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface WebtoonSearch {
    Page<Webtoon> search (PageRequestDTO pageRequestDTO);
    Page<Webtoon> searchList(PageRequestDTO pageRequestDTO);
}
