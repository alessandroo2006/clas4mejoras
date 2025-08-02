// ERROR 1: Atributos públicos (Mala práctica de encapsulamiento)
Error #1 (Producto.java)
•	Corrección: Cambió atributos de public a private con getters/setters.
•	Explicación: Implementó encapsulamiento mediante acceso controlado, reemplazando modificaciones directas por métodos get y set con validaciones, mejorando la integridad de datos (OOP Principle: Encapsulation).

public class Producto {
private int id;
private String nombre;
private double precio;
private int stock;
public int getId() { return id; }
public String getNombre() { return nombre; }
public double getPrecio() { return precio; }
public int getStock() { return stock; }
public void setNombre(String nombre) {
if (nombre == null || nombre.trim().isEmpty()) {
throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
}
this.nombre = nombre.trim();
}
public void setPrecio(double precio) {
if (precio < 0) {
throw new IllegalArgumentException("El precio no puede ser negativo");
}
this.precio = precio;
}
}
Error #2 (Producto.java)
•	Corrección: Ajustó sintaxis del constructor y completó validaciones.
•	Explicación: Corrigió la estructura (int id, ...) en lugar de {) y añadió if condicionales con excepciones (IllegalArgumentException) para validar id, nombre, precio, y stock, asegurando inicialización segura.
// ERROR 2: Constructor con validaciones
public Producto(int id, String nombre, double precio, int stock) {
if (id <= 0) {
throw new IllegalArgumentException("El ID debe ser mayor que 0");
}
if (nombre == null || nombre.trim().isEmpty()) {
throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
}
if (precio < 0) {
throw new IllegalArgumentException("El precio no puede ser negativo");
}
if (stock < 0) {
throw new IllegalArgumentException("El stock no puede ser negativo");
}
this.id = id;
this.nombre = nombre.trim();
this.precio = precio;
this.stock = stock;
}

// ERROR 3: Bucle infinito potencial en reducirStock
Error #3 (Producto.java)
•	Corrección: Agregó validación en reducirStock.
•	Explicación: Insertó if (cantidad > stock) con IllegalStateException para prevenir subtracciones que dejen stock negativo, optimizando la lógica de inventario.

public void reducirStock(int cantidad) {
if (cantidad < 0) {
throw new IllegalArgumentException("Cantidad no puede ser negativa");
}
if (cantidad > stock) {
throw new IllegalStateException("Stock insuficiente");
}
stock -= cantidad;
}

// ERROR 4: No valida precios negativos en setPrecio
Error #4 (Producto.java)
•	Corrección: Añadió validación en setPrecio.
•	Explicación: Implementó if (precio < 0) con IllegalArgumentException para rechazar valores negativos, garantizando consistencia en los datos financieros.

public void setPrecio(double precio) {
if (precio < 0) {
throw new IllegalArgumentException("El precio no puede ser negativo");
}
this.precio = precio;
}

// ERROR 6: Condición incorrecta en hayStock
Error #6 (Producto.java)
•	Corrección: Corrigió condición en hayStock.
•	Explicación: Cambió stock = = cantidad (error de asignación) a stock >= cantidad, asegurando una comparación lógica para verificar disponibilidad.

public boolean hayStock(int cantidad) {
if (cantidad < 0) {
throw new IllegalArgumentException("La cantidad no puede ser negativa");
}
return stock >= cantidad;
}


// ERROR 7: Falta de validación de cantidades negativas en hayStock
Error #7 (Producto.java)
•	Corrección: Añadió validación en hayStock.
•	Explicación: Agregó if (cantidad < 0) con IllegalArgumentException para invalidar cantidades negativas, reforzando la robustez del método.

public boolean hayStock(int cantidad) {
if (cantidad < 0) {
throw new IllegalArgumentException("La cantidad no puede ser negativa");
}
return stock >= cantidad;
}
</CONTENT_FROM_OCR>
</PAGE1>
<PAGE2>
<CONTENT_FROM_OCR>
// ERROR 5: Calculo incorrecto del total (suma precios sin considerar cantidades)
Error #5 (Pedido.java)
•	Corrección: Modificó calcularTotal con Map<Producto, Integer>.
•	Explicación: Reemplazó la suma basada en stock por un bucle sobre entry.getKey().getPrecio() * entry.getValue(), reflejando cantidades pedidas en el total.
public class Pedido {
private int id;
private Cliente cliente;
private Map<Producto, Integer> productos;
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
if (!producto.hayStock(cantidad)) {
throw new IllegalStateException("Stock insuficiente");
}
productos.put(producto, productos.getOrDefault(producto, 0) + cantidad);
producto.reducirStock(cantidad);
calcularTotal();
}

private void calcularTotal() {
total = 0;
for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
total += entry.getKey().getPrecio() * entry.getValue();
}
}
 

Error #6 (Producto.java)
•	Corrección: Corrigió condición en hayStock.
•	Explicación: Cambió stock = = cantidad (error de asignación) a stock >= cantidad, asegurando una comparación lógica para verificar disponibilidad.

// ERROR 6: Constructor sin validaciones
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

// ERROR 7: Encapsulamiento débil en getProductos
Error #7 (Producto.java)
•	Corrección: Añadió validación en hayStock.
•	Explicación: Agregó if (cantidad < 0) con IllegalArgumentException para invalidar cantidades negativas, reforzando la robustez del método.
public Map<Producto, Integer> getProductos() {
return new HashMap<>(productos);
}

// ERROR 8: Bucle infinito potencial
Error #8 (InventarioService.java)
•	Corrección: Ajustó condición en buscarProductoPorId.
•	Explicación: Modificó while (i <= productos.size()) a i < productos.size() para evitar IndexOutOfBoundsException, optimizando el acceso al array.

public class InventarioService {
private List<Producto> productos;

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

// ERROR 9: No actualiza el stock después de la venta
Error #9 (InventarioService.java)
•	Corrección: Agregó actualización en venderProducto.
•	Explicación: Insertó producto.reducirStock(cantidad) tras validación, sincronizando el stock con las ventas realizadas.

public boolean venderProducto(int id, int cantidad) {
Producto producto = buscarProductoPorId(id);
if (producto != null &&

// ERROR 10: Constructor sin validaciones
Error #10 (InventarioService.java)
•	Corrección: Corrigió condición en obtenerProductosDisponibles.
•	Explicación: Cambió if (stock >= 0) a if (stock > 0) para excluir productos agotados, ajustando la lógica de disponibilidad.

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

// ERROR 11: Falta de actualización de stock
Error #11 (PedidoService.java)
•	Corrección: Cambió decremento a incremento en crearPedido.
•	Explicación: Reemplazó contadorPedidos-- por contadorPedidos++, asegurando IDs únicos y ascendentes con aritmética de enteros.

public void agregarProductoConCantidad(Producto producto, int cantidad) {
if (!producto.hayStock(cantidad)) {
throw new IllegalStateException("Stock insuficiente");
}
productos.put(producto, productos.getOrDefault(producto, 0) + cantidad);
producto.reducirStock(cantidad);
calcularTotal();
}
</CONTENT_FROM_OCR>
</PAGE2>
<PAGE3>
<CONTENT_FROM_OCR>
// Errores corregidos en PedidoService.java

// ERROR 11: Inicialización incorrecta de variables
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

// ERROR 12: Condición mal formulada en bucle.
Error #12 (PedidoService.java)
•	Corrección: Simplificó y corrigió bucle en agregarProductoAPedido.
•	Explicación: Eliminó el bucle i != cantidad (infinito potencial) y usó una sola llamada a venderProducto con cantidad total, mejorando eficiencia y control de flujo.
public boolean agregarProductoAPedido(int pedidoId, int productoId, int cantidad) {
Pedido pedido = buscarPedidoPorId(pedidoId);
if (pedido == null) return false;

Producto producto = inventarioService.buscarProductoPorId(productoId);
if (producto == null) return false;

if (cantidad <= 0) {
return false;
}
if (inventarioService.venderProducto(productoId, cantidad)) {
pedido.agregarProductoConCantidad(producto, cantidad);
return true;
}
return false;
}
}

 
# mejoras.md

## Mejora #5:
- **Ubicación**: `com.negocio.models.Producto.java` (método `reducirStock`) y `com.negocio.services.InventarioService.java` (método `venderProducto`)
- **Descripción del cambio**: Añadido chequeo adicional en `reducirStock` para asegurar que el stock no baje de 0, y ajustado `venderProducto` para validar stock antes de la reducción.
- **Justificación**: Previene estados inválidos de inventario (stock negativo), mejorando la integridad de los datos y evitando inconsistencias en el sistema.

## Mejora #6:
- **Ubicación**: `com.negocio.models.Producto.java` (métodos `setPrecio`, `reducirStock`, `hayStock`) y `umg.edu.gt.Main.java` (métodos de entrada)
- **Descripción del cambio**: Reforzada validación de precios y cantidades como positivos en `setPrecio`, `reducirStock`, y `hayStock`; añadida validación en `Main.java` para entradas de usuario en `agregarProductoAPedido` y `agregarProductoAlInventario`.
- **Justificación**: Garantiza que solo se acepten valores válidos, protegiendo contra errores de entrada y manipulaciones maliciosas, mejorando la seguridad del sistema.

## Mejora #7:
- **Ubicación**: `umg.edu.gt.Main.java` (método `mostrarResumenInventario`)
- **Descripción del cambio**: Implementado `mostrarResumenInventario` para listar productos, stock actual y ventas estimadas (stock inicial - stock actual), con opción 8 en el menú.
- **Justificación**: Proporciona una vista clara del estado del inventario, facilitando la toma de decisiones y la auditoría del negocio.

## Mejora #8:
- **Ubicación**: `umg.edu.gt.Main.java` (método `eliminarProductoDelInventario`)
- **Descripción del cambio**: Añadida solicitud de confirmación ("s/n") antes de eliminar un producto, con visualización previa del inventario.
- **Justificación**: Reduce el riesgo de eliminaciones accidentales, mejorando la seguridad y la experiencia del usuario.