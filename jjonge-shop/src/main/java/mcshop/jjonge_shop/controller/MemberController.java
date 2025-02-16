package mcshop.jjonge_shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.dto.MemberForm;
import mcshop.jjonge_shop.dto.MemberDto;
import mcshop.jjonge_shop.repository.MemberRepository;
import mcshop.jjonge_shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
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

    @PostMapping("/members/login")
    public String login(@ModelAttribute MemberForm memberForm, Model model) {
        // 입력된 이메일을 기준으로 회원 정보 조회 (Optional로 감싸진 객체를 반환)
        Optional<Member> optionalMember = memberRepository.findByEmail(memberForm.getEmail());

        // 회원이 존재하지 않거나 비밀번호가 일치하지 않으면 로그인 실패 처리
        if (optionalMember.isEmpty() || !passwordEncoder.matches(memberForm.getPassword(), optionalMember.get().getPassword())) {
            model.addAttribute("loginError", "잘못된 이메일 또는 비밀번호입니다.");
            return "/members/login";  // 로그인 페이지로 다시 이동
        }

        // 로그인 성공 시 회원 객체를 가져옴
        Member member = optionalMember.get();

        // 로그인 성공 시 세션이나 모델에 사용자 정보 저장
        model.addAttribute("member", member);

        return "redirect:/";  // 홈페이지로 리다이렉트
    }


    @PostMapping("/members/new")
    public String register(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult) {
        // 이메일 중복 검사
        if (memberRepository.findByEmail(memberForm.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
            return "/members/createMemberForm";
        }

        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "/members/createMemberForm";
        }

        try {
            // 입력된 정보로 새로운 사용자 생성
            Member newMember = new Member(
                    memberForm.getUsername(),
                    memberForm.getEmail(),
                    passwordEncoder.encode(memberForm.getPassword()),
                    "ROLE_USER",
                    memberForm.getRealName()
            );

            // 사용자 정보 저장
            memberRepository.save(newMember);

            return "redirect:/members/login";  // 회원가입 성공 시 로그인 페이지로
        } catch (Exception e) {
            // 에러 로깅
            bindingResult.reject("signupFailed", "회원가입 중 오류가 발생했습니다.");
            return "/members/createMemberForm";
        }
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