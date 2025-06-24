package com.Proyecto_Venta_Mercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pruebas2")
public class PRUEBA2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pruebas")
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}


