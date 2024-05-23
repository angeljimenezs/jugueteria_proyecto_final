USE  proyecto;
SELECT * FROM usuario;
SELECT * FROM rol;
SELECT * FROM usuario_rol_relation;

-- SELECT * FROM marca;
-- SELECT * FROM categoria;
-- SELECT * FROM producto;


-- SELECT * FROM pago;

-- SELECT * FROM direccion;

-- SELECT * FROM Estado_mexicano;


SELECT * FROM orden;

SELECT * FROM carrito;











/*
DROP TABLE IF EXISTS prueba;

CREATE TABLE prueba (
	id_prueba INT PRIMARY KEY AUTO_INCREMENT,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_usuario INT
);

INSERT INTO prueba VALUES  
(1, DEFAULT, 1),
(2, '2021-05-16 23:27:38', 1),
(3, DEFAULT, 2),
(4, '2023-05-16 23:27:38', 2),
(5, '2025-05-16 23:27:38', 2);

SELECT * FROM prueba WHERE id_usuario=2 ORDER BY fecha DESC;

*/