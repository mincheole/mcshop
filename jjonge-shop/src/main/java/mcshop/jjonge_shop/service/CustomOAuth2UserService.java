package mcshop.jjonge_shop.service;

import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.dto.GoogleReponse;
import mcshop.jjonge_shop.dto.NaverResponse;
import mcshop.jjonge_shop.dto.OAuth2Response;
import mcshop.jjonge_shop.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService의 확장 클래스로, OAuth2 사용자 로딩 로직 커스터마이징

    // memberRepository를 주입받기 위한 생성자
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        // 생성자에서 memberRepository를 초기화
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 loadUser 메서드를 호출하여 OAuth2User 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 전체 속성 출력 (디버깅용)
        System.out.println("전체 속성: " + oAuth2User.getAttributes());

        // OAuth2 제공자 이름 가져오기 (Google, Naver 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // 제공자가 Naver인 경우
        if (registrationId.equals("naver")) {
            // NaverResponse 객체 생성
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

            // 각 속성 값 출력 (디버깅용)
            System.out.println("Provider: " + oAuth2Response.getProvider());       // 제공자 이름
            System.out.println("ProviderId: " + oAuth2Response.getProviderId()); // 제공자 고유 ID
            System.out.println("Email: " + oAuth2Response.getEmail());           // 이메일
            System.out.println("Name: " + oAuth2Response.getName());             // 사용자 이름

            // 제공자가 Google인 경우
        } else if (registrationId.equals("google")) {
            // GoogleResponse 객체 생성
            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());

            // 지원하지 않는 제공자인 경우 null 반환
        } else {
            return null;
        }

        // 사용자 이름을 제공자와 고유 ID를 조합하여 생성
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        String role = "ROLE_USER"; // 기본 사용자 역할 지정

        // 데이터베이스에서 사용자 정보 검색, 없으면 새 사용자 생성
        Member member = memberRepository.findByUsername(username)
                .orElse(new Member());

        // 사용자 정보 업데이트
        member.setUsername(username);                  // 사용자 이름 설정
        member.setEmail(oAuth2Response.getEmail());    // 이메일 설정
        member.setRole(role);                          // 역할 설정
        member.setUsername(oAuth2Response.getName());  // 사용자 이름 설정 (실제 이름으로)

        // 사용자 정보 데이터베이스에 저장
        memberRepository.save(member);

        // 인증된 사용자 정보와 역할을 CustomOAuth2User 객체로 반환
        return new CustomOAuth2User(oAuth2Response, role);
    }
}