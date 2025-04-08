package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문 엔티티
 * 주문 정보를 저장하고 주문과 관련된 비즈니스 로직을 처리
 */
@Entity
@Table(name = "orders") // orders는 DB에서 예약어일 수 있으므로 테이블명 명시
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 접근 제한
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id; // 주문 ID

    @ManyToOne(fetch = LAZY) // 지연 로딩 설정
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 상품 목록

    private LocalDateTime orderDate; // 주문 일시

    @Enumerated(EnumType.STRING) // 문자열로 저장
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    /**
     * 연관관계 편의 메서드 - 회원 설정
     * 양방향 연관관계에서 양쪽에 값을 설정
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); // 회원의 주문 목록에도 추가
    }

    /**
     * 연관관계 편의 메서드 - 주문상품 추가
     * 주문상품을 추가하고 주문상품에 주문 설정
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
     * 주문 생성 메서드
     * 주문 엔티티를 생성하고 필요한 연관관계를 설정
     */
    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);

        // 주문상품 추가
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.status = OrderStatus.ORDER; // 주문 상태 설정
        order.orderDate = LocalDateTime.now(); // 주문 시간 설정
        return order;
    }

    /**
     * 주문 취소 메서드
     * 주문 상태를 취소로 변경하고 주문상품의 재고를 원복
     */
    public void cancel() {
        // 이미 취소된 경우 예외 발생
        if (this.status == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }

        this.status = OrderStatus.CANCEL; // 주문 상태를 취소로 변경

        // 주문상품별로 취소 처리
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 재고 원복
        }
    }

    /**
     * 전체 주문 가격 조회
     * 모든 주문상품의 가격을 합산하여 반환
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}