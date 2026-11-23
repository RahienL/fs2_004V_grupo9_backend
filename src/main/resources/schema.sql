-- Crear base de datos
CREATE DATABASE IF NOT EXISTS pasteleria_db;
USE pasteleria_db;

-- Tabla CATEGORIA
CREATE TABLE IF NOT EXISTS categoria (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL UNIQUE,
  descripcion VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_nombre (nombre)
);

-- Tabla USUARIO
CREATE TABLE IF NOT EXISTS usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  telefono VARCHAR(20),
  fecha_nacimiento DATE,
  region VARCHAR(100),
  comuna VARCHAR(100),
  direccion VARCHAR(300),
  fecha_entrega_preferida DATE,
  rol ENUM('ADMIN', 'VENDEDOR', 'CLIENTE') DEFAULT 'CLIENTE',
  estado VARCHAR(20) DEFAULT 'ACTIVO',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_correo (correo),
  INDEX idx_rol (rol)
);

-- Tabla PRODUCTO
CREATE TABLE IF NOT EXISTS producto (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) NOT NULL UNIQUE,
  nombre VARCHAR(200) NOT NULL,
  descripcion VARCHAR(1000),
  precio DECIMAL(10, 2) NOT NULL,
  stock INT DEFAULT 0,
  categoria_id BIGINT NOT NULL,
  imagen VARCHAR(500),
  disponible BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE RESTRICT,
  INDEX idx_codigo (codigo),
  INDEX idx_categoria_id (categoria_id),
  INDEX idx_disponible (disponible)
);

-- Tabla BOLETA
CREATE TABLE IF NOT EXISTS boleta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10, 2) NOT NULL,
  estado ENUM('PENDIENTE', 'CONFIRMADA', 'PAGADA', 'ENVIADA', 'ENTREGADA', 'CANCELADA') DEFAULT 'PENDIENTE',
  observaciones VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT,
  INDEX idx_usuario_id (usuario_id),
  INDEX idx_estado (estado),
  INDEX idx_fecha (fecha)
);

-- Tabla DETALLE_BOLETA
CREATE TABLE IF NOT EXISTS detalle_boleta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  boleta_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10, 2) NOT NULL,
  subtotal DECIMAL(10, 2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (boleta_id) REFERENCES boleta(id) ON DELETE CASCADE,
  FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE RESTRICT,
  INDEX idx_boleta_id (boleta_id),
  INDEX idx_producto_id (producto_id)
);

-- Crear índices adicionales
CREATE INDEX idx_usuario_fecha ON boleta(usuario_id, fecha);
CREATE INDEX idx_boleta_estado_fecha ON boleta(estado, fecha);
