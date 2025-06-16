package com.Proyecto_Venta_Mercado.controller;



import com.Proyecto_Venta_Mercado.dto.ItemVentaDTO;
import com.Proyecto_Venta_Mercado.dto.MercadoPago;
import com.Proyecto_Venta_Mercado.dto.VentaDTO;
import com.Proyecto_Venta_Mercado.services.MercadoPagoService;


import com.Proyecto_Venta_Mercado.services.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/mercadopago")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;
    private final VentaService ventaService;
    public MercadoPagoController(MercadoPagoService mercadoPagoService, VentaService ventaService) {
        this.mercadoPagoService = mercadoPagoService;
        this.ventaService = ventaService;
    }

    @PostMapping("/crear-preferencia")
    public ResponseEntity<Map<String, String>> crearPreferencia(@RequestBody MercadoPago request) {
        String initPoint = mercadoPagoService.crearPreferencia(request);
        Map<String, String> response = new HashMap<>();
        response.put("init_point", initPoint);
        return ResponseEntity.ok(response);
    }
    // NUEVO: Webhook de MercadoPago
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestParam(name = "id") String paymentId,
                                                 @RequestParam(name = "topic") String topic) {
        System.out.println("Webhook recibido - Payment ID: " + paymentId + ", Topic: " + topic);

        if ("payment".equals(topic)) {
            try {
                Map<String, Object> paymentInfo = mercadoPagoService.procesarPago(paymentId);
                String status = (String) paymentInfo.get("status");
                BigDecimal total = BigDecimal.valueOf(Long.parseLong(paymentInfo.get("transaction_amount").toString()));

                if ("approved".equals(status)) {
                    System.out.println("Pago aprobado por " + total);

                    VentaDTO ventaDTO = new VentaDTO();
                    ventaDTO.setTotal(total);
                    ventaDTO.setFecha(LocalDateTime.now());


                    // Recuperar cliente desde metadata
                    Map<String, Object> metadata = (Map<String, Object>) paymentInfo.get("metadata");
                    ventaDTO.setCliente(metadata.get("cliente").toString());

                    // Recuperamos el carrito desde metadata
                    List<Map<String, Object>> carrito = (List<Map<String, Object>>) metadata.get("carrito");

                    List<ItemVentaDTO> detalle = carrito.stream().map(item -> {
                        ItemVentaDTO itemVenta = new ItemVentaDTO();
                        itemVenta.setProducto(item.get("title").toString());
                        itemVenta.setCantidad(Integer.parseInt(item.get("quantity").toString()));
                        itemVenta.setPrecioUnitario(BigDecimal.valueOf(Double.parseDouble(item.get("unit_price").toString())));
                        return itemVenta;
                    }).collect(Collectors.toList());

                    // Guardamos la venta
                    ventaService.save(ventaDTO);
                } else {
                    System.out.println("Pago no aprobado: " + status);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error al procesar el pago");
            }
        }

        return ResponseEntity.ok("Webhook recibido correctamente");
    }
}
