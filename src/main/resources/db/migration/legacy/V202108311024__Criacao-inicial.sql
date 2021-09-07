CREATE DATABASE  IF NOT EXISTS `ecopool` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ecopool`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: ecopool
-- ------------------------------------------------------
-- Server version	5.7.34-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coordenada`
--

DROP TABLE IF EXISTS `coordenada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coordenada` (
  `id_coord` bigint(20) NOT NULL AUTO_INCREMENT,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  PRIMARY KEY (`id_coord`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `destino`
--

DROP TABLE IF EXISTS `destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destino` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endereco` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `parceiro_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyexggmy43c8eygwnyjavmvdf` (`parceiro_id`),
  CONSTRAINT `FKdyexggmy43c8eygwnyjavmvdf` FOREIGN KEY (`parceiro_id`) REFERENCES `parceiro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parceiro`
--

DROP TABLE IF EXISTS `parceiro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parceiro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(255) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c9vx1r0odk420wv5cj96m0b5s` (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parceiro_usuarios`
--

DROP TABLE IF EXISTS `parceiro_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parceiro_usuarios` (
  `parceiro_id` bigint(20) NOT NULL,
  `usuarios_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_q8btkqxwcbcfs61fypymywamm` (`usuarios_id`),
  KEY `FKk6f0ic2jqtnm3cpbs12bwne4j` (`parceiro_id`),
  CONSTRAINT `FK4ko2j4ecc2136m1u2v8j8u71v` FOREIGN KEY (`usuarios_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKk6f0ic2jqtnm3cpbs12bwne4j` FOREIGN KEY (`parceiro_id`) REFERENCES `parceiro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `percurso`
--

DROP TABLE IF EXISTS `percurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `percurso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endereco_casa` varchar(255) NOT NULL,
  `horario` time NOT NULL,
  `modo_percurso` varchar(255) NOT NULL,
  `sentido_percurso` varchar(255) NOT NULL,
  `destino_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbb6dusek81jib0ro7nlsipaoi` (`destino_id`),
  CONSTRAINT `FKbb6dusek81jib0ro7nlsipaoi` FOREIGN KEY (`destino_id`) REFERENCES `destino` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `percurso_pontos`
--

DROP TABLE IF EXISTS `percurso_pontos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `percurso_pontos` (
  `percurso_id` bigint(20) NOT NULL,
  `pontos_id_coord` bigint(20) NOT NULL,
  UNIQUE KEY `UK_95cm0oj3remjw86nyphr5ir0a` (`pontos_id_coord`),
  KEY `FKlxf49blrdtrl38x5a2vpsqbeo` (`percurso_id`),
  CONSTRAINT `FKlxf49blrdtrl38x5a2vpsqbeo` FOREIGN KEY (`percurso_id`) REFERENCES `percurso` (`id`),
  CONSTRAINT `FKt2dp70fg8o2fl84w0xfgbk7fr` FOREIGN KEY (`pontos_id_coord`) REFERENCES `coordenada` (`id_coord`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `_tipo_usuario` varchar(255) DEFAULT NULL,
  `_tolerancia_distancia` bigint(20) DEFAULT NULL,
  `_tolerancia_tempo` int(11) DEFAULT NULL,
  `sexo` int(11) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `parceiro_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`),
  UNIQUE KEY `UK_86phslelq64eeo6insr50y422` (`telefone`),
  KEY `FK1syyxk1h2jlsde6gp8b5uy33g` (`parceiro_id`),
  CONSTRAINT `FK1syyxk1h2jlsde6gp8b5uy33g` FOREIGN KEY (`parceiro_id`) REFERENCES `parceiro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_percurso`
--

DROP TABLE IF EXISTS `usuario_percurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_percurso` (
  `usuario_id` bigint(20) NOT NULL,
  `percursos_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_t1gvblqsq4u5qbirpspx3bjld` (`percursos_id`),
  KEY `FKrvjcjodo41293nrry77cf2bjw` (`usuario_id`),
  CONSTRAINT `FK27hh0mvtm3llderwmg8vkfo15` FOREIGN KEY (`percursos_id`) REFERENCES `percurso` (`id`),
  CONSTRAINT `FKrvjcjodo41293nrry77cf2bjw` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'ecopool'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-07 13:31:50
