package com.negocio.services;

import com.negocio.models.Cliente;
import com.negocio.models.Pedido;
import com.negocio.models.Producto;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private List<Pedido> pedidos;
    private InventarioService inventarioService;
    private int contadorPedidos;

    public PedidoService(InventarioService inventarioService) {
        this.pedidos = new ArrayList<>();
        this.inventarioService = inventarioService;
        this.contadorPedidos = 1;
    }

    public Pedido crearPedido(Cliente cliente) {
        Pedido pedido = new Pedido(contadorPedidos, cliente);
        contadorPedidos++; // Incrementa para el siguiente pedido
        pedidos.add(pedido);
        return pedido;
    }

    public boolean agregarProductoAPedido(int pedidoId, int productoId, int cantidad) {
        Pedido pedido = buscarPedidoPorId(pedidoId);
        if (pedido == null) return false;

        Producto producto = inventarioService.buscarProductoPorId(productoId);
        if (producto == null) return false;

        if (cantidad <= 0) {
            return false; // Validación adicional
        }
        if (inventarioService.venderProducto(productoId, cantidad)) {
            pedido.agregarProductoConCantidad(producto, cantidad); // Usa el método con cantidad
            return true;
        }
        return false;
    }

    private Pedido buscarPedidoPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public double calcularIngresosTotales() {
        double ingresos = 0;
        for (Pedido pedido : pedidos) {
            ingresos += pedido.getTotal();
        }
        return ingresos;
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return new ArrayList<>(pedidos); // Encapsulamiento mejorado
    }

    public void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }
}