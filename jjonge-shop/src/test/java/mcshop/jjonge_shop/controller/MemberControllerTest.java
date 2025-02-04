package mcshop.jjonge_shop.controller;

import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Test
    void testFindMembers() {
        // DB에서 회원 목록 가져오기
        List<Member> members = memberService.findMembers();

        // 콘솔 출력
        System.out.println("=== 회원 목록 출력 ===");
        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Name: " + member.getUsername());
        }
        System.out.println("===================");

        // 검증 (회원이 존재하는지 확인)
        assert !members.isEmpty() : "회원 목록이 비어 있습니다!";
    }
}