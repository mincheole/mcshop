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