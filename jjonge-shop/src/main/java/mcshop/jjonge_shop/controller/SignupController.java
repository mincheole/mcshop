package mcshop.jjonge_shop.controller;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.domain.MemberForm;
import mcshop.jjonge_shop.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
//public class SignupController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/createMemberForm")
//    public String showSignupForm(Model model) {
//        model.addAttribute("memberForm", new MemberForm()); // 빈 폼 객체 전달
//        return "createMemberForm"; // resources/templates/signup.html 반환
//    }
//
//    @PostMapping("/register")
//    public String registerMember(@ModelAttribute MemberForm memberForm) {
//        memberService.registerMember(memberForm);
//        return "redirect:/login"; // 회원가입 후 로그인 페이지로 이동
//    }
//}
@Controller
@RequiredArgsConstructor
public class SignupController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // GET 요청으로 회원가입 폼을 보여주는 메서드
    @GetMapping("/createMemberForm")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // 회원가입 폼 객체를 뷰로 전달
        return "createMemberForm"; // 회원가입 페이지(createMemberForm.html)를 반환
    }

    // POST 요청으로 회원가입 처리하는 메서드
    @PostMapping("/signup")
    public String register(@ModelAttribute MemberForm memberForm) {
        // 입력된 정보로 새로운 사용자 생성
        Member newMember = new Member(
                memberForm.getUsername(),
                memberForm.getEmail(),
                passwordEncoder.encode(memberForm.getPassword()), // 비밀번호 암호화
                "ROLE_USER", // 기본 역할은 ROLE_USER
                memberForm.getRealName()
        );

        // 사용자 정보 저장
        memberRepository.save(newMember);

        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
