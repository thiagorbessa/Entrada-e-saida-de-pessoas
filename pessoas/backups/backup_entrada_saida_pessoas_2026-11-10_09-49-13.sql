-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: entrada_saida_pessoas
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `log_auditoria`
--

DROP TABLE IF EXISTS `log_auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_auditoria` (
  `id` binary(16) NOT NULL,
  `acao` varchar(255) NOT NULL,
  `data_hora` datetime(6) DEFAULT NULL,
  `detalhes` text,
  `entidade` varchar(255) DEFAULT NULL,
  `entidade_descricao` varchar(255) DEFAULT NULL,
  `entidade_id` binary(16) DEFAULT NULL,
  `usuario_cpf` varchar(255) NOT NULL,
  `usuario_nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_log_data_hora` (`data_hora`),
  KEY `idx_log_usuario_cpf` (`usuario_cpf`),
  KEY `idx_log_entidade_id` (`entidade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_auditoria`
--

LOCK TABLES `log_auditoria` WRITE;
/*!40000 ALTER TABLE `log_auditoria` DISABLE KEYS */;
INSERT INTO `log_auditoria` VALUES (_binary 'Ωù\›6BJÄØ (´∂è∏','CRIACAO','2026-11-09 14:53:14.055355','Criou o utilizador com perfil ROLE_SISTEMA','USUARIO','gdfgds',_binary '\»rçùL?cØâ\ÃÜ,','00000000000','Administrador Geral'),(_binary 'A±◊ÜéIgùˇ˙™~`@B','CRIACAO','2026-11-09 15:14:42.760049','Criou o utilizador com perfil ROLE_USER','USUARIO','hgfhfd',_binary '\Ï\À\»~K\\2˛®0è\Ÿ\Úß∫\Û','09942622055','gdfgds'),(_binary 'I\Z†xêO≠©\r\–Núzâ\⁄','CRIACAO','2026-11-09 14:52:47.764505','Cadastrou a pessoa na unidade HC3','PESSOA','fed',_binary '£≥G\›tG÷Åò¿,|\“á','00000000000','Administrador Geral'),(_binary 'X,aõúDIß\»¯a.*M','EDICAO','2026-11-09 15:15:55.322627','Setor: \'informatica\' -> \'informatica dsjabndjkhabhjfas ndsajankj aka \'','PESSOA','fed1',_binary '£≥G\›tG÷Åò¿,|\“á','67503514094','hgfhfd'),(_binary 'x¯\ƒyBo®Y:Íæñ\ÿ','EDICAO','2026-11-09 14:25:27.137217','Unidade: \'HC2\' -> \'HC1\'','PESSOA','Thiago',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù','00000000000','Administrador Geral'),(_binary 'ÅSãS˘QE\‡ø\ˆ∆Ω=s£<','CRIACAO','2026-11-09 14:16:20.027968','Cadastrou a pessoa na unidade HC2','PESSOA','Thiago',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù','00000000000','Administrador Geral'),(_binary 'ü∂+KK≈áOjxc7\Ë∏','BACKUP_E_MANTER','2026-11-10 12:34:21.569050','Backup do ano 2026 conclu√≠do: 9 registros novos copiados para registros_2026 e 2 pessoas atualizadas em pessoas_2026.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary '\⁄\◊KG-ø®\œ\"K~\Ÿ\ÿ','CRIACAO','2026-11-09 17:05:17.221507','Cadastrou a pessoa na unidade HC4','PESSOA','fedaaaaaaaaa',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë','00000000000','Administrador Geral'),(_binary '˘(ëe∑∂B´ò\\\›{p¶|f','EDICAO','2026-11-09 14:53:42.531896','Nome: \'fed\' -> \'fed1\'','PESSOA','fed1',_binary '£≥G\›tG÷Åò¿,|\“á','00000000000','Administrador Geral');
/*!40000 ALTER TABLE `log_auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs_2025`
--

DROP TABLE IF EXISTS `logs_2025`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs_2025` (
  `id` binary(16) NOT NULL,
  `acao` varchar(255) NOT NULL,
  `data_hora` datetime(6) DEFAULT NULL,
  `detalhes` text,
  `entidade` varchar(255) DEFAULT NULL,
  `entidade_descricao` varchar(255) DEFAULT NULL,
  `entidade_id` binary(16) DEFAULT NULL,
  `usuario_cpf` varchar(255) NOT NULL,
  `usuario_nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs_2025`
--

LOCK TABLES `logs_2025` WRITE;
/*!40000 ALTER TABLE `logs_2025` DISABLE KEYS */;
INSERT INTO `logs_2025` VALUES (_binary 'Ωù\›6BJÄØ (´∂è∏','CRIACAO','2026-11-09 14:53:14.055355','Criou o utilizador com perfil ROLE_SISTEMA','USUARIO','gdfgds',_binary '\»rçùL?cØâ\ÃÜ,','00000000000','Administrador Geral'),(_binary 'A±◊ÜéIgùˇ˙™~`@B','CRIACAO','2026-11-09 15:14:42.760049','Criou o utilizador com perfil ROLE_USER','USUARIO','hgfhfd',_binary '\Ï\À\»~K\\2˛®0è\Ÿ\Úß∫\Û','09942622055','gdfgds'),(_binary 'I\Z†xêO≠©\r\–Núzâ\⁄','CRIACAO','2026-11-09 14:52:47.764505','Cadastrou a pessoa na unidade HC3','PESSOA','fed',_binary '£≥G\›tG÷Åò¿,|\“á','00000000000','Administrador Geral'),(_binary 'X,aõúDIß\»¯a.*M','EDICAO','2026-11-09 15:15:55.322627','Setor: \'informatica\' -> \'informatica dsjabndjkhabhjfas ndsajankj aka \'','PESSOA','fed1',_binary '£≥G\›tG÷Åò¿,|\“á','67503514094','hgfhfd'),(_binary 'x¯\ƒyBo®Y:Íæñ\ÿ','EDICAO','2026-11-09 14:25:27.137217','Unidade: \'HC2\' -> \'HC1\'','PESSOA','Thiago',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù','00000000000','Administrador Geral'),(_binary 'ÅSãS˘QE\‡ø\ˆ∆Ω=s£<','CRIACAO','2026-11-09 14:16:20.027968','Cadastrou a pessoa na unidade HC2','PESSOA','Thiago',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù','00000000000','Administrador Geral'),(_binary '\⁄\◊KG-ø®\œ\"K~\Ÿ\ÿ','CRIACAO','2026-11-09 17:05:17.221507','Cadastrou a pessoa na unidade HC4','PESSOA','fedaaaaaaaaa',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë','00000000000','Administrador Geral'),(_binary '˘(ëe∑∂B´ò\\\›{p¶|f','EDICAO','2026-11-09 14:53:42.531896','Nome: \'fed\' -> \'fed1\'','PESSOA','fed1',_binary '£≥G\›tG÷Åò¿,|\“á','00000000000','Administrador Geral');
/*!40000 ALTER TABLE `logs_2025` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `identidade` varchar(20) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhtm13k1mr18ijf4fld0gq1ok7` (`usuario_id`),
  CONSTRAINT `FKhtm13k1mr18ijf4fld0gq1ok7` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù','S1','35906292071','','Thiago','','funcionario','informatica','21972400853','HC1',1);
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoas`
--

DROP TABLE IF EXISTS `pessoas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoas` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `identidade` varchar(20) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_pessoa_cpf` (`cpf`),
  KEY `idx_pessoa_nome` (`nome`),
  KEY `idx_pessoa_unidade` (`unidade`),
  KEY `FK6jcu64t88706m5to8ih7mu934` (`usuario_id`),
  CONSTRAINT `FK6jcu64t88706m5to8ih7mu934` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoas`
--

LOCK TABLES `pessoas` WRITE;
/*!40000 ALTER TABLE `pessoas` DISABLE KEYS */;
INSERT INTO `pessoas` VALUES (_binary '£≥G\›tG÷Åò¿,|\“á','S1','62512041093','','fed1','','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','21972400853','HC3',3),(_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë','PILOTIS','35906292071','','fedaaaaaaaaa','','funcionario','informatica','21972400853','HC4',1);
/*!40000 ALTER TABLE `pessoas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoas_2025`
--

DROP TABLE IF EXISTS `pessoas_2025`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoas_2025` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `identidade` varchar(20) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoas_2025`
--

LOCK TABLES `pessoas_2025` WRITE;
/*!40000 ALTER TABLE `pessoas_2025` DISABLE KEYS */;
INSERT INTO `pessoas_2025` VALUES (_binary '£≥G\›tG÷Åò¿,|\“á','S1','62512041093','','fed1','','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','21972400853','HC3',3),(_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë','PILOTIS','35906292071','','fedaaaaaaaaa','','funcionario','informatica','21972400853','HC4',1);
/*!40000 ALTER TABLE `pessoas_2025` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoas_2026`
--

DROP TABLE IF EXISTS `pessoas_2026`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoas_2026` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `identidade` varchar(20) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_pessoa_cpf` (`cpf`),
  KEY `idx_pessoa_nome` (`nome`),
  KEY `idx_pessoa_unidade` (`unidade`),
  KEY `FK6jcu64t88706m5to8ih7mu934` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoas_2026`
--

LOCK TABLES `pessoas_2026` WRITE;
/*!40000 ALTER TABLE `pessoas_2026` DISABLE KEYS */;
INSERT INTO `pessoas_2026` VALUES (_binary '£≥G\›tG÷Åò¿,|\“á','S1','62512041093','','fed1','','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','21972400853','HC3',3),(_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë','PILOTIS','35906292071','','fedaaaaaaaaa','','funcionario','informatica','21972400853','HC4',1);
/*!40000 ALTER TABLE `pessoas_2026` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `hora_entrada` datetime(6) DEFAULT NULL,
  `hora_saida` datetime(6) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `pessoa_id` binary(16) DEFAULT NULL,
  `usuario_entrada_id` bigint DEFAULT NULL,
  `usuario_saida_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmu52iscijqjj3a5htrhtwxkhi` (`pessoa_id`),
  KEY `FKmk6746jli4ys0u1nl7wf45i39` (`usuario_entrada_id`),
  KEY `FKqjtw1mqryi2jo8ldxbwdy9698` (`usuario_saida_id`),
  CONSTRAINT `FKmk6746jli4ys0u1nl7wf45i39` FOREIGN KEY (`usuario_entrada_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKmu52iscijqjj3a5htrhtwxkhi` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `FKqjtw1mqryi2jo8ldxbwdy9698` FOREIGN KEY (`usuario_saida_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
INSERT INTO `registro` VALUES (_binary '%±©Ω—≥Dß\"¸∫t&≈µ','S1','2026-11-09 14:43:03.035882','2026-11-09 14:43:03.987864','funcionario','informatica','HC1',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù',1,1),(_binary 'áG\qPO∑)±w\Áì','S1','2026-11-09 14:42:47.388253','2026-11-09 14:42:48.425064','funcionario','informatica','HC1',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù',1,1),(_binary 'åÇæ\Õ\·\ÊK\n∂\√OÄ\0Åë¸','S1','2026-11-09 14:25:21.321392','2026-11-09 14:25:22.345811','funcionario','informatica','HC2',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù',1,1),(_binary '∏a£\“ ¯F€≠Øª\ı<›ºj','S1','2026-11-09 14:16:26.511724','2026-11-09 14:16:27.439645','funcionario','informatica','HC2',_binary '\ˆõ~G±ÉJ\Òë\ZA\Âk\Ù',1,1);
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros`
--

DROP TABLE IF EXISTS `registros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `hora_entrada` datetime(6) DEFAULT NULL,
  `hora_saida` datetime(6) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `pessoa_id` binary(16) DEFAULT NULL,
  `usuario_entrada_id` bigint DEFAULT NULL,
  `usuario_saida_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_registro_pessoa_id` (`pessoa_id`),
  KEY `idx_registro_hora_entrada` (`hora_entrada`),
  KEY `idx_registro_hora_saida` (`hora_saida`),
  KEY `FK30v16v3xx8w14q4mir9o15crn` (`usuario_entrada_id`),
  KEY `FKraes539oajndr0opx0amfskv8` (`usuario_saida_id`),
  CONSTRAINT `FK30v16v3xx8w14q4mir9o15crn` FOREIGN KEY (`usuario_entrada_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKbna3qgg3uasickblc9eie24uy` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoas` (`id`),
  CONSTRAINT `FKraes539oajndr0opx0amfskv8` FOREIGN KEY (`usuario_saida_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros`
--

LOCK TABLES `registros` WRITE;
/*!40000 ALTER TABLE `registros` DISABLE KEYS */;
INSERT INTO `registros` VALUES (_binary '\‰¥áíH	∂kp¡g‹üd','S1','2026-11-09 14:53:45.957898','2026-11-09 14:53:46.997570','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '$D\\0∫\0Ly≠KˇΩN¶˝','S1','2026-11-09 19:06:57.699494',NULL,'funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,NULL),(_binary '5.á≥\ﬂITÇ{\Ëå\"ãè','S1','2026-11-09 19:06:54.338902','2026-11-09 19:06:55.397825','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary 'I!3∞ˇLÇ\Ú-A@g\Ó','S1','2026-11-09 19:00:20.807654','2026-11-09 19:00:21.843520','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary ']∑≠§k\›F^Éj ß80Nc','PILOTIS','2026-11-09 19:00:23.027924','2026-11-09 19:00:23.780377','funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,1),(_binary 'àxU\Ú:UN\Zæ\»\«*∂ ü','S1','2026-11-09 14:53:58.211612','2026-11-09 14:53:59.044067','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',2,2),(_binary '≥\ÎR5,@¥öv4\rn\›\¬','PILOTIS','2026-11-09 18:33:42.260709','2026-11-09 18:33:43.233363','funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,1),(_binary 'ΩóuGúñKpø\‡XNÜº','PILOTIS','2026-11-09 19:06:56.275157',NULL,'funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,NULL),(_binary 'ø=\–\˜¢ùEy≠T†	\˜\Ô±4','S1','2026-11-09 18:33:38.744152','2026-11-09 18:33:40.482002','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\‹¨}¡BQàê!@)8@/','S1','2026-11-09 15:33:02.237252','2026-11-09 15:33:03.591097','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\·]\ﬁ ¥¥NÎß§˚5F','S1','2026-11-09 14:52:51.806912','2026-11-09 14:52:52.846695','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1);
/*!40000 ALTER TABLE `registros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros_2025`
--

DROP TABLE IF EXISTS `registros_2025`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros_2025` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `hora_entrada` datetime(6) DEFAULT NULL,
  `hora_saida` datetime(6) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `pessoa_id` binary(16) DEFAULT NULL,
  `usuario_entrada_id` bigint DEFAULT NULL,
  `usuario_saida_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros_2025`
--

LOCK TABLES `registros_2025` WRITE;
/*!40000 ALTER TABLE `registros_2025` DISABLE KEYS */;
INSERT INTO `registros_2025` VALUES (_binary '\‰¥áíH	∂kp¡g‹üd','S1','2026-11-09 14:53:45.957898','2026-11-09 14:53:46.997570','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary 'àxU\Ú:UN\Zæ\»\«*∂ ü','S1','2026-11-09 14:53:58.211612','2026-11-09 14:53:59.044067','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',2,2),(_binary '≥\ÎR5,@¥öv4\rn\›\¬','PILOTIS','2026-11-09 18:33:42.260709','2026-11-09 18:33:43.233363','funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,1),(_binary 'ø=\–\˜¢ùEy≠T†	\˜\Ô±4','S1','2026-11-09 18:33:38.744152','2026-11-09 18:33:40.482002','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\‹¨}¡BQàê!@)8@/','S1','2026-11-09 15:33:02.237252','2026-11-09 15:33:03.591097','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\·]\ﬁ ¥¥NÎß§˚5F','S1','2026-11-09 14:52:51.806912','2026-11-09 14:52:52.846695','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1);
/*!40000 ALTER TABLE `registros_2025` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros_2026`
--

DROP TABLE IF EXISTS `registros_2026`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros_2026` (
  `id` binary(16) NOT NULL,
  `andar` varchar(255) DEFAULT NULL,
  `hora_entrada` datetime(6) DEFAULT NULL,
  `hora_saida` datetime(6) DEFAULT NULL,
  `paciente` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  `pessoa_id` binary(16) DEFAULT NULL,
  `usuario_entrada_id` bigint DEFAULT NULL,
  `usuario_saida_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_registro_pessoa_id` (`pessoa_id`),
  KEY `idx_registro_hora_entrada` (`hora_entrada`),
  KEY `idx_registro_hora_saida` (`hora_saida`),
  KEY `FK30v16v3xx8w14q4mir9o15crn` (`usuario_entrada_id`),
  KEY `FKraes539oajndr0opx0amfskv8` (`usuario_saida_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros_2026`
--

LOCK TABLES `registros_2026` WRITE;
/*!40000 ALTER TABLE `registros_2026` DISABLE KEYS */;
INSERT INTO `registros_2026` VALUES (_binary '\‰¥áíH	∂kp¡g‹üd','S1','2026-11-09 14:53:45.957898','2026-11-09 14:53:46.997570','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '5.á≥\ﬂITÇ{\Ëå\"ãè','S1','2026-11-09 19:06:54.338902','2026-11-09 19:06:55.397825','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary 'I!3∞ˇLÇ\Ú-A@g\Ó','S1','2026-11-09 19:00:20.807654','2026-11-09 19:00:21.843520','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary ']∑≠§k\›F^Éj ß80Nc','PILOTIS','2026-11-09 19:00:23.027924','2026-11-09 19:00:23.780377','funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,1),(_binary 'àxU\Ú:UN\Zæ\»\«*∂ ü','S1','2026-11-09 14:53:58.211612','2026-11-09 14:53:59.044067','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',2,2),(_binary '≥\ÎR5,@¥öv4\rn\›\¬','PILOTIS','2026-11-09 18:33:42.260709','2026-11-09 18:33:43.233363','funcionario','informatica','HC4',_binary 'ß\Û*⁄ù©Kf≠\‡ù6∫∑˚ë',1,1),(_binary 'ø=\–\˜¢ùEy≠T†	\˜\Ô±4','S1','2026-11-09 18:33:38.744152','2026-11-09 18:33:40.482002','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\‹¨}¡BQàê!@)8@/','S1','2026-11-09 15:33:02.237252','2026-11-09 15:33:03.591097','funcionario','informatica dsjabndjkhabhjfas ndsajankj aka ','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1),(_binary '\·]\ﬁ ¥¥NÎß§˚5F','S1','2026-11-09 14:52:51.806912','2026-11-09 14:52:52.846695','funcionario','informatica','HC3',_binary '£≥G\›tG÷Åò¿,|\“á',1,1);
/*!40000 ALTER TABLE `registros_2026` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_unidades`
--

DROP TABLE IF EXISTS `usuario_unidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_unidades` (
  `usuario_id` bigint NOT NULL,
  `unidade` enum('COAD','CPQ','DIPAT','HC1','HC2','HC3','HC4','MP') DEFAULT NULL,
  KEY `FK90dqp1x0k98o12f1du6vqdl1o` (`usuario_id`),
  CONSTRAINT `FK90dqp1x0k98o12f1du6vqdl1o` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_unidades`
--

LOCK TABLES `usuario_unidades` WRITE;
/*!40000 ALTER TABLE `usuario_unidades` DISABLE KEYS */;
INSERT INTO `usuario_unidades` VALUES (2,'HC3'),(2,'HC2'),(3,'HC3'),(3,'HC2');
/*!40000 ALTER TABLE `usuario_unidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) NOT NULL,
  `nome_completo` varchar(255) NOT NULL,
  `perfil` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2et2smpfrtsohr7w9fe1v8a5e` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'00000000000','Administrador Geral','ROLE_GERAL','$2a$10$NY8Qkw70vfSLOZ5PiMzOWuTa08qHlr8e1iegK8bHy7guzIZVC0Gqe'),(2,'09942622055','gdfgds','ROLE_SISTEMA','$2a$10$7hExuvOytDAk89YL1aXRweu2JUoeMAzkrabnpq4gMgAX2YBIUnOK2'),(3,'67503514094','hgfhfd','ROLE_USER','$2a$10$XAWHC47LJw.Uqo0aL5oQkui1EfcoYrY.TSIKLn4LMTTr5tVZdNZem');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_2025`
--

DROP TABLE IF EXISTS `usuarios_2025`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_2025` (
  `id` bigint NOT NULL DEFAULT '0',
  `cpf` varchar(255) NOT NULL,
  `nome_completo` varchar(255) NOT NULL,
  `perfil` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_2025`
--

LOCK TABLES `usuarios_2025` WRITE;
/*!40000 ALTER TABLE `usuarios_2025` DISABLE KEYS */;
INSERT INTO `usuarios_2025` VALUES (1,'00000000000','Administrador Geral','ROLE_GERAL','$2a$10$NY8Qkw70vfSLOZ5PiMzOWuTa08qHlr8e1iegK8bHy7guzIZVC0Gqe'),(2,'09942622055','gdfgds','ROLE_SISTEMA','$2a$10$7hExuvOytDAk89YL1aXRweu2JUoeMAzkrabnpq4gMgAX2YBIUnOK2'),(3,'67503514094','hgfhfd','ROLE_USER','$2a$10$XAWHC47LJw.Uqo0aL5oQkui1EfcoYrY.TSIKLn4LMTTr5tVZdNZem');
/*!40000 ALTER TABLE `usuarios_2025` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-10  9:49:15
