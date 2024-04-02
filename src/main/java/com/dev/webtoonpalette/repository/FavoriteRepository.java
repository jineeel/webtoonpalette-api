package com.dev.webtoonpalette.repository;

import com.dev.webtoonpalette.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select f from Favorite f where f.member.id = :memberId and f.webtoon.id = :webtoonId")
    Optional<Favorite> findByMemberIdAndWebtoonId(Long memberId, Long webtoonId);

    @Query("select f from Favorite f where f.member.id = :memberId")
    List<Favorite> findByMemberId(Long memberId);

}
