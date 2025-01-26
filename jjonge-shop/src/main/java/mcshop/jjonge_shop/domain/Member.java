package mcshop.jjonge_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String role;

    private LocalDateTime lastLoginTime; // 로그인한 마지막 시각 추가

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}