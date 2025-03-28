package pl.edu.wszib.gateway_api.client;

import feign.form.FormEncoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignFormSupportConfig {
    @Bean
    public FormEncoder feignFormEncoder() {
        return new FormEncoder(new SpringFormEncoder());
    }
}