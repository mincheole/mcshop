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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
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
        // 문자 인코딩 필터 추가
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http
                // CSRF 보호 비활성화 (필요에 따라 활성화 고려)
                .csrf((csrf) -> csrf.disable())
                // 폼 로그인 설정
//                .formLogin((login) -> login
//                        .loginPage("/members/login")
//                        .defaultSuccessUrl("/", true)
//                        .permitAll())
                .formLogin((login) -> login
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/members/login")
                        .loginProcessingUrl("/members/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/members/login?error=true")
                        .permitAll())
                // HTTP Basic 인증 비활성화
                .httpBasic((basic) -> basic.disable())
                // OAuth2 로그인 설정
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/members/login")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(authenticationSuccessHandler()))
                // URL 기반 접근 제어 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/members/login", "/members/new", "/css/**", "/js/**", "/img/**").permitAll()
                        .anyRequest().authenticated())
                // 인증 예외 처리
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/members/login");
                        }))
                // 로그아웃 설정
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true));

        return http.build();
//
//        // CSRF 보호 기능 비활성화
//        http
//                .csrf((csrf) -> csrf.disable());
//
//        // 사용자 정의 로그인 페이지 설정
//        http
//                .formLogin((login) -> login
//                        .loginPage("/login")  // 로그인 페이지 경로 설정
//                        .permitAll());        // 로그인 페이지는 인증 없이 접근 가능하도록 설정
//
//        // HTTP Basic 인증 비활성화
//        http
//                .httpBasic((basic) -> basic.disable());
//
//        // OAuth2 로그인 설정
//        http
//                .oauth2Login((oauth2) -> oauth2
//                // OAuth2 로그인의 로그인 페이지 URL을 지정
//                .loginPage("/login")
//                // OAuth2 클라이언트 등록 정보를 저장하는 레포지토리 설정
//                .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
//                // OAuth2 로그인 성공 후 사용자 정보를 가져올 때의 설정
//                .userInfoEndpoint((userInfoEndpointConfig) ->
//                        // 사용자 정보를 처리할 커스텀 서비스 설정
//                        userInfoEndpointConfig.userService(customOAuth2UserService))
//                // 로그인 성공 시 실행될 핸들러 추가
//                .successHandler((request, response, authentication) -> {
//                    response.setCharacterEncoding("UTF-8");
//                    response.sendRedirect("/");  // 로그인 성공 후 리다이렉트할 페이지
//                }));
//
//        // URL별 인증 및 접근 권한 설정
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        // 웰컴 페이지와 로그인 페이지는 인증 없이 접근 가능하도록 허용
//                        .requestMatchers("/", "/oauth2/**", "/members/login", "/members/new").permitAll()
//                        // 로그인 후 사용자는 ROLE_USER로 기본 권한을 부여받고 접근 가능
//                        .requestMatchers("/**").hasRole("USER")
//                        // 그 외 모든 요청은 인증 필요
//                        .anyRequest().authenticated());
//
//        // 인증되지 않은 사용자가 접근하려는 페이지가 있을 경우 로그인 페이지로 리다이렉트
//        http.exceptionHandling((exception) -> exception
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.sendRedirect("/members/login"); // 로그인 페이지로 리다이렉트 (에러 파라미터 추가)
//                }));
//
//        // 로그아웃 설정
//        http.logout((logout) -> logout
//                .logoutUrl("/logout")              // 로그아웃 URL 설정
//                .logoutSuccessUrl("/")             // 로그아웃 성공 후 리디렉션할 페이지 (홈 화면 등)
//                .invalidateHttpSession(true)       // 세션 무효화
//                .clearAuthentication(true));       // 인증 정보 제거

        // SecurityFilterChain 객체를 빌드하여 반환
    }

    // 인증 성공 핸들러 빈 등록
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setCharacterEncoding("UTF-8");
            response.sendRedirect("/");  // 인증 성공 시 홈페이지로 리다이렉트
        };
    }

}