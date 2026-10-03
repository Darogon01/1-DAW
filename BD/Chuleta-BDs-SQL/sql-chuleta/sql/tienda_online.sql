-- ******************************************************************
-- BD DE PRÁCTICA: tienda_online
-- Para todos los ejemplos y misiones de la chuleta SQL.
--
-- 1. Crear la base de datos desde una Query Tool abierta en OTRA BD (p. ej. postgres):
--      CREATE DATABASE tienda_online;
-- 2. Abrir una Query Tool sobre tienda_online y ejecutar este script entero (F5).
--    Se puede volver a ejecutar las veces que haga falta: empieza borrando todo.
-- ******************************************************************

-- Limpieza (orden inverso a la creación: primero las tablas que dependen de otras)
DROP TABLE IF EXISTS lineas_pedido;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS empleados;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;
DROP TYPE IF EXISTS estado_pedido;

-- Tipo enumerado
CREATE TYPE estado_pedido AS ENUM ('pendiente', 'enviado', 'entregado', 'cancelado');

-- Tabla categorias
CREATE TABLE categorias (
    id_categoria  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre        VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla productos (1:N con categorias)
CREATE TABLE productos (
    id_producto   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_categoria  INTEGER NOT NULL REFERENCES categorias (id_categoria),
    nombre        VARCHAR(100) NOT NULL,
    precio        NUMERIC(8,2) NOT NULL CHECK (precio > 0),
    stock         INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    activo        BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_alta    DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Tabla clientes
CREATE TABLE clientes (
    id_cliente      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR(50)  NOT NULL,
    apellidos       VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    ciudad          VARCHAR(50),
    telefono        VARCHAR(15),
    fecha_registro  DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Tabla empleados (relación reflexiva: cada empleado puede tener un jefe que también es empleado)
CREATE TABLE empleados (
    id_empleado     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    puesto          VARCHAR(50)  NOT NULL,
    id_jefe         INTEGER REFERENCES empleados (id_empleado),
    salario         NUMERIC(8,2) NOT NULL CHECK (salario > 0),
    fecha_contrato  DATE NOT NULL
);

-- Tabla pedidos (1:N con clientes y con empleados)
CREATE TABLE pedidos (
    id_pedido     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_cliente    INTEGER NOT NULL REFERENCES clientes (id_cliente),
    id_empleado   INTEGER REFERENCES empleados (id_empleado) ON DELETE SET NULL,
    fecha         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado        estado_pedido NOT NULL DEFAULT 'pendiente',
    gastos_envio  NUMERIC(5,2) NOT NULL DEFAULT 0 CHECK (gastos_envio >= 0)
);

-- Tabla lineas_pedido (N:M entre pedidos y productos, con atributos de la relación)
CREATE TABLE lineas_pedido (
    id_pedido        INTEGER REFERENCES pedidos (id_pedido) ON DELETE CASCADE,
    id_producto      INTEGER REFERENCES productos (id_producto),
    cantidad         INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario  NUMERIC(8,2) NOT NULL CHECK (precio_unitario > 0),
    subtotal         NUMERIC(10,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    PRIMARY KEY (id_pedido, id_producto)
);

-- Datos
INSERT INTO categorias (nombre) VALUES
('Informática'), ('Periféricos'), ('Audio'), ('Hogar'), ('Gaming');

INSERT INTO productos (id_categoria, nombre, precio, stock, activo, fecha_alta) VALUES
(1, 'Portátil Lenovo IdeaPad 5',     649.00, 12, TRUE,  '2025-10-01'),
(1, 'Monitor LG 27" 4K',             329.90,  7, TRUE,  '2025-10-01'),
(2, 'Teclado mecánico Keychron K2',   89.99, 25, TRUE,  '2025-10-15'),
(2, 'Ratón Logitech MX Master 3S',    99.00, 30, TRUE,  '2025-10-15'),
(2, 'Webcam Full HD',                 45.50,  0, TRUE,  '2025-11-02'),
(3, 'Auriculares Sony WH-1000XM5',   349.00,  9, TRUE,  '2025-11-20'),
(3, 'Altavoz JBL Flip 6',            119.00, 15, TRUE,  '2026-01-10'),
(3, 'Micrófono USB',                  69.90,  4, TRUE,  '2026-01-10'),
(4, 'Robot aspirador',               279.00,  3, TRUE,  '2026-02-01'),
(4, 'Cafetera espresso',             189.50,  6, FALSE, '2026-02-01'),
(2, 'Hub USB-C 7 en 1',               39.90, 50, TRUE,  '2026-05-12'),
(1, 'SSD externo 1 TB',              109.00, 18, TRUE,  '2026-06-30');

INSERT INTO clientes (nombre, apellidos, email, ciudad, telefono, fecha_registro) VALUES
('Lucía',  'Fernández Gil',  'lucia.fernandez@correo.com', 'Madrid',    '600111222', '2025-11-03'),
('Marcos', 'Ruiz Torres',    'marcos.ruiz@correo.com',     'Barcelona', NULL,        '2025-12-15'),
('Elena',  'Navarro Pinto',  'elena.navarro@correo.com',   'Madrid',    '611222333', '2026-01-20'),
('Javier', 'Ortega Sanz',    'javier.ortega@correo.com',   'Valencia',  '622333444', '2026-02-02'),
('Sara',   'Molina Vidal',   'sara.molina@correo.com',     'Sevilla',   NULL,        '2026-03-11'),
('Pablo',  'Herrero Lago',   'pablo.herrero@correo.com',   'Bilbao',    '633444555', '2026-04-08'),
('Andrea', 'Campos Ríos',    'andrea.campos@correo.com',   NULL,        NULL,        '2026-06-19'),
('Diego',  'Santos Vera',    'diego.santos@correo.com',    'Madrid',    '644555666', '2026-09-01');

INSERT INTO empleados (nombre, puesto, id_jefe, salario, fecha_contrato) VALUES
('Carmen Rojas', 'Directora',           NULL, 3800.00, '2019-03-01'),
('Tomás Vidal',  'Jefe de ventas',      1,    2600.00, '2021-06-15'),
('Nuria Pons',   'Comercial',           2,    1850.00, '2023-01-09'),
('Iván Lara',    'Comercial',           2,    1800.00, '2024-09-02'),
('Rosa Gil',     'Atención al cliente', 1,    1650.00, '2025-02-17');

INSERT INTO pedidos (id_cliente, id_empleado, fecha, estado, gastos_envio) VALUES
(1, 3,    '2026-01-15 10:23', 'entregado', 0),
(2, 3,    '2026-02-03 18:40', 'entregado', 4.99),
(1, 4,    '2026-03-22 09:05', 'entregado', 0),
(3, 4,    '2026-04-10 21:15', 'entregado', 0),
(4, 3,    '2026-05-05 12:00', 'cancelado', 4.99),
(5, NULL, '2026-06-18 16:30', 'entregado', 4.99),
(3, 3,    '2026-07-02 11:45', 'enviado',   0),
(6, 4,    '2026-08-27 20:10', 'enviado',   0),
(1, NULL, '2026-09-14 08:55', 'pendiente', 0),
(2, 4,    '2026-09-30 13:20', 'pendiente', 4.99);

INSERT INTO lineas_pedido (id_pedido, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 649.00), (1, 4, 1, 99.00),
(2, 3, 1, 89.99),  (2, 5, 2, 45.50),
(3, 6, 1, 329.00),
(4, 2, 2, 329.90), (4, 4, 1, 95.00),
(5, 9, 1, 279.00),
(6, 7, 2, 119.00), (6, 8, 1, 69.90),
(7, 10, 1, 189.50), (7, 3, 1, 89.99),
(8, 1, 1, 629.00), (8, 6, 1, 349.00), (8, 4, 2, 99.00),
(9, 7, 1, 119.00),
(10, 8, 1, 69.90), (10, 3, 2, 85.00);

-- Comprobación rápida
SELECT 'categorias' AS tabla, COUNT(*) AS filas FROM categorias
UNION ALL SELECT 'productos', COUNT(*) FROM productos
UNION ALL SELECT 'clientes', COUNT(*) FROM clientes
UNION ALL SELECT 'empleados', COUNT(*) FROM empleados
UNION ALL SELECT 'pedidos', COUNT(*) FROM pedidos
UNION ALL SELECT 'lineas_pedido', COUNT(*) FROM lineas_pedido;
