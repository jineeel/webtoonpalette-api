package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberIdAndWebtoonId(Long memberId, Long webtoonId);
}
