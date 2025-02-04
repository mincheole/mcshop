package mcshop.jjonge_shop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.domain.MemberForm;
import mcshop.jjonge_shop.dto.MemberDto;
import mcshop.jjonge_shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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