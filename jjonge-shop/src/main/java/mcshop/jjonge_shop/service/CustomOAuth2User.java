package mcshop.jjonge_shop.service;

import mcshop.jjonge_shop.dto.OAuth2Response;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// OAuth2 인증을 처리하기 위한 사용자 정의 OAuth2User 구현체
public class CustomOAuth2User implements OAuth2User {
    // 사용자 세부 정보를 포함하는 OAuth2Response 참조
    private final OAuth2Response oAuth2Response;

    // 권한 부여를 위한 사용자 역할
    private final String role;

    // OAuth2 응답과 역할로 사용자 초기화하는 생성자
    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

    // 사용자 속성 반환 - 현재는 null 반환, 일반적으로 사용자 속성 반환 예정
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    // 사용자 권한(역할) 생성
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 저장할 컬렉션 생성
        Collection<GrantedAuthority> collection = new ArrayList<>();
        // 사용자 역할을 가진 새 GrantedAuthority 추가
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role; // 사용자 역할 반환
            }
        });
        return collection;
    }

    // OAuth2Response에서 사용자 이름 반환
    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    // 제공자와 제공자별 ID를 결합한 고유 사용자 이름 생성
    public String getUsername() {
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }
}