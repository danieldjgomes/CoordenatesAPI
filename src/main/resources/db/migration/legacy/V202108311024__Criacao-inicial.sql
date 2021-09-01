CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo_usuario` varchar(255) DEFAULT NULL,
  `tolerancia_distancia` bigint(20) DEFAULT NULL,
  `tolerancia_tempo` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `coordinate` (
  `id_coord` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  PRIMARY KEY (`id_coord`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

CREATE TABLE `percurso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endereco_destino` varchar(255) DEFAULT NULL,
  `endereco_origem` varchar(255) DEFAULT NULL,
  `modo_percurso` varchar(255) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `horario_destino` time DEFAULT NULL,
  `horario_origem` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeig8d4nrmfsfirvcd4u142ahb` (`usuario_id`),
  CONSTRAINT `FKeig8d4nrmfsfirvcd4u142ahb` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `usuario_percursos` (
  `usuario_id` bigint(20) NOT NULL,
  `percursos_id` bigint(20) NOT NULL,
  `percursos_order` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`,`percursos_order`),
  UNIQUE KEY `UK_1m6m8rpyj7scsj4l3i1kmf64i` (`percursos_id`),
  CONSTRAINT `FKkaek44t0ld6ych3n4aaewc8yg` FOREIGN KEY (`percursos_id`) REFERENCES `percurso` (`id`),
  CONSTRAINT `FKqp85nkeyslx2ohhduqndlufvq` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `percurso_pontos` (
  `percurso_id` bigint(20) NOT NULL,
  `pontos_id_coord` bigint(20) NOT NULL,
  UNIQUE KEY `UK_95cm0oj3remjw86nyphr5ir0a` (`pontos_id_coord`),
  KEY `FKlxf49blrdtrl38x5a2vpsqbeo` (`percurso_id`),
  CONSTRAINT `FKlxf49blrdtrl38x5a2vpsqbeo` FOREIGN KEY (`percurso_id`) REFERENCES `percurso` (`id`),
  CONSTRAINT `FKs66132f2u5hpckwbpgq64ty1e` FOREIGN KEY (`pontos_id_coord`) REFERENCES `coordinate` (`id_coord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

