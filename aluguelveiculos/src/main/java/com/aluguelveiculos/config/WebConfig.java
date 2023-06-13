package com.aluguelveiculos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	//Configuração de Cross-Origin Resource Sharing (CORS), permitindo que solicitações HTTP sejam feitas a partir de origens diferentes
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Define o padrão de URL para o qual as configurações CORS serão aplicadas
            .allowedOrigins("http://127.0.0.1:5500") // Define a origem permitida para as solicitações CORS
            .allowedMethods("GET", "POST", "PUT", "DELETE"); // Define os métodos HTTP permitidos para as solicitações CORS
    }
}
