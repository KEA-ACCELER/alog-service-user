package kea.alog.user.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import kea.alog.user.service.UserService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EnableWebSecurity(debug = true)
// @EnableWebSecurity(debug = true) // 기본적인 웹 보안 활성화
// 추가적인 설정을 위해 WebSecurityConfigurer를 implements하거나
// WebSecurityConfigurerAdapter를 extends하는 방법이 있다.
@Configuration
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    /*
     * 보호할 URL경로 정의 / , /app은 인증이 필요하지 않도록 구성
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // front 가 분리된 환경을 가정하고 있기 때문에 csrf를 disable
                .httpBasic().disable()
                .anonymous().authorities("ROLE_ANONYMOUS").and()// token 기반 인증이기 때문에 기본적인 http 인증은 disable
                .cors().configurationSource(corsConfigurationSource()).and() // cors 커스텀하기
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) //jjwt 라이브러리가
                // Java에서 JWT를 생성하고 검증하는 데 사용되는 반면 OAuth 2.0은 토큰 전송 방식을 지정하는 프로토콜
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션저장기능
                                                                                                                // // 제거
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/users/login", "/api/users/signup", "/api/users/duplicated/**",
                                "/api/users/swagger", "/api/users/swagger-ui/**", "/api/users/swagger-resources/**", "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtTokenFilter(userService, secretKey),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("/swagger*/**"));
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 브라우저가 해당 origin이 자원에 접근 할 수 있도록 허용한다. *는
                                                                    // credential이 없는 경우에 한해 모든 origin에서 접근이 가능하게 함.
        configuration.setAllowedMethods(Arrays.asList("*")); // preflight 요청에 대한 응답으로 허용되는 메서드들
        configuration.setAllowedHeaders(Arrays.asList("*")); // preflight 요청에 다한 응답으로 실제 요청시 사용할 수 있는 HTTP헤더를 나타냄
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}

