package mcshop.jjonge_shop.controller;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.dto.MemberForm;
import mcshop.jjonge_shop.dto.MemberDto;
import mcshop.jjonge_shop.repository.MemberRepository;
import mcshop.jjonge_shop.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // GET 요청으로 회원가입 폼을 보여주는 메서드
    @GetMapping("/members/new")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // 회원가입 폼 객체를 뷰로 전달
        return "/members/createMemberForm"; // 회원가입 페이지(createMemberForm.html)를 반환
    }

    @GetMapping("/members/login")
    public String loginPage() {
        return "/members/login"; // `login.html` 템플릿 반환
    }

//    @PostMapping("/members/login")
//    public String login(@ModelAttribute MemberForm memberForm, Model model) {
//        Member member = memberRepository.findByEmail(memberForm.getEmail())
//                .orElse(null);
//
//        if (member == null || !passwordEncoder.matches(memberForm.getPassword(), member.getPassword())) {
//            model.addAttribute("loginError", "Invalid email or password");
//            return "/members/login";
//        }
//
//        model.addAttribute("member", member);
//        return "redirect:/";
//    }

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

        return "redirect:/"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

    // 회원 목록을 조회하여 출력하는 GET 메서드
    @GetMapping("/members")
    public String list(Model model) {
        // MemberService의 findMembers() 메서드를 호출하여 회원 리스트를 가져옴
        List<MemberDto> members = memberService.getfindMembers();
        // 모델에 회원 리스트 추가
        model.addAttribute("members", members);
        return "members/memberList";  // 회원 목록 뷰 이름
    }
}