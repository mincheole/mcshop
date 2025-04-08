//package mcshop.jjonge_shop.repository;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import mcshop.jjonge_shop.domain.OrderStatus;
//import mcshop.jjonge_shop.domain.QMember;
//import mcshop.jjonge_shop.domain.QOrder;
//import mcshop.jjonge_shop.dto.OrderDto;
//import mcshop.jjonge_shop.dto.OrderSearch;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * 사용자 정의 쿼리 구현체
// * - OrderRepositoryCustom 인터페이스를 구현
// * - Querydsl을 사용하여 동적 쿼리 작성
// */
//@Repository
//@RequiredArgsConstructor
//public class OrderRepositoryImpl implements OrderRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    /**
//     * 검색 조건을 기반으로 주문 리스트 조회
//     */
//    @Override
//    public List<OrderDto> customSearch(OrderSearch orderSearch) {
//        QOrder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return queryFactory
//                .select(new QOrderDto(
//                        order.id,
//                        member.realName,
//                        order.orderDate,
//                        order.status
//                ))
//                .from(order)
//                .join(order.member, member)               // 회원과 조인
//                .where(
//                        statusEq(orderSearch.getOrderStatus()),    // 주문 상태 필터링
//                        nameLike(orderSearch.getMemberName())      // 회원 이름 필터링
//                )
//                .limit(1000) // 성능 보호: 최대 1000건 제한
//                .fetch();
//    }
//
//    /**
//     * 주문 상태가 null이 아니면 조건 추가
//     */
//    private BooleanExpression statusEq(OrderStatus status) {
//        return status != null ? QOrder.order.status.eq(status) : null;
//    }
//
//    /**
//     * 회원 이름이 비어 있지 않으면 조건 추가 (부분 일치 검색)
//     */
//    private BooleanExpression nameLike(String memberName) {
//        return memberName != null && !memberName.isBlank()
//                ? QOrder.order.member.realName.contains(memberName)
//                : null;
//    }
//}
