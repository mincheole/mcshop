package mcshop.jjonge_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class EncodingConfig {

    // CharacterEncodingFilter 빈(Bean)을 생성하는 메서드
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        // CharacterEncodingFilter 객체를 생성
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        // 문자 인코딩을 UTF-8로 설정
        filter.setEncoding("UTF-8");

        // 강제로 지정한 인코딩 설정을 적용
        filter.setForceEncoding(true);

        // CharacterEncodingFilter 객체를 반환
        return filter;
    }
}
