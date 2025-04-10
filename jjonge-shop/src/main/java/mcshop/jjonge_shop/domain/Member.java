package mcshop.jjonge_shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    // 주문 연관관계 추가
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

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
}