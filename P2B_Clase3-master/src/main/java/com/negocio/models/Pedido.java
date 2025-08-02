package com.negocio.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Map<Producto, Integer> productos; // Mapa de productos y cantidades
    private LocalDateTime fecha;
    private double total;

    public Pedido(int id, Cliente cliente) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser positivo");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser nulo");
        }
        this.id = id;
        this.cliente = cliente;
        this.productos = new HashMap<>();
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
    }

    public void agregarProductoConCantidad(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        if (producto == null) {
            throw new IllegalArgumentException("Producto no puede ser nulo");
        }
        if (!producto.hayStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente");
        }
        // Verificar si el producto ya está en el pedido por ID
        for (Producto p : productos.keySet()) {
            if (p.getId() == producto.getId()) {
                throw new IllegalStateException("El producto ya está en el pedido");
            }
        }
        productos.put(producto, cantidad);
        producto.reducirStock(cantidad);
        calcularTotal();
    }

    public void calcularTotal() {
        total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
    }

    public Map<Producto, Integer> getProductos() {
        return new HashMap<>(productos); // Devuelve una copia para evitar modificaciones externas
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", productos=" + productos +
                ", fecha=" + fecha +
                ", total=Q" + total +
                '}';
    }
}