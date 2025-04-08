package mcshop.jjonge_shop.repository;

import mcshop.jjonge_shop.domain.Order;
import mcshop.jjonge_shop.domain.OrderStatus;
import mcshop.jjonge_shop.dto.OrderSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 기존 쿼리 메서드 + search() 사용 가능

    // 특정 회원의 주문 목록 조회
    List<Order> findByMemberId(Long memberId);

    // 페이징 처리를 포함한 회원별 주문 조회
    Page<Order> findByMemberId(Long memberId, Pageable pageable);

    // 주문 상태별 조회
    List<Order> findByStatus(OrderStatus status);

    // 특정 기간 내 주문 조회
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 회원별, 상태별 주문 조회
    List<Order> findByMemberIdAndStatus(Long memberId, OrderStatus status);

    // 날짜 기준 내림차순 정렬하여 회원의 주문 조회
    List<Order> findByMemberIdOrderByOrderDateDesc(Long memberId);

    // 주문 ID로 주문을 조회할 때 회원 정보도 함께 조회 (N+1 문제 방지)
    @EntityGraph(attributePaths = {"member"})
    Optional<Order> findById(Long orderId);

    // 주문 ID로 주문을 조회할 때 주문상품도 함께 조회
    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findWithOrderItemsById(Long orderId);

    // 주문 ID로 주문, 회원, 주문상품을 모두 함께 조회
    @EntityGraph(attributePaths = {"member", "orderItems"})
    Optional<Order> findWithMemberAndOrderItemsById(Long orderId);

    // 특정 가격 이상의 주문 조회
    List<Order> findByOrderItemsOrderPriceGreaterThan(int price);

    // 최근 주문 조회 (날짜 내림차순 정렬 후 첫 번째)
    Order findFirstByOrderByOrderDateDesc();

//    List<Order> findAllBySearch(OrderSearch orderSearch);


    @Query("SELECT o FROM Order o JOIN o.member m " +
            "WHERE (:memberName IS NULL OR m.realName LIKE %:memberName%) " +
            "AND (:orderStatus IS NULL OR o.status = :orderStatus)")
    List<Order> findAllBySearch(@Param("memberName") String memberName,
                                @Param("orderStatus") OrderStatus orderStatus);

}