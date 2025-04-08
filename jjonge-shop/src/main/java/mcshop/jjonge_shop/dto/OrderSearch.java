package mcshop.jjonge_shop.dto;

import lombok.Getter;
import lombok.Setter;
import mcshop.jjonge_shop.domain.OrderStatus;

/**
 * 주문 검색 조건 DTO
 */
@Getter
@Setter
public class OrderSearch {
    private String memberName;             // 회원 이름 (검색 조건)
    private OrderStatus orderStatus;       // 주문 상태 [ORDER, CANCEL]
}