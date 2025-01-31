package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Entity
//@Getter
//@Setter
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//
//    private String email;
//
//    private String role;
//
//    private LocalDateTime lastLoginTime; // 로그인한 마지막 시각 추가
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 일반 사용자 & SNS 사용자 모두 사용하는 ID

    @Column(nullable = false)
    private String email; // 이메일 (회원가입 & SNS 로그인)

    private String password; // 일반 로그인 사용자를 위한 비밀번호

    private String role; // 역할 (ROLE_USER, ROLE_ADMIN 등)

    private String realName; // 사용자의 실제 이름 (선택적)

    private LocalDateTime lastLoginTime; // 마지막 로그인 시각

    // 회원가입 사용자 생성자
    public Member(String username, String email, String password, String role, String realName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.realName = realName;
        this.lastLoginTime = LocalDateTime.now();
    }

    // SNS 사용자 생성자
    public Member(String username, String email, String role, String realName) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.realName = realName;
        this.lastLoginTime = LocalDateTime.now();
    }

    // lastLoginTime을 'hh.mm.ss' 형식으로 반환하는 메서드
    public String getFormattedLastLoginTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
        return lastLoginTime.format(formatter);
    }
}