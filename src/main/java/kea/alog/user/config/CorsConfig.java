package kea.alog.user.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer{
    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {

    //     CorsConfiguration configuration = new CorsConfiguration();
    //     // configuration.setAllowedOrigins(Arrays.asList("/swagger*/**"));
    //     configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 브라우저가 해당 origin이 자원에 접근 할 수 있도록 허용한다. *는
    //                                                                 // credential이 없는 경우에 한해 모든 origin에서 접근이 가능하게 함.
    //     configuration.setAllowedMethods(Arrays.asList("*")); // preflight 요청에 대한 응답으로 허용되는 메서드들
    //     configuration.setAllowedHeaders(Arrays.asList("*")); // preflight 요청에 다한 응답으로 실제 요청시 사용할 수 있는 HTTP헤더를 나타냄
    //     configuration.setAllowCredentials(true);
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;

    // }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //}
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}