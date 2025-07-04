package com.Proyecto_Venta_Mercado.services.impl;


import com.Proyecto_Venta_Mercado.dto.MercadoPago;
import com.Proyecto_Venta_Mercado.dto.MercadoPagoItem;
import com.Proyecto_Venta_Mercado.dto.VentaDTO;
import com.Proyecto_Venta_Mercado.dto.VentaMapper;
import com.Proyecto_Venta_Mercado.exception.ResourceNotFoundException;
import com.Proyecto_Venta_Mercado.model.Venta;
import com.Proyecto_Venta_Mercado.repository.VentaRepository;
import com.Proyecto_Venta_Mercado.services.MercadoPagoService;
import com.Proyecto_Venta_Mercado.services.VentaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final MercadoPagoService mercadoPagoService;

    @Override
    public List<VentaDTO> listVenta() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventaMapper.toDTOList(ventas);
    }
    // 🔥 Este es el método modificado con MercadoPago integrado
    public String saveVentaConPago(VentaDTO ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta.setFecha(LocalDateTime.now());
        Venta ventaGuardada = ventaRepository.save(venta);

        // Convertimos para MercadoPago
        MercadoPago requestMP = convertirVentaParaMercadoPago(ventaGuardada);
        String initPoint = mercadoPagoService.crearPreferencia(requestMP);

        return initPoint;
    }

    private MercadoPago convertirVentaParaMercadoPago(Venta venta) {
        List<MercadoPagoItem> items = venta.getItems().stream().map(item -> {
            return MercadoPagoItem.builder()
                    .title(item.getProducto())
                    .quantity(item.getCantidad())
                    .unit_price(item.getPrecioUnitario())
                    .build();
        }).collect(Collectors.toList());

        return MercadoPago.builder()
                .items(items)
                .build();
    }
    @Override
    public void actualizarEstadoPago(String externalReference) {
        Optional<Venta> ventaOptional = ventaRepository.findByExternalReference(externalReference);

        if (ventaOptional.isPresent()) {
            Venta venta = ventaOptional.get();
            venta.setMetodoPago("PAGADO");
            ventaRepository.save(venta);
        } else {
            throw new ResourceNotFoundException("No se encontró una venta con referencia: " + externalReference);
        }
    }
    @Override
    public VentaDTO save(VentaDTO  ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta.setFecha(LocalDateTime.now());
        Venta ventaGuardada = ventaRepository.save(venta);
        return ventaMapper.toDTO(ventaGuardada);
    }
/*Servicio con lógica de validación temporal

| Escenario                                                    | ¿Qué detecta? |
| ------------------------------------------------------------ | ------------- |
| Cliente cambia manualmente la hora del sistema               | ✅ Detectado   |
| Cliente realiza un pago antiguo y lo reintenta mucho después | ✅ Detectado   |
| Un error en el frontend manda una fecha vieja o futura       | ✅ Detectado   |

@Override
public Venta save(Venta model) {
    LocalDateTime ahora = LocalDateTime.now(); // hora del backend
    model.setFechaClienteBackend(ahora);       // guardar la hora del servidor

    if (model.getFechaClienteFrontend() != null) {
        Duration diferencia = Duration.between(model.getFechaClienteFrontend(), ahora);
        long segundos = Math.abs(diferencia.getSeconds());

        // Rechazar si hay más de 5 minutos de desfase (300 segundos)
        if (segundos > 300) {
            throw new IllegalArgumentException("La fecha/hora del cliente está fuera de rango permitido.");
        }
        ó
        if (segundos > 300) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "La fecha/hora del cliente está fuera del rango permitido.");
}
    }

    model.setFecha(ahora); // establecer la fecha oficial
    return ventaRepository.save(model);
}
*/
    @Override
    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public Venta findById(Long id) {
        Optional<Venta> venta =  ventaRepository.findById(id);
        //var user = userRepository.findById((id));
        if (venta.isEmpty()){
            return null;
        }else {
            return venta.get();
        }
    }

    /*@Override
    public Venta update(Venta model) {

        var ventaExiste = ventaRepository.findById((model.getId_habitacion()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found, cannot be updated id: " + model.getId_habitacion() )) ;

            ventaExiste.setFecha(model.getFecha());
            ventaExiste.setProducto(model.getProducto());
            ventaExiste.setCantidad(model.getCantidad());
            ventaExiste.setPrecioUnitario(model.getPrecioUnitario());
            //ventaExiste.setTotal(model.getTotal());
            ventaExiste.setCliente(model.getCliente());
            ventaExiste.setMetodoPago(model.getMetodoPago());

            return ventaRepository.save(ventaExiste);
    }*/
}
