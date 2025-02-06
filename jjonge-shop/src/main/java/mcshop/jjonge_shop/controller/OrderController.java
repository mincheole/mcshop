package mcshop.jjonge_shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    /**
     * 주문
     */
    @GetMapping(value = "/order")
    public String createForm(Model model) {
        return "order/orderForm";
    }

    /**
     * 주문 내역
     */
    @GetMapping(value = "/orders")
    public String orderList(Model model) {
        return "order/orderList";
    }
}