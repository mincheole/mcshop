package mcshop.jjonge_shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.Order;
import mcshop.jjonge_shop.domain.OrderItem;
import mcshop.jjonge_shop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OrderDto {

    private Long id;                      // 주문 ID
    private String memberName;           // 주문자 이름
    private String itemName;             // 상품 이름 (첫 번째 상품 기준)
    private int orderPrice;              // 주문 가격 (단가 * 수량)
    private int count;                   // 수량
    private OrderStatus status;          // 주문 상태 (ORDER, CANCEL 등)
    private LocalDateTime orderDate;     // 주문 날짜
    private List<OrderItemDto> orderItems;

    /**
     * Order 엔티티로부터 DTO를 생성하는 생성자
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
        this.orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }

    /**
     * QueryDSL DTO 프로젝션용 생성자
     */
    @QueryProjection
    public OrderDto(Long id, String memberName, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.status = status;
    }

    /**
     * 정적 팩토리 메서드로 변환 (컨트롤러에서 깔끔하게 사용 가능)
     */
    public static OrderDto from(Order order) {
        return new OrderDto(order);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(item -> item.getOrderPrice() * item.getCount())
                .sum();
    }
}