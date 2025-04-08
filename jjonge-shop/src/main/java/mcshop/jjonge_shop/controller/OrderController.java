package mcshop.jjonge_shop.controller;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.dto.*;
import mcshop.jjonge_shop.service.ItemService;
import mcshop.jjonge_shop.service.MemberService;
import mcshop.jjonge_shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 주문 등록 폼 (GET /order)
     */
    @GetMapping("/order")
    public String createForm(Model model) {
        List<MemberDto> members = memberService.findMemberDtos();
        List<ItemDto> items = itemService.findAllItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        model.addAttribute("orderForm", new OrderForm()); // 유효성 검사를 위한 빈 폼 추가
        return "order/orderForm";
    }

    /**
     * 주문 요청 (POST /order)
     */
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    /**
     * 주문 목록 (GET /orders)
     */
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<OrderDto> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    /**
     * 주문 취소 (POST /orders/{orderId}/cancel)
     */
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}