package mcshop.jjonge_shop.service;

import mcshop.jjonge_shop.domain.Member;
import mcshop.jjonge_shop.dto.GoogleResponse;
import mcshop.jjonge_shop.dto.NaverResponse;
import mcshop.jjonge_shop.dto.OAuth2Response;
import mcshop.jjonge_shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService의 확장 클래스로, OAuth2 사용자 로딩 로직 커스터마이징

    // memberRepository를 주입받기 위한 생성자
    private final MemberRepository memberRepository;

    private Map<String, Integer> snsCountMap = new HashMap<>();

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
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        // 제공자가 Google인 경우
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        // 지원하지 않는 제공자는 처리하지 않음
        else {
            return null;
        }

        // 사용자 정보 추출
        String provider = oAuth2Response.getProvider();  // SNS 제공자 (예: google, naver)
        String providerId = oAuth2Response.getProviderId();  // 제공자의 고유 ID

        // SNS 이름 뒤에 번호를 추가하기 위해 카운트를 확인 및 증가
        int count = snsCountMap.getOrDefault(provider, 0) + 1;
        snsCountMap.put(provider, count);

        // 사용자 이름 생성: SNS 이름 + 증가된 번호 (예: google1, naver1)
        String username = provider + count;
        String email = oAuth2Response.getEmail();  // 사용자의 이메일
        String realName = oAuth2Response.getName();  // 사용자의 실제 이름
        String role = "ROLE_USER";  // 기본 사용자 역할

        // 데이터베이스에서 사용자 검색
        // 기존 사용자가 없으면 새로 생성
        Member member = memberRepository.findByUsername(username)
                .orElse(new Member(username, email, role, realName));

        // 사용자 정보 업데이트
        // 로그인 시간을 현재 시각으로 설정
        member.setLastLoginTime(LocalDateTime.now());

        // 사용자 정보 저장 (새 사용자면 insert, 기존 사용자면 update)
        memberRepository.save(member);

        // 새로운 OAuth2User를 반환
        return new CustomOAuth2User(oAuth2Response, role);
    }
}