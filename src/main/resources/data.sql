-- Insertar categorías
INSERT INTO categoria (nombre, descripcion) VALUES
('Tortas', 'Tortas personalizadas y decoradas'),
('Pasteles', 'Pasteles variados'),
('Cupcakes', 'Cupcakes artesanales'),
('Galletas', 'Galletas decoradas y personalizadas'),
('Postres', 'Postres especiales y postres del día');

-- Insertar usuarios
INSERT INTO usuario (nombre, correo, password, telefono, fecha_nacimiento, region, comuna, direccion, rol, estado) VALUES
('Admin User', 'admin@pasteleria.cl', '$2a$10$slYQmyNdGzin7olVN3p5H.Eo5aeJE5H3YYk0dDgK3FYRvqLVt3BEm', '912345678', '1990-01-15', 'Metropolitana', 'Santiago', 'Av. Principal 100', 'ADMIN', 'ACTIVO'),
('Vendedor Test', 'vendedor@pasteleria.cl', '$2a$10$slYQmyNdGzin7olVN3p5H.Eo5aeJE5H3YYk0dDgK3FYRvqLVt3BEm', '987654321', '1992-05-20', 'Metropolitana', 'Las Condes', 'Calle Secundaria 200', 'VENDEDOR', 'ACTIVO'),
('Cliente Test', 'cliente@example.com', '$2a$10$slYQmyNdGzin7olVN3p5H.Eo5aeJE5H3YYk0dDgK3FYRvqLVt3BEm', '912223344', '1995-08-10', 'Metropolitana', 'Providencia', 'Calle Tercera 300', 'CLIENTE', 'ACTIVO');

-- Insertar productos
INSERT INTO producto (codigo, nombre, descripcion, precio, stock, categoria_id, disponible) VALUES
('TOR-001', 'Torta Chocolate Clásica', 'Torta de chocolate con cobertura de ganache', 35000, 10, 1, TRUE),
('TOR-002', 'Torta Fresa Vainilla', 'Torta de fresa con masa de vainilla', 32000, 8, 1, TRUE),
('PAS-001', 'Pastel Mil Hojas', 'Pastel tradicional chileno con crema pastelera', 15000, 20, 2, TRUE),
('PAS-002', 'Pastel de Choclo', 'Pastel de choclo con queso y aceitunas', 18000, 15, 2, TRUE),
('CUP-001', 'Cupcake Vainilla', 'Cupcake con frosting de vainilla', 5000, 50, 3, TRUE),
('CUP-002', 'Cupcake Chocolate', 'Cupcake con frosting de chocolate', 5500, 45, 3, TRUE),
('GAL-001', 'Galletas Decoradas x6', 'Galletas decoradas con diseños personalizados', 12000, 25, 4, TRUE),
('POST-001', 'Tiramisú', 'Postre italiano clásico', 8000, 12, 5, TRUE),
('POST-002', 'Brownie Especial', 'Brownie casero con nueces', 6500, 30, 5, TRUE),
('TOR-003', 'Torta Tres Leches', 'Torta de tres leches con crema', 38000, 7, 1, TRUE);

-- Insertar una boleta de prueba
INSERT INTO boleta (usuario_id, total, estado, observaciones) VALUES
(3, 45000, 'PENDIENTE', 'Entrega en Providencia, horario entre 10 y 12 hrs');

-- Insertar detalles de boleta
INSERT INTO detalle_boleta (boleta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 35000, 35000),
(1, 5, 2, 5000, 10000);
