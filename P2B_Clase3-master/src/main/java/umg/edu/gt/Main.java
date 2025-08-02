package umg.edu.gt;

import com.negocio.models.Cliente;
import com.negocio.models.Pedido;
import com.negocio.models.Producto;
import com.negocio.services.InventarioService;
import com.negocio.services.PedidoService;
import com.negocio.db.DatabaseManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static InventarioService inventarioService;
    private static PedidoService pedidoService;
    private static Scanner scanner;
    private static Map<Integer, Integer> stockInicial = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("=== FOODNET - Simulador de Negocio de Comida Rápida ===");
        System.out.println("Fecha y hora actual: " + java.time.LocalDateTime.now());

        inventarioService = new InventarioService();
        pedidoService = new PedidoService(inventarioService);
        scanner = new Scanner(System.in);
        inicializarStockInicial();

        boolean continuar = true;
        while (continuar) {
            try {
                mostrarMenu();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        mostrarInventario();
                        break;
                    case 2:
                        crearNuevoPedido();
                        break;
                    case 3:
                        agregarProductoAPedido();
                        break;
                    case 4:
                        mostrarPedidos();
                        break;
                    case 5:
                        mostrarIngresos();
                        break;
                    case 6:
                        aplicarDescuentoAPedido();
                        break;
                    case 7:
                        agregarProductoAlInventario();
                        break;
                    case 8:
                        mostrarResumenInventario();
                        break;
                    case 9:
                        eliminarProductoDelInventario();
                        break;
                    case 10:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer en caso de excepción
            }
        }

        DatabaseManager.cerrarConexion();
        scanner.close();
        System.out.println("¡Gracias por usar FoodNet!");
    }

    private static void inicializarStockInicial() {
        for (Producto p : inventarioService.obtenerTodosLosProductos()) {
            stockInicial.put(p.getId(), p.getStock());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Ver inventario");
        System.out.println("2. Crear nuevo pedido");
        System.out.println("3. Agregar producto a pedido");
        System.out.println("4. Ver pedidos");
        System.out.println("5. Ver ingresos totales");
        System.out.println("6. Aplicar descuento a pedido");
        System.out.println("7. Agregar producto al inventario");
        System.out.println("8. Mostrar resumen de inventario");
        System.out.println("9. Eliminar producto del inventario");
        System.out.println("10. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarInventario() {
        System.out.println("\n--- INVENTARIO ---");
        for (Producto producto : inventarioService.obtenerProductosDisponibles()) {
            System.out.println(producto);
        }
    }

    private static void crearNuevoPedido() {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Teléfono del cliente: ");
        String telefono = scanner.nextLine();

        try {
            Cliente cliente = new Cliente(1, nombre, telefono);
            Pedido pedido = pedidoService.crearPedido(cliente);
            System.out.println("Pedido creado con ID: " + pedido.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarProductoAPedido() {
        System.out.print("ID del pedido: ");
        int pedidoId = scanner.nextInt();
        System.out.print("ID del producto: ");
        int productoId = scanner.nextInt();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        try {
            if (pedidoService.agregarProductoAPedido(pedidoId, productoId, cantidad)) {
                System.out.println("Producto agregado exitosamente");
            } else {
                System.out.println("Error al agregar producto (stock insuficiente o pedido no encontrado)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarPedidos() {
        System.out.println("\n--- PEDIDOS ---");
        pedidoService.mostrarPedidos();
    }

    private static void mostrarIngresos() {
        double ingresos = pedidoService.calcularIngresosTotales();
        System.out.println("Ingresos totales: Q" + ingresos);
    }

    private static void aplicarDescuentoAPedido() {
        System.out.print("ID del pedido: ");
        int pedidoId = scanner.nextInt();
        System.out.print("Porcentaje de descuento (0-100): ");
        double descuento = scanner.nextDouble();

        try {
            if (descuento < 0 || descuento > 100) {
                throw new IllegalArgumentException("Descuento debe estar entre 0 y 100");
            }
            Pedido pedido = null;
            for (Pedido p : pedidoService.obtenerTodosLosPedidos()) {
                if (p.getId() == pedidoId) {
                    pedido = p;
                    break;
                }
            }
            if (pedido == null) {
                System.out.println("Pedido no encontrado");
                return;
            }
            double totalOriginal = pedido.getTotal();
            double descuentoMonto = totalOriginal * (descuento / 100);
            pedido.setTotal(totalOriginal - descuentoMonto);
            System.out.println("Descuento aplicado. Nuevo total: Q" + pedido.getTotal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarProductoAlInventario() {
        System.out.print("ID del producto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();

        try {
            Producto producto = new Producto(id, nombre, precio, stock);
            inventarioService.agregarProducto(producto);
            System.out.println("Producto agregado exitosamente: " + producto);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: Ya existe un producto con ese ID");
        }
    }

    private static void mostrarResumenInventario() {
        System.out.println("\n--- RESUMEN DE INVENTARIO ---");
        for (Producto producto : inventarioService.obtenerTodosLosProductos()) {
            int stockInicialProducto = stockInicial.getOrDefault(producto.getId(), 0);
            int stockActual = producto.getStock();
            int ventasEstimadas = stockInicialProducto - stockActual;
            System.out.println("Producto: " + producto.getNombre() +
                    ", Stock: " + stockActual +
                    ", Ventas: " + ventasEstimadas);
        }
    }

    private static void eliminarProductoDelInventario() {
        System.out.println("\n--- INVENTARIO ACTUAL ---");
        for (Producto producto : inventarioService.obtenerTodosLosProductos()) {
            System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Stock: " + producto.getStock());
        }

        System.out.print("\nID del producto a eliminar: ");
        int id = scanner.nextInt();

        try {
            Producto producto = inventarioService.buscarProductoPorId(id);
            if (producto == null) {
                System.out.println("Producto no encontrado");
                return;
            }
            System.out.print("¿Estás seguro de eliminar " + producto.getNombre() + "? (s/n): ");
            String confirmacion = scanner.next().toLowerCase();
            if (confirmacion.equals("s")) {
                if (inventarioService.eliminarProducto(id)) {
                    System.out.println("Producto eliminado exitosamente");
                    stockInicial.remove(id); // Actualizar stock inicial
                }
            } else {
                System.out.println("Eliminación cancelada");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}