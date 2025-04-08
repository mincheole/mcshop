package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문 상품 엔티티
 * 주문과 상품의 다대다 관계를 해소하는 엔티티
 */
@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 접근 제한
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 주문 상품

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int orderPrice; // 주문 당시 가격
    private int count; // 주문 수량

    /**
     * 주문상품 생성 메서드
     * 상품 정보와 주문 가격, 수량을 받아 주문상품 엔티티 생성
     * 상품의 재고를 감소시킴
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 재고 감소
        return orderItem;
    }

    /**
     * 주문 취소 시 호출되는 메서드
     * 취소된 주문의 상품 수량만큼 재고를 증가시킴
     */
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문상품 전체 가격 조회
     * 주문 가격과 수량을 곱해서 반환
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}