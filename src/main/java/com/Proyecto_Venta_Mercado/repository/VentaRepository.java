package com.Proyecto_Venta_Mercado.repository;

import com.Proyecto_Venta_Mercado.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long>{
    Optional<Venta> findByExternalReference(String externalReference);
}
