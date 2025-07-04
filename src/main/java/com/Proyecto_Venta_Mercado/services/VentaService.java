package com.Proyecto_Venta_Mercado.services;


import com.Proyecto_Venta_Mercado.dto.VentaDTO;
import com.Proyecto_Venta_Mercado.model.Venta;


import java.util.List;

public interface VentaService {

    List<VentaDTO> listVenta(); //LISTAR CATEGORIA
    VentaDTO  save(VentaDTO ventaDTO); //GUARDAR CATEGORIA
    void delete(Long id); //ELIMINAR USUARIO POR ID
    Venta findById(Long id); //LISTAR POR ID
    //Venta update(Venta model);//ACTUALIZA USUARIOS POR ID
}
