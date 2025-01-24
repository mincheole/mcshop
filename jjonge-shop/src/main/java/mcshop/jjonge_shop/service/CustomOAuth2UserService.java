package mcshop.jjonge_shop.service;

import mcshop.jjonge_shop.dto.GoogleReponse;
import mcshop.jjonge_shop.dto.NaverResponse;
import mcshop.jjonge_shop.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService의 확장 클래스로, OAuth2 사용자 로딩 로직 커스터마이징

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 사용자 정보 로드 (부모 클래스의 메서드 호출)
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 사용자 속성 콘솔 출력 (디버깅용)
        System.out.println(oAuth2User.getAttributes());

        // 로그인에 사용된 OAuth2 제공자 ID 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // 제공자에 따라 다른 OAuth2Response 객체 생성
        if (registrationId.equals("naver")) {
            // 네이버 로그인의 경우 NaverResponse 생성
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            // 구글 로그인의 경우 GoogleResponse 생성
            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());
        }
        else {
            // 지원되지 않는 제공자일 경우 null 반환
            return null;
        }

        // 기본 역할 설정
        String role = "ROLE_USER";

        // 커스텀 OAuth2User 객체 생성 및 반환
        return new CustomOAuth2User(oAuth2Response, role);
    }
}