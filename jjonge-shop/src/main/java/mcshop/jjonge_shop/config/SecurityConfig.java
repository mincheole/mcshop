package mcshop.jjonge_shop.config;

import mcshop.jjonge_shop.oauth2.CustomClientRegistrationRepo;
import mcshop.jjonge_shop.oauth2.CustomOAuth2AuthorizedClientService;
import mcshop.jjonge_shop.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // CustomOAuth2UserService를 주입받는 생성자
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomClientRegistrationRepo customClientRegistrationRepo,
                          CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService, JdbcTemplate jdbcTemplate) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 방식으로 암호화
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
                .oauth2Login((oauth2) -> oauth2  //리다이렉션
                        // 사용자 정보 요청 시 커스터마이즈된 서비스 사용
                        .loginPage("/login")            // 로그인 페이지 경로 설정
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)
                        ));

        // URL별 인증 및 접근 권한 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        // 웰컴 페이지와 로그인 페이지는 인증 없이 접근 가능하도록 허용
                        .requestMatchers("/", "/oauth2/**", "/members/login", "/members/new").permitAll()
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated());

        // 인증되지 않은 사용자가 접근하려는 페이지가 있을 경우 로그인 페이지로 리다이렉트
        http.exceptionHandling((exception) -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/members/login"); // 로그인 페이지로 리다이렉트 (에러 파라미터 추가)
                }));

        // 로그아웃 설정
        http.logout((logout) -> logout
                .logoutUrl("/logout")              // 로그아웃 URL 설정
                .logoutSuccessUrl("/")             // 로그아웃 성공 후 리디렉션할 페이지 (홈 화면 등)
                .invalidateHttpSession(true)       // 세션 무효화
                .clearAuthentication(true));       // 인증 정보 제거

        // SecurityFilterChain 객체를 빌드하여 반환
        return http.build();
    }
}