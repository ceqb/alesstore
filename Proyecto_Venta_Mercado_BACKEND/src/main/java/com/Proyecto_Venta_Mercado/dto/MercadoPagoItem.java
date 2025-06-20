package com.Proyecto_Venta_Mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MercadoPagoItem {
    private String title;
    private Integer quantity;
    private BigDecimal unit_price;

}
