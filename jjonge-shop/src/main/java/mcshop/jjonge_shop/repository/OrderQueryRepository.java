//package mcshop.jjonge_shop.repository;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import mcshop.jjonge_shop.domain.Order;
//import mcshop.jjonge_shop.domain.OrderStatus;
//import mcshop.jjonge_shop.domain.QMember;
//import mcshop.jjonge_shop.domain.QOrder;
//import mcshop.jjonge_shop.dto.OrderSearch;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class OrderQueryRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    /**
//     * 주문 검색 조건을 기반으로 동적 쿼리 실행
//     */
//    public List<Order> search(OrderSearch orderSearch) {
//        QOrder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return queryFactory
//                .selectFrom(order)
//                .join(order.member, member)              // 회원과 조인
//                .where(
//                        statusEq(orderSearch.getOrderStatus()),   // 주문 상태 조건
//                        nameLike(orderSearch.getMemberName())     // 회원 이름 조건
//                )
//                .limit(1000) // 최대 1000건만 조회
//                .fetch();
//    }
//
//    /**
//     * 주문 상태 조건이 존재하면 추가
//     */
//    private BooleanExpression statusEq(OrderStatus status) {
//        return status != null ? QOrder.order.status.eq(status) : null;
//    }
//
//    /**
//     * 회원 이름 조건이 존재하면 추가 (like 검색)
//     */
//    private BooleanExpression nameLike(String memberName) {
//        return memberName != null && !memberName.isBlank()
//                ? QOrder.order.member.name.contains(memberName)
//                : null;
//    }
//}