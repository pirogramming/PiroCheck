package backend.pirocheck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 백엔드 API 요청에만 CORS 허용
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://www.pirocheck.org",
                        "https://www.pirocheck.org"
                ) // 프론트 배포 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*")
                .allowCredentials(true); // 세션 쿠키 주고받기 허용
    }
}
