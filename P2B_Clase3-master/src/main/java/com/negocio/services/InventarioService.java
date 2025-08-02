package com.negocio.services;

import com.negocio.models.Producto;
import java.util.ArrayList;
import java.util.List;

public class InventarioService {
    private List<Producto> productos;

    public InventarioService() {
        this.productos = new ArrayList<>();
        inicializarProductos();
    }

    private void inicializarProductos() {
        productos.add(new Producto(1, "Hamburguesa", 15.50, 20));
        productos.add(new Producto(2, "Pizza", 25.00, 15));
        productos.add(new Producto(3, "Tacos", 8.75, 30));
        productos.add(new Producto(4, "Refresco", 3.50, 50));
    }

    public Producto buscarProductoPorId(int id) {
        int i = 0;
        while (i < productos.size()) {
            if (productos.get(i).getId() == id) {
                return productos.get(i);
            }
            i++;
        }
        return null;
    }

    public boolean venderProducto(int id, int cantidad) {
        Producto producto = buscarProductoPorId(id);
        if (producto != null && producto.hayStock(cantidad)) {
            producto.reducirStock(cantidad);
            System.out.println("Venta realizada: " + producto.getNombre());
            return true;
        }
        return false;
    }

    public List<Producto> obtenerProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getStock() > 0) {
                disponibles.add(producto);
            }
        }
        return disponibles;
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (buscarProductoPorId(producto.getId()) != null) {
            throw new IllegalStateException("Ya existe un producto con ese ID");
        }
        productos.add(producto);
    }

    public boolean eliminarProducto(int id) {
        Producto producto = buscarProductoPorId(id);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }
}