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

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // OAuth2 사용자 정보 로드 (부모 클래스 메서드 호출)
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // 디버깅용 사용자 속성 출력
//        System.out.println(oAuth2User.getAttributes());
//
//        // 로그인에 사용된 OAuth2 제공자 식별
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        OAuth2Response oAuth2Response = null;
//
//        // 제공자별 Response 객체 생성
//        if (registrationId.equals("naver")) {
//            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
//        } else if (registrationId.equals("google")) {
//            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());
//        } else {
//            // 지원하지 않는 제공자의 경우 null 반환
//            return null;
//        }
//
//        // 사용자 이름 생성 (제공자 + 고유 ID)
//        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
//
//        // 기본 사용자 역할 설정
//        String role = "ROLE_USER";
//
//        // 기존 사용자 조회, 없으면 새 Member 객체 생성
//        Member member = memberRepository.findByUsername(username)
//                .orElse(new Member());
//
//        // 사용자 정보 업데이트
//        member.setUsername(username);
//        member.setEmail(oAuth2Response.getEmail());
//        member.setRole(role);
//        member.setUsername(oAuth2Response.getName());  // 사용자 이름 추가
//
//        // 사용자 정보 저장
//        memberRepository.save(member);
//
//        // CustomOAuth2User 객체 생성 및 반환
//        return new CustomOAuth2User(oAuth2Response, role);
//    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 전체 속성 상세 출력
        System.out.println("전체 속성: " + oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

            // 각 속성 개별 출력
            System.out.println("Provider: " + oAuth2Response.getProvider());
            System.out.println("ProviderId: " + oAuth2Response.getProviderId());
            System.out.println("Email: " + oAuth2Response.getEmail());
            System.out.println("Name: " + oAuth2Response.getName());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleReponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        String role = "ROLE_USER";

        Member member = memberRepository.findByUsername(username)
                .orElse(new Member());

        member.setUsername(username);
        member.setEmail(oAuth2Response.getEmail());
        member.setRole(role);
        member.setUsername(oAuth2Response.getName());

        memberRepository.save(member);

        return new CustomOAuth2User(oAuth2Response, role);
    }
}