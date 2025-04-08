package mcshop.jjonge_shop.dto;

import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.Order;
import mcshop.jjonge_shop.domain.OrderItem;
import mcshop.jjonge_shop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class OrderDto {

    private Long id;                      // 주문 ID
    private String memberName;           // 주문자 이름
    private String itemName;             // 상품 이름 (첫 번째 상품 기준)
    private int orderPrice;              // 주문 가격 (단가 * 수량)
    private int count;                   // 수량
    private OrderStatus status;          // 주문 상태 (ORDER, CANCEL 등)
    private LocalDateTime orderDate;     // 주문 날짜

    /**
     * Order 엔티티로부터 DTO를 생성하는 생성자
     * 주의: 현재는 첫 번째 주문 상품만 반영함
     */
    public OrderDto(Order order) {
        this.id = order.getId();
        this.memberName = order.getMember().getRealName();

        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems != null && !orderItems.isEmpty()) {
            OrderItem firstItem = orderItems.get(0);
            this.itemName = firstItem.getItem().getName();
            this.orderPrice = firstItem.getOrderPrice();
            this.count = firstItem.getCount();
        } else {
            this.itemName = "(상품 없음)";
            this.orderPrice = 0;
            this.count = 0;
        }

        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }

    /**
     * 정적 팩토리 메서드로 변환 (컨트롤러에서 깔끔하게 사용 가능)
     */
    public static OrderDto from(Order order) {
        return new OrderDto(order);
    }
}

