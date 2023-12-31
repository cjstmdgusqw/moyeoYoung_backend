package com.kosta.moyoung.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value("${api.base.furl}")
    private String apiUrl;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
			.allowedOrigins(apiUrl)
			.allowedMethods("GET","POST","PUT","DELETE")
			.allowedHeaders("*")    //허용되는 헤더
	        .allowCredentials(true)    //자격증명 허용
	        .maxAge(3600);   //허용 시간
	}	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
