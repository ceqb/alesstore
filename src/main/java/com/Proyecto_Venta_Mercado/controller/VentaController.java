package com.Proyecto_Venta_Mercado.controller;


import com.Proyecto_Venta_Mercado.dto.VentaDTO;
import com.Proyecto_Venta_Mercado.exception.BadRequestException;
import com.Proyecto_Venta_Mercado.exception.ResourceNotFoundException;
import com.Proyecto_Venta_Mercado.model.Venta;
import com.Proyecto_Venta_Mercado.payload.MensajeResponse;
import com.Proyecto_Venta_Mercado.services.MercadoPagoService;
import com.Proyecto_Venta_Mercado.services.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@ResponseStatus
@RestControllerAdvice
@RequestMapping("/ventas")
@CrossOrigin(origins = "*")
public class VentaController {


    private final VentaService ventaService;
    private final MercadoPagoService mercadoPagoService;
    @Autowired
    public VentaController(VentaService ventaService, MercadoPagoService mercadoPagoService) {
        this.ventaService = ventaService;
        this.mercadoPagoService = mercadoPagoService;
    }
    // Crear preferencia de MercadoPago (por ejemplo para checkout)
    @PostMapping("/mercadopago")
    public ResponseEntity<String> crearPreferencia(@RequestBody com.Proyecto_Venta_Mercado.dto.MercadoPago mercadoPagoRequest) {
        String initPoint = mercadoPagoService.crearPreferencia(mercadoPagoRequest);
        return ResponseEntity.ok(initPoint);
    }
    @PostMapping
    public ResponseEntity<VentaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        VentaDTO ventaGuardada  = ventaService.save(ventaDTO);
        return ResponseEntity.ok(ventaGuardada );
    }
    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        List<VentaDTO> ventas = ventaService.listVenta();
        return ResponseEntity.ok(ventas);
    }
    /*@GetMapping
    public ResponseEntity<MensajeResponse> habitacionList() {
        var getList = ventaService.listVenta();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("venta no found");
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("to list of ventas")
                .object(VentaDTO.toDto(ventaService.listVenta()))
                .build()
                , HttpStatus.OK);
    }
    @PostMapping
    public  ResponseEntity<MensajeResponse> save(@Valid @RequestBody VentaDTO ventaDTO){

        var model= Venta.toModel(ventaDTO);
        try {
            var models = ventaDTO.toDto(ventaService.save(model));
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("salved OK")
                    .object(models)
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }*/
}
