package kea.alog.user.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties.Server;
import org.springframework.context.annotation.*;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class Swagger3Config {

    @Value("${springdoc.version}")
    private String springdocVersion;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("User API")
                .version(springdocVersion)
                .description("API for user domain");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
// JWT SecurityContext 구성
    // private SecurityContext securityContext() {
    //     return SecurityContext.builder()
    //             .securityReferences(defaultAuth())
    //             .build();
    // }

    // private List<SecurityReference> defaultAuth() {
    //     AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    //     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    //     authorizationScopes[0] = authorizationScope;
    //     return List.of(new SecurityReference("Authorization", authorizationScopes));
    // }
    
    // // ApiKey 정의
    // private ApiKey apiKey() {
    //     return new ApiKey("Authorization", "Authorization", "header");
    // }
}
