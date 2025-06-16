package com.Proyecto_Venta_Mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MercadoPago {

    private List<MercadoPagoItem> items;
    // Si tienes un constructor con argumentos, aún necesitas este:

    // getters y setters

    public List<MercadoPagoItem> getItems() {
        return items;
    }

    public void setItems(List<MercadoPagoItem> items) {
        this.items = items;
    }
    private String cliente;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public static class Item {
        private String title;
        private Integer quantity;
        private BigDecimal unit_price;

        // getters y setters


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(BigDecimal unit_price) {
            this.unit_price = unit_price;
        }
    }
}

