package mcshop.jjonge_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String showLoginPage(Model model) {
//        model.addAttribute("username", "Enter your username");
//        return "login"; // login.html 파일과 매핑
//    }
//}
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // `login.html` 템플릿 반환
    }
}
