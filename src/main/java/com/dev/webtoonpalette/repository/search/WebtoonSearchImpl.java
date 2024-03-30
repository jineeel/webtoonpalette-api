package com.dev.webtoonpalette.repository.search;

import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.domain.Favorite;
import com.dev.webtoonpalette.domain.QFavorite;
import com.dev.webtoonpalette.domain.QWebtoon;
import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.dev.webtoonpalette.repository.WebtoonRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class WebtoonSearchImpl extends QuerydslRepositorySupport implements WebtoonSearch {
    private final JPAQueryFactory queryFactory;

    public WebtoonSearchImpl(EntityManager entityManager) {
        super(Webtoon.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private QWebtoon webtoon = QWebtoon.webtoon;
    private QFavorite favorite = QFavorite.favorite;

    /**
     * 조건에 따른 웹툰 리스트 조회
     */
    @Override
    public Page<Tuple> searchList(PageRequestDTO pageRequestDTO){

        JPQLQuery<Tuple> query = queryFactory.select(
                        webtoon,
                        favorite.id
                )
                .from(webtoon)
                .leftJoin(favorite)
                .on(
                        favorite.webtoon.id.eq(webtoon.id)
                        .and(pageRequestDTO.getMemberId() != null ? favorite.member.id.eq(pageRequestDTO.getMemberId()) : favorite.member.isNull())
                )
                .where(
                        updateDayEq(pageRequestDTO.getUpdateDay()),
                        genreEq(pageRequestDTO.getGenre()),
                        platformEq(pageRequestDTO.getPlatform()),
                        idEq(pageRequestDTO.getId()),
                        finEq(pageRequestDTO.isFin()),
                        keywordEq(pageRequestDTO.getSearchKeyword())
                );

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("fanCount").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Tuple> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<Tuple> searchFavorite(List<Long> webtoonIds, PageRequestDTO pageRequestDTO, Long memberId) {

        JPQLQuery<Tuple> query = queryFactory.select(webtoon, favorite.id)
                .from(webtoon)
                .join(favorite).on(webtoon.id.eq(favorite.webtoon.id))
                .where(webtoon.id.in(webtoonIds),
                       favorite.member.id.eq(memberId),
                       genreEq(pageRequestDTO.getGenre()))
                .orderBy(favorite.id.desc());

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize());

        query = this.getQuerydsl().applyPagination(pageable, query);

        List<Tuple> resultList = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);

    }

    private BooleanExpression updateDayEq(String updateDayCond) {
        if (updateDayCond == null) {
            return null;
        }
        return webtoon.updateDay.eq(updateDayCond);
    }


    private BooleanExpression genreEq(Genre genreCond) {
        if (genreCond == null || genreCond == Genre.ALL) {
            return null;
        }
        return webtoon.genre.eq(genreCond);
    }

    private BooleanExpression platformEq(String platformCond) {
        if (platformCond == null) {
            return null;
        }
        return webtoon.platform.eq(platformCond);
    }

    private BooleanExpression finEq(boolean finCond) {
        if (finCond) {
            return null;
        }

        return webtoon.updateDay.notLike("finished");
    }

    private BooleanExpression idEq(Long idCond) {
        if (idCond == null) {
            return null;
        }
        return webtoon.id.ne(idCond);
    }

    private BooleanExpression keywordEq(String keywordCond){
        if (keywordCond == null){
            return null;
        }
        else if(keywordCond.equals("")){
            return webtoon.searchKeyword.like(keywordCond);
        }

        return webtoon.searchKeyword.contains(keywordCond);
    }

}