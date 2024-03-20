package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.repository.search.WebtoonSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long>, WebtoonSearch {
}
