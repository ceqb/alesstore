package com.Proyecto_Venta_Mercado.dto;

import com.Proyecto_Venta_Mercado.model.Venta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO implements Serializable {

    private Long id;
    private LocalDateTime fecha;
    private String cliente;
    private String metodoPago;
    private List<ItemVentaDTO> items;
    private BigDecimal total;

    private String externalReference;

}
