package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.repository.search.WebtoonSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long>, WebtoonSearch {
}
