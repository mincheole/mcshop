package mcshop.jjonge_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    // 로그아웃 페이지
    @GetMapping("/logout.html")
    public String logout() {
        return "logout"; // 로그아웃 페이지로 이동 (로그아웃을 진행하는 HTML 페이지)
    }
}