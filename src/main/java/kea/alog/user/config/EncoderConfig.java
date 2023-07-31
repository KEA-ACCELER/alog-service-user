package kea.alog.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {// 순환참조 문제가 발생할 수 있기 때문에 filterchain과 다른 클래스에 선언
        return new BCryptPasswordEncoder();
    }


}

