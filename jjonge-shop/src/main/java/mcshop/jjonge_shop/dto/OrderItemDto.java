package mcshop.jjonge_shop.dto;

import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.OrderItem;

@Getter @Setter
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
