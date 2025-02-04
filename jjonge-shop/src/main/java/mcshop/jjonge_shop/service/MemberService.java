package mcshop.jjonge_shop.service;

import lombok.RequiredArgsConstructor;
import mcshop.jjonge_shop.domain.MemberForm;
import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.dto.MemberDto;
import java.util.stream.Collectors;
import mcshop.jjonge_shop.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public void registerMember(MemberForm memberForm) {
        Member member = new Member();
        member.setUsername(memberForm.getUsername());  // 사용자 ID
        member.setEmail(memberForm.getEmail());  // 이메일
//        member.setPassword(passwordEncoder.encode(memberForm.getPassword()));  // 비밀번호 암호화 저장
        member.setRole("ROLE_USER");  // 기본 사용자 역할 지정
//        member.setRealName(memberForm.getRealName());  // 사용자의 실제 이름

        memberRepository.save(member);
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // getfindMembers 메서드 추가
    public List<MemberDto> getfindMembers() {
        List<Member> members = findMembers();
        // Member 엔티티를 MemberDto로 변환
        List<MemberDto> memberDtos = members.stream()
                .map(member -> new MemberDto(member)) // MemberDto 생성자 사용
                .collect(Collectors.toList());
        return memberDtos;
    }
}