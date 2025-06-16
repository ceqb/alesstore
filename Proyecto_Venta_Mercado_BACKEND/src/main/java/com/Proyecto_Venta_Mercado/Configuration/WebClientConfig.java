package com.Proyecto_Venta_Mercado.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${mercadopago.api-url}")
    private String mercadoPagoApiUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(mercadoPagoApiUrl)
                .build();
    }
}
