-- Schema para FoodNet Database
-- Base de datos SQLite para el simulador de negocio de comida rápida

CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    precio REAL NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    telefono TEXT,
    email TEXT
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id INTEGER NOT NULL,
    fecha TEXT NOT NULL,
    total REAL NOT NULL DEFAULT 0.0,
    estado TEXT DEFAULT 'PENDIENTE',
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS pedido_productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pedido_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL DEFAULT 1,
    precio_unitario REAL NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Datos iniciales de productos
INSERT INTO productos (nombre, precio, stock) VALUES
('Hamburguesa Clásica', 15.50, 20),
('Pizza Margarita', 25.00, 15),
('Tacos Mexicanos', 8.75, 30),
('Refresco Cola', 3.50, 50),
('Papas Fritas', 6.00, 25);

-- Datos iniciales de clientes
INSERT INTO clientes (nombre, telefono, email) VALUES
('Juan Pérez', '5555-1234', 'juan@email.com'),
('María González', '5555-5678', 'maria@email.com');
