package com.pinsoft.intern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'ler için CORS ayarları
                .allowedOrigins("http://localhost:5173", "https://blogger-pinsoft.vercel.app/") // İzin verilen kaynaklar
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP yöntemleri
                .allowedHeaders("*") // Tüm başlıklar
                .allowCredentials(true); // Kimlik bilgilerine izin verme
    }
}
