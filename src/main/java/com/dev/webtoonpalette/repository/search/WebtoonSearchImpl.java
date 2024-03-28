package com.dev.webtoonpalette.repository.search;

import com.dev.webtoonpalette.constant.Genre;
import com.dev.webtoonpalette.domain.QWebtoon;
import com.dev.webtoonpalette.domain.Webtoon;
import com.dev.webtoonpalette.dto.PageRequestDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class WebtoonSearchImpl extends QuerydslRepositorySupport implements WebtoonSearch {
    private final JPAQueryFactory queryFactory;

    public WebtoonSearchImpl(EntityManager entityManager) {
        super(Webtoon.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private QWebtoon webtoon = QWebtoon.webtoon;

    /**
     * 웹툰 검색 조회
     */
    @Override
    public Page<Webtoon> search(PageRequestDTO pageRequestDTO){
        JPQLQuery<Webtoon> query = queryFactory.selectFrom(webtoon)
                .where(keywordEq(pageRequestDTO.getSearchKeyword()));

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("fanCount").descending());
        this.getQuerydsl().applyPagination(pageable, query);
        List<Webtoon> list = query.fetch();

        long total = query.fetchCount();
        return new PageImpl<>(list,pageable,total);
    }

    /**
     * 웹툰 조회
     */
    @Override
    public Page<Webtoon> searchList(PageRequestDTO pageRequestDTO){

        JPQLQuery<Webtoon> query = queryFactory.selectFrom(webtoon)
                .where(updateDayEq(pageRequestDTO.getUpdateDay()),
                        genreEq(pageRequestDTO.getGenre()),
                        platformEq(pageRequestDTO.getPlatform()),
                        idEq(pageRequestDTO.getId()),
                        finEq(pageRequestDTO.isFin()));

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("fanCount").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Webtoon> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);

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
        if (keywordCond.equals("")){
            return webtoon.searchKeyword.like(keywordCond);
        }
        return webtoon.searchKeyword.contains(keywordCond);
    }

}