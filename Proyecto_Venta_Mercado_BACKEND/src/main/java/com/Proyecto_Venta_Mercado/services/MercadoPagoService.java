package com.Proyecto_Venta_Mercado.services;





import com.Proyecto_Venta_Mercado.dto.MercadoPago;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MercadoPagoService {

    private final WebClient webClient;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public MercadoPagoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.mercadopago.com").build();
    }

    public String crearPreferencia(MercadoPago request) {
        List<Map<String, Object>> items = request.getItems().stream().map(i -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("title", i.getTitle());
            itemMap.put("quantity", i.getQuantity());
            itemMap.put("unit_price", i.getUnit_price());
            itemMap.put("currency_id", "PEN");
            return itemMap;
        }).collect(Collectors.toList());

        Map<String, Object> body = new HashMap<>();
        body.put("items", items);

        // Agregamos el carrito completo como metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("cliente", request.getCliente());
        body.put("metadata", metadata);

        Map<String, Object> response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/checkout/preferences")
                        .queryParam("access_token", accessToken)
                        .build())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return (String) response.get("init_point");
    }
    public Map<String, Object> procesarPago(String paymentId) {
        return webClient.get()
                .uri("/v1/payments/{paymentId}", paymentId)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(); // <-- bloquea y devuelve la respuesta
    }
}