package com.example.test_web.domain.board.repository;

import com.example.test_web.domain.board.dto.BoardInfo;
import com.example.test_web.domain.board.entity.Board;
import static com.example.test_web.domain.board.entity.QBoard.board;

import static com.example.test_web.domain.user.entity.QUserInfo.userInfo;

import com.example.test_web.domain.board.repository.dto.BoardOrderType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import static com.querydsl.core.types.Projections.fields;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardInfo> findBoardInfos(Pageable pageable) {
        return queryFactory.select(fields(BoardInfo.class,
                    board.id.as("id"),
                    board.title.as("title"),
                    board.content.as("content"),
                    board.view.as("view"),
                    board.registDate.as("registDate"),
                    board.user.userId.as("userId")
                ))
                .from(board)
                .leftJoin(board.user,userInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(boardOrderBy(pageable))
                .fetch();
    }

    /**
     * 정렬 조건을 생성합니다.
     * 정렬 조건은 최신순, 인기순을 지원합니다.
     */
    private OrderSpecifier<?>[] boardOrderBy(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        pageable.getSort().forEach(order -> BoardOrderType.getOrderSpecifier(order).ifPresent(orderSpecifiers::add));
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
