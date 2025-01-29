package mcshop.jjonge_shop.config;

import mcshop.jjonge_shop.oauth2.CustomClientRegistrationRepo;
import mcshop.jjonge_shop.oauth2.CustomOAuth2AuthorizedClientService;
import mcshop.jjonge_shop.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // CustomOAuth2UserService를 주입받는 생성자
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomClientRegistrationRepo customClientRegistrationRepo,
                          CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService, JdbcTemplate jdbcTemplate) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 기능 비활성화
        http
                .csrf((csrf) -> csrf.disable());

        // 사용자 정의 로그인 페이지 설정
        http
                .formLogin((login) -> login
                        .loginPage("/login")  // 로그인 페이지 경로 설정
                        .permitAll());        // 로그인 페이지는 인증 없이 접근 가능하도록 설정

        // HTTP Basic 인증 비활성화
        http
                .httpBasic((basic) -> basic.disable());

        // OAuth2 로그인 설정
        http
                .oauth2Login((oauth2) -> oauth2
                        // 사용자 정보 요청 시 커스터마이즈된 서비스 사용
                        .loginPage("/login")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
//                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(
//                                                    jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));

        // URL별 인증 및 접근 권한 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        // 특정 URL은 인증 없이 접근 가능하도록 허용
                        .requestMatchers("/", "/oauth2/**", "/login/**", "/createMemberForm").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated());

        // SecurityFilterChain 객체를 빌드하여 반환
        return http.build();
    }
}