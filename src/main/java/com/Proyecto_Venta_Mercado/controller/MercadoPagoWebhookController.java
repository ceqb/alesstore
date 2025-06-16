package com.Proyecto_Venta_Mercado.controller;

import com.Proyecto_Venta_Mercado.services.MercadoPagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class MercadoPagoWebhookController {
    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoWebhookController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Map<String, String>> crearPreferencia(@RequestBody Map<String, Object> data) {
        System.out.println("Webhook recibido: " + data);
        try {
            String tipoEvento = (String) data.get("type");
            Map<String, Object> dataMap = (Map<String, Object>) data.get("data");
            Long idPago = Long.valueOf(dataMap.get("id").toString());

            if ("payment".equals(tipoEvento)) {
                mercadoPagoService.procesarPago(String.valueOf(idPago));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }
}
