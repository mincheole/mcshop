package mcshop.jjonge_shop.oauth2;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

@Component // Spring에서 관리되는 Bean으로 등록
public class SocialClientRegistration {

    // 네이버 OAuth2 클라이언트 등록 메서드
    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver") // 네이버 클라이언트 등록 ID
                .clientId("ID") // 네이버 API에서 발급받은 클라이언트 ID
                .clientSecret("KEY") // 네이버 API에서 발급받은 클라이언트 비밀키
                .redirectUri("http://localhost:8080/login/oauth2/code/naver") // 인증 완료 후 리다이렉트될 URI
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 인증 방식: Authorization Code 방식
                .scope("name", "email") // 요청할 권한(사용자 이름과 이메일)
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize") // 네이버 인증 URI
                .tokenUri("https://nid.naver.com/oauth2.0/token") // 네이버 토큰 발급 URI
                .userInfoUri("https://openapi.naver.com/v1/nid/me") // 사용자 정보 조회 URI
                .userNameAttributeName("response") // 사용자 정보의 최상위 속성명
                .build(); // ClientRegistration 객체 생성
    }

    // 구글 OAuth2 클라이언트 등록 메서드
    public ClientRegistration googleClientRegistration() {

        return ClientRegistration.withRegistrationId("google") // 구글 클라이언트 등록 ID
                .clientId("ID") // 구글 API에서 발급받은 클라이언트 ID
                .clientSecret("KEY") // 구글 API에서 발급받은 클라이언트 비밀키
                .redirectUri("http://localhost:8080/login/oauth2/code/google") // 인증 완료 후 리다이렉트될 URI
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 인증 방식: Authorization Code 방식
                .scope("profile", "email") // 요청할 권한(프로필 정보와 이메일)
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth") // 구글 인증 URI
                .tokenUri("https://www.googleapis.com/oauth2/v4/token") // 구글 토큰 발급 URI
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs") // 구글 공개키 URI(JWK)
                .issuerUri("https://accounts.google.com") // 발급자 정보 URI
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo") // 사용자 정보 조회 URI
                .userNameAttributeName(IdTokenClaimNames.SUB) // 사용자 정보의 고유 식별자 속성명
                .build(); // ClientRegistration 객체 생성
    }
}