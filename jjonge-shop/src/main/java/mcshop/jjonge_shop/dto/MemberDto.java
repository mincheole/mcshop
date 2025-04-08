package mcshop.jjonge_shop.dto;

import lombok.Data;
import java.time.LocalDateTime;
import mcshop.jjonge_shop.domain.Member;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String realName;
    private String email;
    private String role;
    private LocalDateTime lastLoginTime;
    private String password;  // 일반 로그인 사용자를 위한 비밀번호 (일반 사용자에게만 필요)

    // 일반 사용자와 SNS 사용자를 구분하는 생성자
    public MemberDto(Long id, String username, String email, String password, String role, String realName, LocalDateTime lastLoginTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.realName = realName;
        this.lastLoginTime = lastLoginTime;
    }

    // SNS 사용자 생성자 (password 필드 없이 처리)
    public MemberDto(Long id, String username, String email, String role, String realName, LocalDateTime lastLoginTime) {
        this(id, username, email, null, role, realName, lastLoginTime);  // password는 null로 설정
    }

    // Member 엔티티를 DTO로 변환하는 생성자
    public MemberDto(Member member) {
        if (member.getPassword() != null) {
            this.id = member.getId();
            this.username = member.getUsername();
            this.email = member.getEmail();
            this.password = member.getPassword();  // 일반 사용자: password 포함
            this.role = member.getRole();
            this.realName = member.getRealName();
            this.lastLoginTime = member.getLastLoginTime();
        } else {
            // SNS 사용자: password 제외
            this.id = member.getId();
            this.username = member.getUsername();
            this.email = member.getEmail();
            this.role = member.getRole();
            this.realName = member.getRealName();
            this.lastLoginTime = member.getLastLoginTime();
        }
    }

    public static MemberDto from(Member member) {
        return new MemberDto(member);
    }
}

