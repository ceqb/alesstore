package com.Proyecto_Venta_Mercado.model;


import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ventas")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "cliente", nullable = false)
    private String cliente;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenta> items;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "external_reference", unique = true)
    private String externalReference;
}
