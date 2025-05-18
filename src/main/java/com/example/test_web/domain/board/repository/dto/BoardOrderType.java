package com.example.test_web.domain.board.repository.dto;

import static com.example.test_web.domain.board.entity.QBoard.board;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public enum BoardOrderType {
    LATEST(BoardOrderSpecifier.Latest.getInstance()),
    POPULAR(BoardOrderSpecifier.Popular.getInstance());

    private final OrderSpecifier<? extends Comparable<?>> orderSpecifier;

    public static Optional<OrderSpecifier<? extends Comparable<?>>> getOrderSpecifier(Sort.Order order) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(order.getProperty()))
                .findFirst()
                .map(orderType -> orderType.orderSpecifier);
    }

    private static class BoardOrderSpecifier {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        static class Latest {
            static class LatestInstanceHolder {
                private static final OrderSpecifier<? extends Comparable<?>> INSTANCE = new OrderSpecifier<>(Order.DESC, board.registDate);
            }

            static OrderSpecifier<? extends Comparable<?>> getInstance() {
                return LatestInstanceHolder.INSTANCE;
            }
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        static class Popular {
            static class PopularInstanceHolder {
                private static final OrderSpecifier<? extends Comparable<?>> INSTANCE = new OrderSpecifier<>(Order.DESC, board.view);
            }

            static OrderSpecifier<? extends Comparable<?>> getInstance() {
                return PopularInstanceHolder.INSTANCE;
            }
        }
    }
}
