package mcshop.jjonge_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/createMemberForm")
    public String showSignupForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // 빈 폼 객체 전달
        return "createMemberForm"; // resources/templates/signup.html 반환
    }
}