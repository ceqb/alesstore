package com.Proyecto_Venta_Mercado.dto;

import com.Proyecto_Venta_Mercado.model.ItemVenta;
import com.Proyecto_Venta_Mercado.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class VentaMapper {

    public VentaDTO toDTO(Venta venta) {
        if (venta == null) return null;

        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .cliente(venta.getCliente())
                .metodoPago(venta.getMetodoPago())
                .total(venta.getTotal())
                .items(toItemVentaDTOList(venta.getItems()))
                .build();
    }

    public Venta toEntity(VentaDTO dto) {
        if (dto == null) return null;

        return Venta.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .cliente(dto.getCliente())
                .metodoPago(dto.getMetodoPago())
                .total(dto.getTotal())
                .items(toItemVentaEntityList(dto.getItems()))
                .build();
    }

    public List<VentaDTO> toDTOList(List<Venta> ventas) {
        return ventas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Venta> toEntityList(List<VentaDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    // ------------ Mappers de ItemVenta ------------

    private List<ItemVentaDTO> toItemVentaDTOList(List<ItemVenta> items) {
        if (items == null) return null;

        return items.stream()
                .map(item -> ItemVentaDTO.builder()
                        .id(item.getId())
                        .producto(item.getProducto())
                        .cantidad(item.getCantidad())
                        .precioUnitario(item.getPrecioUnitario())
                        .total(item.getTotal())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ItemVenta> toItemVentaEntityList(List<ItemVentaDTO> items) {
        if (items == null) return null;

        return items.stream()
                .map(dto -> ItemVenta.builder()
                        .id(dto.getId())
                        .producto(dto.getProducto())
                        .cantidad(dto.getCantidad())
                        .precioUnitario(dto.getPrecioUnitario())
                        .total(dto.getTotal())
                        .build())
                .collect(Collectors.toList());
    }

}
