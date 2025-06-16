package com.Proyecto_Venta_Mercado.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemVentaDTO {

    private Long id;
    private String producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;
}
