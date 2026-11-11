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
INSERT INTO `log_auditoria` VALUES (_binary '\’\‰]\Ô˙J&•`πOQ\◊¿∂','EDICAO','2026-11-11 14:53:57.595435','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary ',=0˛\„M´Iß]\Èô\ı','EDICAO','2026-11-11 14:50:13.849071','Nenhum campo alterado','PESSOA','Ana Beatriz Ramos 120',_binary 'T&$˙\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '4RwÆìZFí†ùûág\≈t*','BACKUP_E_MANTER','2026-11-11 16:12:26.287459','Backup do ano 2026 conclu√≠do: 0 registros novos copiados para registros_2026, 361 pessoas atualizadas em pessoas_2026 e 14 logs gravados em logs_2026.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary '7\\“\‡C+¢\Êì0\Ëíl','EDICAO','2026-11-11 14:46:00.593349','Nenhum campo alterado','PESSOA','Ana Beatriz Dias 116',_binary 'T\"Jt\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '8c¥\˜éÖCü§}~ÖF}*','EDICAO','2026-11-11 14:50:06.532362','Nenhum campo alterado','PESSOA','Ana Beatriz Dias 8',_binary 'Sé?*\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '@≥8ÑhD%äM∂[k\Ë','EDICAO','2026-11-10 16:57:17.506869','Unidades: [HC2, HC3] -> [HC2, HC3, HC4]','USUARIO','gdfgds',_binary '\»rçùL?cØâ\ÃÜ,','00000000000','Administrador Geral'),(_binary '[ëÎ≤®\«Gì≥\‰z˙∏\Â\ÒR','EDICAO','2026-11-11 15:25:51.923048','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'impΩ~≥Gñ¨*‹ÉzΩ\0\Â','IMPORTAR_BACKUP','2026-11-11 16:16:59.411579','Importa√ß√£o conclu√≠da a partir de \'backup_entrada_saida_pessoas_2026-11-11_13-16-29.sql\'.','BACKUP','Importa√ß√£o de dump SQL',NULL,'00000000000','Administrador Geral'),(_binary 'r\–\ˆ\À2h@zè\‹\…˝\‡Ÿá','IMPORTAR_BACKUP','2026-11-11 16:17:46.949700','Importa√ß√£o conclu√≠da a partir de \'backup_entrada_saida_pessoas_2026-11-11_13-16-29.sql\'.','BACKUP','Importa√ß√£o de dump SQL',NULL,'00000000000','Administrador Geral'),(_binary '|b∑-m\ÕFõ±8∞JR7%','EDICAO','2026-11-10 18:20:16.875820','Nenhum campo alterado','PESSOA','Ana Beatriz da Silva 97',_binary 'TjD\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'èH-*\‹@K§±∞\Â\‚k\Ò','EDICAO','2026-11-11 14:06:26.217957','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'í\Ÿnw(AµöÜ\„\Î°f\˜','BACKUP_E_APAGAR','2026-11-11 16:17:19.472782','Backup do ano 2026 conclu√≠do: 0 registros novos copiados para registros_2026, 0 pessoas atualizadas em pessoas_2026 e 2 logs gravados em logs_2026. Removidos do sistema: 0 registros e 0 pessoas.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary 'ß§ÜNòI\"¨†\Î-´U','EDICAO','2026-11-11 14:44:11.165510','Identidade: \'ID16\' -> \'ID1655\'','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '´/›π\Ô\rH›≤∆†πÀ¥±','EXCLUSAO','2026-11-11 14:06:51.245670','Excluiu o registro da pessoa','PESSOA','Beatriz Viana 38',_binary 'S¿ˇE\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '≠vØ\'úKÀÖL¶%\\\Ãw†','BACKUP_E_MANTER','2026-11-11 15:55:20.070482','Backup do ano 2026 conclu√≠do: 214 registros novos copiados para registros_2026 e 361 pessoas atualizadas em pessoas_2026.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary '∂\Ú\ËVAîCZéƒ¥\Zƒªó','EDICAO','2026-11-11 14:50:19.839923','Nenhum campo alterado','PESSOA','Ana Beatriz Ribeiro 96',_binary 'T\rîg\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '∑\ÿ7\Ê\ÊGøê≥\».1!E','EDICAO','2026-11-11 14:55:21.115786','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '\‚p\»\«iÉJ¶∂\„2;◊åä','IMPORTAR_BACKUP','2026-11-11 16:20:36.368147','Importa√ß√£o conclu√≠da a partir de \'backup_entrada_saida_pessoas_2026-11-10_09-49-13.sql\'.','BACKUP','Importa√ß√£o de dump SQL',NULL,'00000000000','Administrador Geral'),(_binary '\Ù—ò°uJMãçu©¨ö∑j','EDICAO','2026-11-11 14:53:43.286337','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '˙ê7’º4H\\ñø\Òeß\‰\Ã\'','EDICAO','2026-11-11 16:23:17.307308','Nenhum campo alterado','PESSOA','Ana Beatriz Ramos 119',_binary '%¡XÅ\›\Ï\ÒÄfbõÉ','00000000000','Administrador Geral');
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
/*!40000 ALTER TABLE `logs_2025` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs_2026`
--

DROP TABLE IF EXISTS `logs_2026`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs_2026` (
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
-- Dumping data for table `logs_2026`
--

LOCK TABLES `logs_2026` WRITE;
/*!40000 ALTER TABLE `logs_2026` DISABLE KEYS */;
INSERT INTO `logs_2026` VALUES (_binary '\’\‰]\Ô˙J&•`πOQ\◊¿∂','EDICAO','2026-11-11 14:53:57.595435','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary ',=0˛\„M´Iß]\Èô\ı','EDICAO','2026-11-11 14:50:13.849071','Nenhum campo alterado','PESSOA','Ana Beatriz Ramos 120',_binary 'T&$˙\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '4RwÆìZFí†ùûág\≈t*','BACKUP_E_MANTER','2026-11-11 16:12:26.287459','Backup do ano 2026 conclu√≠do: 0 registros novos copiados para registros_2026, 361 pessoas atualizadas em pessoas_2026 e 14 logs gravados em logs_2026.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary '7\\“\‡C+¢\Êì0\Ëíl','EDICAO','2026-11-11 14:46:00.593349','Nenhum campo alterado','PESSOA','Ana Beatriz Dias 116',_binary 'T\"Jt\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '8c¥\˜éÖCü§}~ÖF}*','EDICAO','2026-11-11 14:50:06.532362','Nenhum campo alterado','PESSOA','Ana Beatriz Dias 8',_binary 'Sé?*\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '@≥8ÑhD%äM∂[k\Ë','EDICAO','2026-11-10 16:57:17.506869','Unidades: [HC2, HC3] -> [HC2, HC3, HC4]','USUARIO','gdfgds',_binary '\»rçùL?cØâ\ÃÜ,','00000000000','Administrador Geral'),(_binary '[ëÎ≤®\«Gì≥\‰z˙∏\Â\ÒR','EDICAO','2026-11-11 15:25:51.923048','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'impΩ~≥Gñ¨*‹ÉzΩ\0\Â','IMPORTAR_BACKUP','2026-11-11 16:16:59.411579','Importa√ß√£o conclu√≠da a partir de \'backup_entrada_saida_pessoas_2026-11-11_13-16-29.sql\'.','BACKUP','Importa√ß√£o de dump SQL',NULL,'00000000000','Administrador Geral'),(_binary '|b∑-m\ÕFõ±8∞JR7%','EDICAO','2026-11-10 18:20:16.875820','Nenhum campo alterado','PESSOA','Ana Beatriz da Silva 97',_binary 'TjD\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'èH-*\‹@K§±∞\Â\‚k\Ò','EDICAO','2026-11-11 14:06:26.217957','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary 'ß§ÜNòI\"¨†\Î-´U','EDICAO','2026-11-11 14:44:11.165510','Identidade: \'ID16\' -> \'ID1655\'','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '´/›π\Ô\rH›≤∆†πÀ¥±','EXCLUSAO','2026-11-11 14:06:51.245670','Excluiu o registro da pessoa','PESSOA','Beatriz Viana 38',_binary 'S¿ˇE\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '≠vØ\'úKÀÖL¶%\\\Ãw†','BACKUP_E_MANTER','2026-11-11 15:55:20.070482','Backup do ano 2026 conclu√≠do: 214 registros novos copiados para registros_2026 e 361 pessoas atualizadas em pessoas_2026.','BACKUP','Arquivamento do ano 2026',NULL,'00000000000','Administrador Geral'),(_binary '∂\Ú\ËVAîCZéƒ¥\Zƒªó','EDICAO','2026-11-11 14:50:19.839923','Nenhum campo alterado','PESSOA','Ana Beatriz Ribeiro 96',_binary 'T\rîg\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '∑\ÿ7\Ê\ÊGøê≥\».1!E','EDICAO','2026-11-11 14:55:21.115786','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral'),(_binary '\Ù—ò°uJMãçu©¨ö∑j','EDICAO','2026-11-11 14:53:43.286337','Nenhum campo alterado','PESSOA','Ana Beatriz Alencar 16',_binary 'Sõ\\∑\›3\Ò∑\√fµ\'\‰;','00000000000','Administrador Geral');
/*!40000 ALTER TABLE `logs_2026` ENABLE KEYS */;
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
INSERT INTO `pessoas` VALUES (_binary '%Mï]\›\Ï\ÒÄfbõÉ','S2','26086777110','ID1','Marcos Vinicius Mendes 1','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC2',1),(_binary '%Op•\›\Ï\ÒÄfbõÉ','PILOTIS','86662108260','ID2','Patrcia Lima 2','Gerado em massa - 200','familiar','Manuteno','21999998888','HC3',1),(_binary '%Pèu\›\Ï\ÒÄfbõÉ','S1','26580223367','ID3','Ana Beatriz da Silva 3','Gerado em massa - 200','paciente','Farmacia','21999998888','HC4',1),(_binary '%Ql\›\Ï\ÒÄfbõÉ','S2','01617404489','ID4','Marcos Vinicius Ribeiro 4','Gerado em massa - 200','funcionario','Oncologia','21999998888','COAD',1),(_binary '%Rå\›\Ï\ÒÄfbõÉ','PILOTIS','21083336576','ID5','Camila Ramos 5','Gerado em massa - 200','familiar','Informatica','21999998888','CPQ',1),(_binary '%S|\'\›\Ï\ÒÄfbõÉ','S1','72747737632','ID6','Patrcia Ramos 6','Gerado em massa - 200','paciente','Cardiologia','21999998888','DIPAT',1),(_binary '%TRR\›\Ï\ÒÄfbõÉ','S2','45284881753','ID7','Beatriz da Silva 7','Gerado em massa - 200','funcionario','Manuteno','21999998888','MP',1),(_binary '%Uã\›\Ï\ÒÄfbõÉ','PILOTIS','30728840804','ID8','Ricardo Ribeiro 8','Gerado em massa - 200','familiar','Farmacia','21999998888','HC1',1),(_binary '%VG\›\Ï\ÒÄfbõÉ','S1','84553700093','ID9','Ricardo Martins 9','Gerado em massa - 200','paciente','Oncologia','21999998888','HC2',1),(_binary '%V¯\Ÿ\›\Ï\ÒÄfbõÉ','S2','47430376118','ID10','Fernanda Ramos 10','Gerado em massa - 200','funcionario','Informatica','21999998888','HC3',1),(_binary '%X\›\Ï\ÒÄfbõÉ','PILOTIS','03586770251','ID11','Marcos Vinicius Rocha 11','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC4',1),(_binary '%Y#\›\Ï\ÒÄfbõÉ','S1','25233657317','ID12','Fernanda de Souza 12','Gerado em massa - 200','paciente','Manuteno','21999998888','COAD',1),(_binary '%Y‹æ\›\Ï\ÒÄfbõÉ','S2','88874761449','ID13','Patrcia Alencar 13','Gerado em massa - 200','funcionario','Farmacia','21999998888','CPQ',1),(_binary '%Z\„w\›\Ï\ÒÄfbõÉ','PILOTIS','44648581555','ID14','Beatriz Costa 14','Gerado em massa - 200','familiar','Oncologia','21999998888','DIPAT',1),(_binary '%[\∆\›\Ï\ÒÄfbõÉ','S1','53873433630','ID15','Patrcia de Souza 15','Gerado em massa - 200','paciente','Informatica','21999998888','MP',1),(_binary '%\\ª\r\›\Ï\ÒÄfbõÉ','S2','62061573770','ID16','Roberto da Silva 16','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC1',1),(_binary '%]¥\›\Ï\ÒÄfbõÉ','PILOTIS','57477601819','ID17','Joo Paulo Viana 17','Gerado em massa - 200','familiar','Manuteno','21999998888','HC2',1),(_binary '%^ü∫\›\Ï\ÒÄfbõÉ','S1','41527120074','ID18','Fernanda Viana 18','Gerado em massa - 200','paciente','Farmacia','21999998888','HC3',1),(_binary '%_å\›\Ï\ÒÄfbõÉ','S2','62301410196','ID19','Gabriel Rocha 19','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC4',1),(_binary '%`zô\›\Ï\ÒÄfbõÉ','PILOTIS','88036224208','ID20','Marcos Vinicius Martins 20','Gerado em massa - 200','familiar','Informatica','21999998888','COAD',1),(_binary '%aAT\›\Ï\ÒÄfbõÉ','S1','60036567353','ID21','Mariana da Silva 21','Gerado em massa - 200','paciente','Cardiologia','21999998888','CPQ',1),(_binary '%b\›\Ï\ÒÄfbõÉ','S2','72526635403','ID22','Ana Beatriz Mendes 22','Gerado em massa - 200','funcionario','Manuteno','21999998888','DIPAT',1),(_binary '%có\›\Ï\ÒÄfbõÉ','PILOTIS','31343540550','ID23','Patrcia Dias 23','Gerado em massa - 200','familiar','Farmacia','21999998888','MP',1),(_binary '%c\€\Ò\›\Ï\ÒÄfbõÉ','S1','73207762603','ID24','Mariana Costa 24','Gerado em massa - 200','paciente','Oncologia','21999998888','HC1',1),(_binary '%d\«\ \›\Ï\ÒÄfbõÉ','S2','62183160764','ID25','Gabriel Souza 25','Gerado em massa - 200','funcionario','Informatica','21999998888','HC2',1),(_binary '%e∑)\›\Ï\ÒÄfbõÉ','PILOTIS','31806021803','ID26','Ana Beatriz Viana 26','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC3',1),(_binary '%fìo\›\Ï\ÒÄfbõÉ','S1','44773654031','ID27','Camila Ribeiro 27','Gerado em massa - 200','paciente','Manuteno','21999998888','HC4',1),(_binary '%gX\›\Ï\ÒÄfbõÉ','S2','83016017105','ID28','Lucas Gabriel Oliveira Santos 28','Gerado em massa - 200','funcionario','Farmacia','21999998888','COAD',1),(_binary '%h;\›\Ï\ÒÄfbõÉ','PILOTIS','58732183232','ID29','Roberto Souza 29','Gerado em massa - 200','familiar','Oncologia','21999998888','CPQ',1),(_binary '%h˘¨\›\Ï\ÒÄfbõÉ','S1','60161547338','ID30','Juliana da Silva 30','Gerado em massa - 200','paciente','Informatica','21999998888','DIPAT',1),(_binary '%iŸÇ\›\Ï\ÒÄfbõÉ','S2','50181114453','ID31','Gabriel de Souza 31','Gerado em massa - 200','funcionario','Cardiologia','21999998888','MP',1),(_binary '%j√º\›\Ï\ÒÄfbõÉ','PILOTIS','82334344508','ID32','Ricardo de Souza 32','Gerado em massa - 200','familiar','Manuteno','21999998888','HC1',1),(_binary '%kï\Œ\›\Ï\ÒÄfbõÉ','S1','68681322630','ID33','Lucas Gabriel Souza 33','Gerado em massa - 200','paciente','Farmacia','21999998888','HC2',1),(_binary '%lw\Î\›\Ï\ÒÄfbõÉ','S2','46331361782','ID34','Gabriel de Souza 34','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC3',1),(_binary '%m^\‘\›\Ï\ÒÄfbõÉ','PILOTIS','06550761824','ID35','Marcos Vinicius Martins 35','Gerado em massa - 200','familiar','Informatica','21999998888','HC4',1),(_binary '%n3¥\›\Ï\ÒÄfbõÉ','S1','77341670045','ID36','Marcos Vinicius Souza 36','Gerado em massa - 200','paciente','Cardiologia','21999998888','COAD',1),(_binary '%o˙\›\Ï\ÒÄfbõÉ','S2','53330726121','ID37','Beatriz Costa e Silva 37','Gerado em massa - 200','funcionario','Manuteno','21999998888','CPQ',1),(_binary '%o\ÙG\›\Ï\ÒÄfbõÉ','PILOTIS','03454613205','ID38','Marcos Vinicius Costa 38','Gerado em massa - 200','familiar','Farmacia','21999998888','DIPAT',1),(_binary '%p\Ì\›\Ï\ÒÄfbõÉ','S1','28678126310','ID39','Ricardo Lima 39','Gerado em massa - 200','paciente','Oncologia','21999998888','MP',1),(_binary '%qµ\≈\›\Ï\ÒÄfbõÉ','S2','27845273449','ID40','Roberto Lima 40','Gerado em massa - 200','funcionario','Informatica','21999998888','HC1',1),(_binary '%r¶\\›\Ï\ÒÄfbõÉ','PILOTIS','28720370555','ID41','Camila Ramos 41','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC2',1),(_binary '%sî#\›\Ï\ÒÄfbõÉ','S1','74768707602','ID42','Eduardo Souza 42','Gerado em massa - 200','paciente','Manuteno','21999998888','HC3',1),(_binary '%t{ù\›\Ï\ÒÄfbõÉ','S2','87356858754','ID43','Carlos Alberto Rocha 43','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC4',1),(_binary '%uK}\›\Ï\ÒÄfbõÉ','PILOTIS','35532057842','ID44','Lucas Gabriel da Silva 44','Gerado em massa - 200','familiar','Oncologia','21999998888','COAD',1),(_binary '%vNC\›\Ï\ÒÄfbõÉ','S1','04127526033','ID45','Beatriz Mendes 45','Gerado em massa - 200','paciente','Informatica','21999998888','CPQ',1),(_binary '%wR\›\Ï\ÒÄfbõÉ','S2','71280162104','ID46','Gabriel Oliveira Santos 46','Gerado em massa - 200','funcionario','Cardiologia','21999998888','DIPAT',1),(_binary '%w\Ú†\›\Ï\ÒÄfbõÉ','PILOTIS','55128886255','ID47','Patrcia Dias 47','Gerado em massa - 200','familiar','Manuteno','21999998888','MP',1),(_binary '%x\”,\›\Ï\ÒÄfbõÉ','S1','17408524355','ID48','Joo Paulo Souza 48','Gerado em massa - 200','paciente','Farmacia','21999998888','HC1',1),(_binary '%y´\›\Ï\ÒÄfbõÉ','S2','10522720455','ID49','Lucas Gabriel Lima 49','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC2',1),(_binary '%zòs\›\Ï\ÒÄfbõÉ','PILOTIS','25246174590','ID50','Gabriel Costa e Silva 50','Gerado em massa - 200','familiar','Informatica','21999998888','HC3',1),(_binary '%{Y\Ò\›\Ï\ÒÄfbõÉ','S1','86810071652','ID51','Joo Paulo Ramos 51','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC4',1),(_binary '%|Z\›\Ï\ÒÄfbõÉ','S2','71325734705','ID52','Juliana Viana 52','Gerado em massa - 200','funcionario','Manuteno','21999998888','COAD',1),(_binary '%}ì\›\Ï\ÒÄfbõÉ','PILOTIS','53218108853','ID53','Marcos Vinicius Costa 53','Gerado em massa - 200','familiar','Farmacia','21999998888','CPQ',1),(_binary '%~\n\›\Ï\ÒÄfbõÉ','S1','36155183066','ID54','Ana Beatriz Souza 54','Gerado em massa - 200','paciente','Oncologia','21999998888','DIPAT',1),(_binary '%ö\›\Ï\ÒÄfbõÉ','S2','50621781185','ID55','Eduardo Viana 55','Gerado em massa - 200','funcionario','Informatica','21999998888','MP',1),(_binary '%\˜¡\›\Ï\ÒÄfbõÉ','PILOTIS','22272800262','ID56','Juliana de Souza 56','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC1',1),(_binary '%Ä\”\Û\›\Ï\ÒÄfbõÉ','S1','27475525350','ID57','Mariana Souza 57','Gerado em massa - 200','paciente','Manuteno','21999998888','HC2',1),(_binary '%Å°\Ë\›\Ï\ÒÄfbõÉ','S2','80448044471','ID58','Carlos Alberto Viana 58','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC3',1),(_binary '%Ç~Ç\›\Ï\ÒÄfbõÉ','PILOTIS','60028231589','ID59','Marcos Vinicius Costa e Silva 59','Gerado em massa - 200','familiar','Oncologia','21999998888','HC4',1),(_binary '%Éí¡\›\Ï\ÒÄfbõÉ','S1','03823218638','ID60','Juliana Martins 60','Gerado em massa - 200','paciente','Informatica','21999998888','COAD',1),(_binary '%Ñû\›\Ï\ÒÄfbõÉ','S2','06384685744','ID61','Carlos Alberto Souza 61','Gerado em massa - 200','funcionario','Cardiologia','21999998888','CPQ',1),(_binary '%ÖH\÷\›\Ï\ÒÄfbõÉ','PILOTIS','81126838837','ID62','Eduardo Mendes 62','Gerado em massa - 200','familiar','Manuteno','21999998888','DIPAT',1),(_binary '%Ü1°\›\Ï\ÒÄfbõÉ','S1','85574842084','ID63','Carlos Alberto Alencar 63','Gerado em massa - 200','paciente','Farmacia','21999998888','MP',1),(_binary '%Ü˙µ\›\Ï\ÒÄfbõÉ','S2','02824427159','ID64','Fernanda Mendes 64','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC1',1),(_binary '%á\ o\›\Ï\ÒÄfbõÉ','PILOTIS','42230161210','ID65','Fernanda Rocha 65','Gerado em massa - 200','familiar','Informatica','21999998888','HC2',1),(_binary '%à\ÙÜ\›\Ï\ÒÄfbõÉ','S1','54683342324','ID66','Roberto de Souza 66','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC3',1),(_binary '%â˘o\›\Ï\ÒÄfbõÉ','S2','05238670427','ID67','Juliana Costa e Silva 67','Gerado em massa - 200','funcionario','Manuteno','21999998888','HC4',1),(_binary '%ä\…\œ\›\Ï\ÒÄfbõÉ','PILOTIS','36603641500','ID68','Joo Paulo Rocha 68','Gerado em massa - 200','familiar','Farmacia','21999998888','COAD',1),(_binary '%ã\”\„\›\Ï\ÒÄfbõÉ','S1','48816356626','ID69','Gabriel Alencar 69','Gerado em massa - 200','paciente','Oncologia','21999998888','CPQ',1),(_binary '%åü\Œ\›\Ï\ÒÄfbõÉ','S2','57336616747','ID70','Lucas Gabriel Oliveira Santos 70','Gerado em massa - 200','funcionario','Informatica','21999998888','DIPAT',1),(_binary '%çÄ\›\Ï\ÒÄfbõÉ','PILOTIS','56517420851','ID71','Carlos Alberto Mendes 71','Gerado em massa - 200','familiar','Cardiologia','21999998888','MP',1),(_binary '%érí\›\Ï\ÒÄfbõÉ','S1','38476847017','ID72','Marcos Vinicius Costa e Silva 72','Gerado em massa - 200','paciente','Manuteno','21999998888','HC1',1),(_binary '%è^æ\›\Ï\ÒÄfbõÉ','S2','15526455173','ID73','Roberto Oliveira Santos 73','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC2',1),(_binary '%êBL\›\Ï\ÒÄfbõÉ','PILOTIS','06642747224','ID74','Camila Ribeiro 74','Gerado em massa - 200','familiar','Oncologia','21999998888','HC3',1),(_binary '%ë$™\›\Ï\ÒÄfbõÉ','S1','50158461347','ID75','Marcos Vinicius Mendes 75','Gerado em massa - 200','paciente','Informatica','21999998888','HC4',1),(_binary '%ë\€\‰\›\Ï\ÒÄfbõÉ','S2','87321101460','ID76','Fernanda Mendes 76','Gerado em massa - 200','funcionario','Cardiologia','21999998888','COAD',1),(_binary '%íΩ¶\›\Ï\ÒÄfbõÉ','PILOTIS','45675733582','ID77','Ricardo Costa e Silva 77','Gerado em massa - 200','familiar','Manuteno','21999998888','CPQ',1),(_binary '%ì\’\€\›\Ï\ÒÄfbõÉ','S1','77602810643','ID78','Ricardo Ribeiro 78','Gerado em massa - 200','paciente','Farmacia','21999998888','DIPAT',1),(_binary '%îòr\›\Ï\ÒÄfbõÉ','S2','70760356777','ID79','Carlos Alberto Mendes 79','Gerado em massa - 200','funcionario','Oncologia','21999998888','MP',1),(_binary '%ï©\«\›\Ï\ÒÄfbõÉ','PILOTIS','23162880815','ID80','Mariana Alencar 80','Gerado em massa - 200','familiar','Informatica','21999998888','HC1',1),(_binary '%ñf	\›\Ï\ÒÄfbõÉ','S1','02887340093','ID81','Mariana Ramos 81','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC2',1),(_binary '%óW\√\›\Ï\ÒÄfbõÉ','S2','58860281105','ID82','Ricardo Oliveira Santos 82','Gerado em massa - 200','funcionario','Manuteno','21999998888','HC3',1),(_binary '%ò∑\›\Ï\ÒÄfbõÉ','PILOTIS','40645232297','ID83','Fernanda Souza 83','Gerado em massa - 200','familiar','Farmacia','21999998888','HC4',1),(_binary '%ò\Áµ\›\Ï\ÒÄfbõÉ','S1','33433008370','ID84','Ricardo Martins 84','Gerado em massa - 200','paciente','Oncologia','21999998888','COAD',1),(_binary '%ô\¬\ı\›\Ï\ÒÄfbõÉ','S2','47602377478','ID85','Carlos Alberto Alencar 85','Gerado em massa - 200','funcionario','Informatica','21999998888','CPQ',1),(_binary '%öÇâ\›\Ï\ÒÄfbõÉ','PILOTIS','18043364591','ID86','Lucas Gabriel Souza 86','Gerado em massa - 200','familiar','Cardiologia','21999998888','DIPAT',1),(_binary '%õCã\›\Ï\ÒÄfbõÉ','S1','52505728631','ID87','Ricardo Souza 87','Gerado em massa - 200','paciente','Manuteno','21999998888','MP',1),(_binary '%úü\›\Ï\ÒÄfbõÉ','S2','30350435740','ID88','Juliana Alencar 88','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC1',1),(_binary '%ú\›N\›\Ï\ÒÄfbõÉ','PILOTIS','87008525893','ID89','Ana Beatriz Martins 89','Gerado em massa - 200','familiar','Oncologia','21999998888','HC2',1),(_binary '%ù∏Ü\›\Ï\ÒÄfbõÉ','S1','28015543088','ID90','Joo Paulo de Souza 90','Gerado em massa - 200','paciente','Informatica','21999998888','HC3',1),(_binary '%û€Ç\›\Ï\ÒÄfbõÉ','S2','35531722150','ID91','Roberto Rocha 91','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC4',1),(_binary '%ü«ç\›\Ï\ÒÄfbõÉ','PILOTIS','05735733281','ID92','Camila Rocha 92','Gerado em massa - 200','familiar','Manuteno','21999998888','COAD',1),(_binary '%†¡ä\›\Ï\ÒÄfbõÉ','S1','68266412324','ID93','Camila de Souza 93','Gerado em massa - 200','paciente','Farmacia','21999998888','CPQ',1),(_binary '%°æΩ\›\Ï\ÒÄfbõÉ','S2','35017684433','ID94','Patrcia Costa 94','Gerado em massa - 200','funcionario','Oncologia','21999998888','DIPAT',1),(_binary '%¢™!\›\Ï\ÒÄfbõÉ','PILOTIS','27288684504','ID95','Juliana Lima 95','Gerado em massa - 200','familiar','Informatica','21999998888','MP',1),(_binary '%£Ø\n\›\Ï\ÒÄfbõÉ','S1','45728017622','ID96','Juliana Oliveira Santos 96','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC1',1),(_binary '%§µz\›\Ï\ÒÄfbõÉ','S2','70811267741','ID97','Gabriel Costa e Silva 97','Gerado em massa - 200','funcionario','Manuteno','21999998888','HC2',1),(_binary '%•®\r\›\Ï\ÒÄfbõÉ','PILOTIS','00211267805','ID98','Mariana de Souza 98','Gerado em massa - 200','familiar','Farmacia','21999998888','HC3',1),(_binary '%¶ï\…\›\Ï\ÒÄfbõÉ','S1','32447761082','ID99','Marcos Vinicius Mendes 99','Gerado em massa - 200','paciente','Oncologia','21999998888','HC4',1),(_binary '%ßg∏\›\Ï\ÒÄfbõÉ','S2','66308473189','ID100','Marcos Vinicius Rocha 100','Gerado em massa - 200','funcionario','Informatica','21999998888','COAD',1),(_binary '%®o\Ÿ\›\Ï\ÒÄfbõÉ','PILOTIS','25058550210','ID101','Juliana de Souza 101','Gerado em massa - 200','familiar','Cardiologia','21999998888','CPQ',1),(_binary '%©W\›\Ï\ÒÄfbõÉ','S1','40618365362','ID102','Beatriz Viana 102','Gerado em massa - 200','paciente','Manuteno','21999998888','DIPAT',1),(_binary '%™Ä\√\›\Ï\ÒÄfbõÉ','S2','01671316487','ID103','Lucas Gabriel Mendes 103','Gerado em massa - 200','funcionario','Farmacia','21999998888','MP',1),(_binary '%´v>\›\Ï\ÒÄfbõÉ','PILOTIS','63133782533','ID104','Marcos Vinicius Oliveira Santos 104','Gerado em massa - 200','familiar','Oncologia','21999998888','HC1',1),(_binary '%¨>x\›\Ï\ÒÄfbõÉ','S1','57261356603','ID105','Beatriz Ribeiro 105','Gerado em massa - 200','paciente','Informatica','21999998888','HC2',1),(_binary '%≠z*\›\Ï\ÒÄfbõÉ','S2','21838676716','ID106','Mariana Alencar 106','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC3',1),(_binary '%Ø*T\›\Ï\ÒÄfbõÉ','PILOTIS','01614248800','ID107','Carlos Alberto Viana 107','Gerado em massa - 200','familiar','Manuteno','21999998888','HC4',1),(_binary '%±Ax\›\Ï\ÒÄfbõÉ','S1','42854356080','ID108','Ricardo Rocha 108','Gerado em massa - 200','paciente','Farmacia','21999998888','COAD',1),(_binary '%≤\◊u\›\Ï\ÒÄfbõÉ','S2','53731816172','ID109','Lucas Gabriel Costa e Silva 109','Gerado em massa - 200','funcionario','Oncologia','21999998888','CPQ',1),(_binary '%¥a\›\Ï\ÒÄfbõÉ','PILOTIS','85247553268','ID110','Eduardo da Silva 110','Gerado em massa - 200','familiar','Informatica','21999998888','DIPAT',1),(_binary '%µ\ˆ!\›\Ï\ÒÄfbõÉ','S1','66168538372','ID111','Joo Paulo Rocha 111','Gerado em massa - 200','paciente','Cardiologia','21999998888','MP',1),(_binary '%∂\ﬁ9\›\Ï\ÒÄfbõÉ','S2','48030785488','ID112','Camila Mendes 112','Gerado em massa - 200','funcionario','Manuteno','21999998888','HC1',1),(_binary '%∏Ç®\›\Ï\ÒÄfbõÉ','PILOTIS','04082745572','ID113','Beatriz Alencar 113','Gerado em massa - 200','familiar','Farmacia','21999998888','HC2',1),(_binary '%π˛ª\›\Ï\ÒÄfbõÉ','S1','46853133641','ID114','Carlos Alberto Oliveira Santos 114','Gerado em massa - 200','paciente','Oncologia','21999998888','HC3',1),(_binary '%ª_=\›\Ï\ÒÄfbõÉ','S2','20243615795','ID115','Marcos Vinicius de Souza 115','Gerado em massa - 200','funcionario','Informatica','21999998888','HC4',1),(_binary '%Ω\Ó\›\Ï\ÒÄfbõÉ','PILOTIS','12752658800','ID116','Gabriel Oliveira Santos 116','Gerado em massa - 200','familiar','Cardiologia','21999998888','COAD',1),(_binary '%æç\›\Ï\ÒÄfbõÉ','S1','48462887062','ID117','Lucas Gabriel Dias 117','Gerado em massa - 200','paciente','Manuteno','21999998888','CPQ',1),(_binary '%ø˘Æ\›\Ï\ÒÄfbõÉ','S2','87272755156','ID118','Mariana Dias 118','Gerado em massa - 200','funcionario','Farmacia','21999998888','DIPAT',1),(_binary '%¡XÅ\›\Ï\ÒÄfbõÉ','PILOTIS','38708644276','ID119','Ana Beatriz Ramos 119','Gerado em massa - 200','familiar','Oncologia','21999998888','MP',1),(_binary '%\¬W\·\›\Ï\ÒÄfbõÉ','S1','04072683329','ID120','Roberto da Silva 120','Gerado em massa - 200','paciente','Informatica','21999998888','HC1',1),(_binary '%\√1G\›\Ï\ÒÄfbõÉ','S2','35056858447','ID121','Beatriz Ramos 121','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC2',1),(_binary '%\ƒAX\›\Ï\ÒÄfbõÉ','PILOTIS','57686777553','ID122','Fernanda Martins 122','Gerado em massa - 200','familiar','Manuteno','21999998888','HC3',1),(_binary '%\≈6Ç\›\Ï\ÒÄfbõÉ','S1','46222606628','ID123','Juliana Martins 123','Gerado em massa - 200','paciente','Farmacia','21999998888','HC4',1),(_binary '%\∆x\›\Ï\ÒÄfbõÉ','S2','76834274707','ID124','Camila Martins 124','Gerado em massa - 200','funcionario','Oncologia','21999998888','COAD',1),(_binary '%\«\Ó\›\Ï\ÒÄfbõÉ','PILOTIS','27121774879','ID125','Gabriel Mendes 125','Gerado em massa - 200','familiar','Informatica','21999998888','CPQ',1),(_binary '%\«Õ±\›\Ï\ÒÄfbõÉ','S1','48021101032','ID126','Marcos Vinicius Costa e Silva 126','Gerado em massa - 200','paciente','Cardiologia','21999998888','DIPAT',1),(_binary '%»≥\Õ\›\Ï\ÒÄfbõÉ','S2','00176154108','ID127','Marcos Vinicius Costa e Silva 127','Gerado em massa - 200','funcionario','Manuteno','21999998888','MP',1),(_binary '%…Å\⁄\›\Ï\ÒÄfbõÉ','PILOTIS','22506037233','ID128','Beatriz Dias 128','Gerado em massa - 200','familiar','Farmacia','21999998888','HC1',1),(_binary '%\ =≤\›\Ï\ÒÄfbõÉ','S1','18372526370','ID129','Patrcia da Silva 129','Gerado em massa - 200','paciente','Oncologia','21999998888','HC2',1),(_binary '%\À\›\Ï\ÒÄfbõÉ','S2','35747818490','ID130','Joo Paulo de Souza 130','Gerado em massa - 200','funcionario','Informatica','21999998888','HC3',1),(_binary '%\À\„\›\›\Ï\ÒÄfbõÉ','PILOTIS','47042188560','ID131','Ana Beatriz Costa e Silva 131','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC4',1),(_binary '%Ã©s\›\Ï\ÒÄfbõÉ','S1','62314846613','ID132','Ana Beatriz Souza 132','Gerado em massa - 200','paciente','Manuteno','21999998888','COAD',1),(_binary '%Õ®5\›\Ï\ÒÄfbõÉ','S2','31552506746','ID133','Lucas Gabriel Costa e Silva 133','Gerado em massa - 200','funcionario','Farmacia','21999998888','CPQ',1),(_binary '%Œ•z\›\Ï\ÒÄfbõÉ','PILOTIS','72546168835','ID134','Camila Viana 134','Gerado em massa - 200','familiar','Oncologia','21999998888','DIPAT',1),(_binary '%œÇO\›\Ï\ÒÄfbõÉ','S1','57457134026','ID135','Fernanda de Souza 135','Gerado em massa - 200','paciente','Informatica','21999998888','MP',1),(_binary '%\–NB\›\Ï\ÒÄfbõÉ','S2','76871135197','ID136','Ana Beatriz da Silva 136','Gerado em massa - 200','funcionario','Cardiologia','21999998888','HC1',1),(_binary '%\—\n\ﬁ\›\Ï\ÒÄfbõÉ','PILOTIS','28246851233','ID137','Mariana de Souza 137','Gerado em massa - 200','familiar','Manuteno','21999998888','HC2',1),(_binary '%\—\„p\›\Ï\ÒÄfbõÉ','S1','46141217303','ID138','Joo Paulo Souza 138','Gerado em massa - 200','paciente','Farmacia','21999998888','HC3',1),(_binary '%“•\"\›\Ï\ÒÄfbõÉ','S2','10082885435','ID139','Joo Paulo Mendes 139','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC4',1),(_binary '%”ùó\›\Ï\ÒÄfbõÉ','PILOTIS','10813018560','ID140','Eduardo Ramos 140','Gerado em massa - 200','familiar','Informatica','21999998888','COAD',1),(_binary '%‘üd\›\Ï\ÒÄfbõÉ','S1','45681320618','ID141','Fernanda Costa 141','Gerado em massa - 200','paciente','Cardiologia','21999998888','CPQ',1),(_binary '%’ö±\›\Ï\ÒÄfbõÉ','S2','07757268770','ID142','Patrcia Lima 142','Gerado em massa - 200','funcionario','Manuteno','21999998888','DIPAT',1),(_binary '%\÷[£\›\Ï\ÒÄfbõÉ','PILOTIS','32876712865','ID143','Gabriel Costa 143','Gerado em massa - 200','familiar','Farmacia','21999998888','MP',1),(_binary '%\◊ T\›\Ï\ÒÄfbõÉ','S1','32313761061','ID144','Eduardo Martins 144','Gerado em massa - 200','paciente','Oncologia','21999998888','HC1',1),(_binary '%\ÿr\›\Ï\ÒÄfbõÉ','S2','82441154168','ID145','Marcos Vinicius Martins 145','Gerado em massa - 200','funcionario','Informatica','21999998888','HC2',1),(_binary '%\ÿ˘f\›\Ï\ÒÄfbõÉ','PILOTIS','62107615268','ID146','Juliana Viana 146','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC3',1),(_binary '%\Ÿ\€T\›\Ï\ÒÄfbõÉ','S1','21775456331','ID147','Marcos Vinicius Ramos 147','Gerado em massa - 200','paciente','Manuteno','21999998888','HC4',1),(_binary '%⁄©Y\›\Ï\ÒÄfbõÉ','S2','47210776451','ID148','Ana Beatriz de Souza 148','Gerado em massa - 200','funcionario','Farmacia','21999998888','COAD',1),(_binary '%\€dE\›\Ï\ÒÄfbõÉ','PILOTIS','54628106525','ID149','Roberto Oliveira Santos 149','Gerado em massa - 200','familiar','Oncologia','21999998888','CPQ',1),(_binary '%\‹¯\Ï\›\Ï\ÒÄfbõÉ','S1','76238677686','ID150','Camila Costa e Silva 150','Gerado em massa - 200','paciente','Informatica','21999998888','DIPAT',1),(_binary '%›ª\‰\›\Ï\ÒÄfbõÉ','S2','38456575704','ID151','Marcos Vinicius Costa 151','Gerado em massa - 200','funcionario','Cardiologia','21999998888','MP',1),(_binary '%ﬁ®\›\Ï\ÒÄfbõÉ','PILOTIS','34286637808','ID152','Ana Beatriz Ramos 152','Gerado em massa - 200','familiar','Manuteno','21999998888','HC1',1),(_binary '%ﬂè<\›\Ï\ÒÄfbõÉ','S1','16280567036','ID153','Beatriz Oliveira Santos 153','Gerado em massa - 200','paciente','Farmacia','21999998888','HC2',1),(_binary '%\‡å4\›\Ï\ÒÄfbõÉ','S2','58022334197','ID154','Patrcia Martins 154','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC3',1),(_binary '%\·~\"\›\Ï\ÒÄfbõÉ','PILOTIS','40710638230','ID155','Joo Paulo Martins 155','Gerado em massa - 200','familiar','Informatica','21999998888','HC4',1),(_binary '%\‚o\›\Ï\ÒÄfbõÉ','S1','84552285394','ID156','Roberto Costa e Silva 156','Gerado em massa - 200','paciente','Cardiologia','21999998888','COAD',1),(_binary '%\„L\Ó\›\Ï\ÒÄfbõÉ','S2','17342727407','ID157','Marcos Vinicius de Souza 157','Gerado em massa - 200','funcionario','Manuteno','21999998888','CPQ',1),(_binary '%\‰@¯\›\Ï\ÒÄfbõÉ','PILOTIS','08335748500','ID158','Fernanda Costa 158','Gerado em massa - 200','familiar','Farmacia','21999998888','DIPAT',1),(_binary '%\ÂF¨\›\Ï\ÒÄfbõÉ','S1','05887310642','ID159','Patrcia de Souza 159','Gerado em massa - 200','paciente','Oncologia','21999998888','MP',1),(_binary '%\Ê¿T\›\Ï\ÒÄfbõÉ','S2','60205871771','ID160','Marcos Vinicius de Souza 160','Gerado em massa - 200','funcionario','Informatica','21999998888','HC1',1),(_binary '%\Áï7\›\Ï\ÒÄfbõÉ','PILOTIS','12752734832','ID161','Ana Beatriz Ramos 161','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC2',1),(_binary '%Ë§ô\›\Ï\ÒÄfbõÉ','S1','22475662000','ID162','Roberto Mendes 162','Gerado em massa - 200','paciente','Manuteno','21999998888','HC3',1),(_binary '%\È{O\›\Ï\ÒÄfbõÉ','S2','67774250123','ID163','Eduardo Viana 163','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC4',1),(_binary '%\Íã\\\›\Ï\ÒÄfbõÉ','PILOTIS','48110073280','ID164','Roberto da Silva 164','Gerado em massa - 200','familiar','Oncologia','21999998888','COAD',1),(_binary '%\Î´\›\Ï\ÒÄfbõÉ','S1','56636252324','ID165','Joo Paulo Costa e Silva 165','Gerado em massa - 200','paciente','Informatica','21999998888','CPQ',1),(_binary '%\ÏÄF\›\Ï\ÒÄfbõÉ','S2','56784407482','ID166','Beatriz Dias 166','Gerado em massa - 200','funcionario','Cardiologia','21999998888','DIPAT',1),(_binary '%\ÌåP\›\Ï\ÒÄfbõÉ','PILOTIS','05720412506','ID167','Ricardo Dias 167','Gerado em massa - 200','familiar','Manuteno','21999998888','MP',1),(_binary '%\ÓS`\›\Ï\ÒÄfbõÉ','S1','50300725604','ID168','Roberto Mendes 168','Gerado em massa - 200','paciente','Farmacia','21999998888','HC1',1),(_binary '%\Ô>\Á\›\Ï\ÒÄfbõÉ','S2','87321706702','ID169','Juliana de Souza 169','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC2',1),(_binary '%\\Ò\›\Ï\ÒÄfbõÉ','PILOTIS','33083036809','ID170','Roberto de Souza 170','Gerado em massa - 200','familiar','Informatica','21999998888','HC3',1),(_binary '%\Ò52\›\Ï\ÒÄfbõÉ','S1','13446001077','ID171','Juliana Oliveira Santos 171','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC4',1),(_binary '%\Ú\ﬁ\›\Ï\ÒÄfbõÉ','S2','85821052157','ID172','Mariana Mendes 172','Gerado em massa - 200','funcionario','Manuteno','21999998888','COAD',1),(_binary '%\Û\›\Ï\ÒÄfbõÉ','PILOTIS','74755044251','ID173','Ricardo Oliveira Santos 173','Gerado em massa - 200','familiar','Farmacia','21999998888','CPQ',1),(_binary '%\Û\ﬂ\›\Ï\ÒÄfbõÉ','S1','37880601317','ID174','Beatriz Souza 174','Gerado em massa - 200','paciente','Oncologia','21999998888','DIPAT',1),(_binary '%\Ù\’\Œ\›\Ï\ÒÄfbõÉ','S2','66616338401','ID175','Ricardo Ribeiro 175','Gerado em massa - 200','funcionario','Informatica','21999998888','MP',1),(_binary '%\ı¨\'\›\Ï\ÒÄfbõÉ','PILOTIS','16028720585','ID176','Lucas Gabriel Costa e Silva 176','Gerado em massa - 200','familiar','Cardiologia','21999998888','HC1',1),(_binary '%\ˆ«µ\›\Ï\ÒÄfbõÉ','S1','86635210652','ID177','Gabriel Ramos 177','Gerado em massa - 200','paciente','Manuteno','21999998888','HC2',1),(_binary '%\˜±\€\›\Ï\ÒÄfbõÉ','S2','36433851703','ID178','Carlos Alberto Souza 178','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC3',1),(_binary '%¯£\›\Ï\ÒÄfbõÉ','PILOTIS','11342732820','ID179','Juliana Ribeiro 179','Gerado em massa - 200','familiar','Oncologia','21999998888','HC4',1),(_binary '%˘â@\›\Ï\ÒÄfbõÉ','S1','77532302040','ID180','Beatriz Souza 180','Gerado em massa - 200','paciente','Informatica','21999998888','COAD',1),(_binary '%˙x\›\›\Ï\ÒÄfbõÉ','S2','52847414126','ID181','Patrcia Costa 181','Gerado em massa - 200','funcionario','Cardiologia','21999998888','CPQ',1),(_binary '%˚å\‘\›\Ï\ÒÄfbõÉ','PILOTIS','27275271281','ID182','Joo Paulo Martins 182','Gerado em massa - 200','familiar','Manuteno','21999998888','DIPAT',1),(_binary '%¸ëÇ\›\Ï\ÒÄfbõÉ','S1','26085060386','ID183','Joo Paulo Souza 183','Gerado em massa - 200','paciente','Farmacia','21999998888','MP',1),(_binary '%˝~\›\Ï\ÒÄfbõÉ','S2','27267127460','ID184','Juliana Costa 184','Gerado em massa - 200','funcionario','Oncologia','21999998888','HC1',1),(_binary '%˛dã\›\Ï\ÒÄfbõÉ','PILOTIS','73634544503','ID185','Roberto Ribeiro 185','Gerado em massa - 200','familiar','Informatica','21999998888','HC2',1),(_binary '%ˇJ\›\Ï\ÒÄfbõÉ','S1','56710776646','ID186','Gabriel Souza 186','Gerado em massa - 200','paciente','Cardiologia','21999998888','HC3',1),(_binary '&\0£\›\Ï\ÒÄfbõÉ','S2','34322443710','ID187','Mariana Alencar 187','Gerado em massa - 200','funcionario','Manuteno','21999998888','HC4',1),(_binary '&V\›\Ï\ÒÄfbõÉ','PILOTIS','14113382867','ID188','Ricardo Ramos 188','Gerado em massa - 200','familiar','Farmacia','21999998888','COAD',1),(_binary '&Ct\›\Ï\ÒÄfbõÉ','S1','17747756038','ID189','Joo Paulo Lima 189','Gerado em massa - 200','paciente','Oncologia','21999998888','CPQ',1),(_binary '&	ª\›\Ï\ÒÄfbõÉ','S2','74761735112','ID190','Patrcia Costa e Silva 190','Gerado em massa - 200','funcionario','Informatica','21999998888','DIPAT',1),(_binary '&\À\›\Ï\ÒÄfbõÉ','PILOTIS','02258727227','ID191','Marcos Vinicius Costa e Silva 191','Gerado em massa - 200','familiar','Cardiologia','21999998888','MP',1),(_binary '&\ı\‘\›\Ï\ÒÄfbõÉ','S1','15785030357','ID192','Camila Costa 192','Gerado em massa - 200','paciente','Manuteno','21999998888','HC1',1),(_binary '&\Â*\›\Ï\ÒÄfbõÉ','S2','88875300410','ID193','Carlos Alberto Martins 193','Gerado em massa - 200','funcionario','Farmacia','21999998888','HC2',1),(_binary '&“ú\›\Ï\ÒÄfbõÉ','PILOTIS','08473416589','ID194','Camila Costa 194','Gerado em massa - 200','familiar','Oncologia','21999998888','HC3',1),(_binary '&º\Ï\›\Ï\ÒÄfbõÉ','S1','63757342666','ID195','Gabriel Souza 195','Gerado em massa - 200','paciente','Informatica','21999998888','HC4',1),(_binary '&¯¸\›\Ï\ÒÄfbõÉ','S2','48106434761','ID196','Mariana Martins 196','Gerado em massa - 200','funcionario','Cardiologia','21999998888','COAD',1),(_binary '&	»Ç\›\Ï\ÒÄfbõÉ','PILOTIS','06246676855','ID197','Fernanda Rocha 197','Gerado em massa - 200','familiar','Manuteno','21999998888','CPQ',1),(_binary '&\n¨!\›\Ï\ÒÄfbõÉ','S1','68530850009','ID198','Camila Ribeiro 198','Gerado em massa - 200','paciente','Farmacia','21999998888','DIPAT',1),(_binary '&õò\›\Ï\ÒÄfbõÉ','S2','70586645101','ID199','Lucas Gabriel Costa 199','Gerado em massa - 200','funcionario','Oncologia','21999998888','MP',1),(_binary '&ë\Ï\›\Ï\ÒÄfbõÉ','PILOTIS','08662106285','ID200','Roberto Mendes 200','Gerado em massa - 200','familiar','Informatica','21999998888','HC1',1);
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
INSERT INTO `registros` VALUES (_binary '%N\Õh\›\Ï\ÒÄfbõÉ','S2','2026-11-11 12:22:53.000000',NULL,'funcionario','Cardiologia','HC2',_binary '%Mï]\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%O˛n\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-11 11:22:53.000000','2026-11-11 12:07:53.000000','familiar','Manuteno','HC3',_binary '%Op•\›\Ï\ÒÄfbõÉ',1,1),(_binary '%P\ÏW\›\Ï\ÒÄfbõÉ','S1','2026-11-11 10:22:53.000000',NULL,'paciente','Farmacia','HC4',_binary '%Pèu\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Q\\›\Ï\ÒÄfbõÉ','S2','2026-11-11 09:22:53.000000','2026-11-11 10:07:53.000000','funcionario','Oncologia','COAD',_binary '%Ql\›\Ï\ÒÄfbõÉ',1,1),(_binary '%S\Z\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-11 08:22:53.000000',NULL,'familiar','Informatica','CPQ',_binary '%Rå\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Sÿ≠\›\Ï\ÒÄfbõÉ','S1','2026-11-11 07:22:53.000000','2026-11-11 08:07:53.000000','paciente','Cardiologia','DIPAT',_binary '%S|\'\›\Ï\ÒÄfbõÉ',1,1),(_binary '%T\«\·\›\Ï\ÒÄfbõÉ','S2','2026-11-11 06:22:53.000000',NULL,'funcionario','Manuteno','MP',_binary '%TRR\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Uìc\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-11 05:22:53.000000','2026-11-11 06:07:53.000000','familiar','Farmacia','HC1',_binary '%Uã\›\Ï\ÒÄfbõÉ',1,1),(_binary '%VgΩ\›\Ï\ÒÄfbõÉ','S1','2026-11-11 04:22:53.000000',NULL,'paciente','Oncologia','HC2',_binary '%VG\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Wü\ı\›\Ï\ÒÄfbõÉ','S2','2026-11-11 03:22:53.000000','2026-11-11 04:07:53.000000','funcionario','Informatica','HC3',_binary '%V¯\Ÿ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%Xîé\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-11 02:22:53.000000',NULL,'familiar','Cardiologia','HC4',_binary '%X\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Yg\Ú\›\Ï\ÒÄfbõÉ','S1','2026-11-11 01:22:53.000000','2026-11-11 02:07:53.000000','paciente','Manuteno','COAD',_binary '%Y#\›\Ï\ÒÄfbõÉ',1,1),(_binary '%Z^é\›\Ï\ÒÄfbõÉ','S2','2026-11-11 00:22:53.000000',NULL,'funcionario','Farmacia','CPQ',_binary '%Y‹æ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%[I=\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 23:22:53.000000','2026-11-11 00:07:53.000000','familiar','Oncologia','DIPAT',_binary '%Z\„w\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\\KÆ\›\Ï\ÒÄfbõÉ','S1','2026-11-10 22:22:53.000000',NULL,'paciente','Informatica','MP',_binary '%[\∆\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%]<9\›\Ï\ÒÄfbõÉ','S2','2026-11-10 21:22:53.000000','2026-11-10 22:07:53.000000','funcionario','Cardiologia','HC1',_binary '%\\ª\r\›\Ï\ÒÄfbõÉ',1,1),(_binary '%^+ü\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 20:22:53.000000',NULL,'familiar','Manuteno','HC2',_binary '%]¥\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%_∑\›\Ï\ÒÄfbõÉ','S1','2026-11-10 19:22:53.000000','2026-11-10 20:07:53.000000','paciente','Farmacia','HC3',_binary '%^ü∫\›\Ï\ÒÄfbõÉ',1,1),(_binary '%`\›\Ï\ÒÄfbõÉ','S2','2026-11-10 18:22:53.000000',NULL,'funcionario','Oncologia','HC4',_binary '%_å\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%`\E\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 17:22:53.000000','2026-11-10 18:07:53.000000','familiar','Informatica','COAD',_binary '%`zô\›\Ï\ÒÄfbõÉ',1,1),(_binary '%a´~\›\Ï\ÒÄfbõÉ','S1','2026-11-10 16:22:53.000000',NULL,'paciente','Cardiologia','CPQ',_binary '%aAT\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%bõµ\›\Ï\ÒÄfbõÉ','S2','2026-11-10 15:22:53.000000','2026-11-10 16:07:53.000000','funcionario','Manuteno','DIPAT',_binary '%b\›\Ï\ÒÄfbõÉ',1,1),(_binary '%cv\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 14:22:53.000000',NULL,'familiar','Farmacia','MP',_binary '%có\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%dMl\›\Ï\ÒÄfbõÉ','S1','2026-11-10 13:22:53.000000','2026-11-10 14:07:53.000000','paciente','Oncologia','HC1',_binary '%c\€\Ò\›\Ï\ÒÄfbõÉ',1,1),(_binary '%eN\›\Ï\ÒÄfbõÉ','S2','2026-11-10 12:22:53.000000',NULL,'funcionario','Informatica','HC2',_binary '%d\«\ \›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%f)t\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 11:22:53.000000','2026-11-10 12:07:53.000000','familiar','Cardiologia','HC3',_binary '%e∑)\›\Ï\ÒÄfbõÉ',1,1),(_binary '%g%\›\Ï\ÒÄfbõÉ','S1','2026-11-10 10:22:53.000000',NULL,'paciente','Manuteno','HC4',_binary '%fìo\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%g«ë\›\Ï\ÒÄfbõÉ','S2','2026-11-10 09:22:53.000000','2026-11-10 10:07:53.000000','funcionario','Farmacia','COAD',_binary '%gX\›\Ï\ÒÄfbõÉ',1,1),(_binary '%héU\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 08:22:53.000000',NULL,'familiar','Oncologia','CPQ',_binary '%h;\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%iW\›\Ï\ÒÄfbõÉ','S1','2026-11-10 07:22:53.000000','2026-11-10 08:07:53.000000','paciente','Informatica','DIPAT',_binary '%h˘¨\›\Ï\ÒÄfbõÉ',1,1),(_binary '%jV&\›\Ï\ÒÄfbõÉ','S2','2026-11-10 06:22:53.000000',NULL,'funcionario','Cardiologia','MP',_binary '%iŸÇ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%k)\Œ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 05:22:53.000000','2026-11-10 06:07:53.000000','familiar','Manuteno','HC1',_binary '%j√º\›\Ï\ÒÄfbõÉ',1,1),(_binary '%k˝\›\Ï\ÒÄfbõÉ','S1','2026-11-10 04:22:53.000000',NULL,'paciente','Farmacia','HC2',_binary '%kï\Œ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%l\Ït\›\Ï\ÒÄfbõÉ','S2','2026-11-10 03:22:53.000000','2026-11-10 04:07:53.000000','funcionario','Oncologia','HC3',_binary '%lw\Î\›\Ï\ÒÄfbõÉ',1,1),(_binary '%m\Ã\Ì\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-10 02:22:53.000000',NULL,'familiar','Informatica','HC4',_binary '%m^\‘\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%n£\÷\›\Ï\ÒÄfbõÉ','S1','2026-11-10 01:22:53.000000','2026-11-10 02:07:53.000000','paciente','Cardiologia','COAD',_binary '%n3¥\›\Ï\ÒÄfbõÉ',1,1),(_binary '%oà\›\Ï\ÒÄfbõÉ','S2','2026-11-10 00:22:53.000000',NULL,'funcionario','Manuteno','CPQ',_binary '%o˙\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%p\\¶\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 23:22:53.000000','2026-11-10 00:07:53.000000','familiar','Farmacia','DIPAT',_binary '%o\ÙG\›\Ï\ÒÄfbõÉ',1,1),(_binary '%qd\›\Ï\ÒÄfbõÉ','S1','2026-11-09 22:22:53.000000',NULL,'paciente','Oncologia','MP',_binary '%p\Ì\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%r 1\›\Ï\ÒÄfbõÉ','S2','2026-11-09 21:22:53.000000','2026-11-09 22:07:53.000000','funcionario','Informatica','HC1',_binary '%qµ\≈\›\Ï\ÒÄfbõÉ',1,1),(_binary '%s#D\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 20:22:53.000000',NULL,'familiar','Cardiologia','HC2',_binary '%r¶\\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%t¨\›\Ï\ÒÄfbõÉ','S1','2026-11-09 19:22:53.000000','2026-11-09 20:07:53.000000','paciente','Manuteno','HC3',_binary '%sî#\›\Ï\ÒÄfbõÉ',1,1),(_binary '%t\·\‹\›\Ï\ÒÄfbõÉ','S2','2026-11-09 18:22:53.000000',NULL,'funcionario','Farmacia','HC4',_binary '%t{ù\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%u\ﬂ\Â\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 17:22:53.000000','2026-11-09 18:07:53.000000','familiar','Oncologia','COAD',_binary '%uK}\›\Ï\ÒÄfbõÉ',1,1),(_binary '%v†\Ú\›\Ï\ÒÄfbõÉ','S1','2026-11-09 16:22:53.000000',NULL,'paciente','Informatica','CPQ',_binary '%vNC\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%w§¡\›\Ï\ÒÄfbõÉ','S2','2026-11-09 15:22:53.000000','2026-11-09 16:07:53.000000','funcionario','Cardiologia','DIPAT',_binary '%wR\›\Ï\ÒÄfbõÉ',1,1),(_binary '%xet\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 14:22:53.000000',NULL,'familiar','Manuteno','MP',_binary '%w\Ú†\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%y2j\›\Ï\ÒÄfbõÉ','S1','2026-11-09 13:22:53.000000','2026-11-09 14:07:53.000000','paciente','Farmacia','HC1',_binary '%x\”,\›\Ï\ÒÄfbõÉ',1,1),(_binary '%z0§\›\Ï\ÒÄfbõÉ','S2','2026-11-09 12:22:53.000000',NULL,'funcionario','Oncologia','HC2',_binary '%y´\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%z\Ë≥\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 11:22:53.000000','2026-11-09 12:07:53.000000','familiar','Informatica','HC3',_binary '%zòs\›\Ï\ÒÄfbõÉ',1,1),(_binary '%{\Ã\Ú\›\Ï\ÒÄfbõÉ','S1','2026-11-09 10:22:53.000000',NULL,'paciente','Cardiologia','HC4',_binary '%{Y\Ò\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%|†C\›\Ï\ÒÄfbõÉ','S2','2026-11-09 09:22:53.000000','2026-11-09 10:07:53.000000','funcionario','Manuteno','COAD',_binary '%|Z\›\Ï\ÒÄfbõÉ',1,1),(_binary '%}òÉ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 08:22:53.000000',NULL,'familiar','Farmacia','CPQ',_binary '%}ì\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%~ü[\›\Ï\ÒÄfbõÉ','S1','2026-11-09 07:22:53.000000','2026-11-09 08:07:53.000000','paciente','Oncologia','DIPAT',_binary '%~\n\›\Ï\ÒÄfbõÉ',1,1),(_binary '%è<\›\Ï\ÒÄfbõÉ','S2','2026-11-09 06:22:53.000000',NULL,'funcionario','Informatica','MP',_binary '%ö\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Äfç\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 05:22:53.000000','2026-11-09 06:07:53.000000','familiar','Cardiologia','HC1',_binary '%\˜¡\›\Ï\ÒÄfbõÉ',1,1),(_binary '%Å>†\›\Ï\ÒÄfbõÉ','S1','2026-11-09 04:22:53.000000',NULL,'paciente','Manuteno','HC2',_binary '%Ä\”\Û\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Ç\›\Ï\ÒÄfbõÉ','S2','2026-11-09 03:22:53.000000','2026-11-09 04:07:53.000000','funcionario','Farmacia','HC3',_binary '%Å°\Ë\›\Ï\ÒÄfbõÉ',1,1),(_binary '%É*\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-09 02:22:53.000000',NULL,'familiar','Oncologia','HC4',_binary '%Ç~Ç\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Ñ\nR\›\Ï\ÒÄfbõÉ','S1','2026-11-09 01:22:53.000000','2026-11-09 02:07:53.000000','paciente','Informatica','COAD',_binary '%Éí¡\›\Ï\ÒÄfbõÉ',1,1),(_binary '%Ñ\Ù\›\Ï\ÒÄfbõÉ','S2','2026-11-09 00:22:53.000000',NULL,'funcionario','Cardiologia','CPQ',_binary '%Ñû\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Ö¥¿\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 23:22:53.000000','2026-11-09 00:07:53.000000','familiar','Manuteno','DIPAT',_binary '%ÖH\÷\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ÜâH\›\Ï\ÒÄfbõÉ','S1','2026-11-08 22:22:53.000000',NULL,'paciente','Farmacia','MP',_binary '%Ü1°\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%án3\›\Ï\ÒÄfbõÉ','S2','2026-11-08 21:22:53.000000','2026-11-08 22:07:53.000000','funcionario','Oncologia','HC1',_binary '%Ü˙µ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%àF∫\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 20:22:53.000000',NULL,'familiar','Informatica','HC2',_binary '%á\ o\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%âa\›\Ï\ÒÄfbõÉ','S1','2026-11-08 19:22:53.000000','2026-11-08 20:07:53.000000','paciente','Cardiologia','HC3',_binary '%à\ÙÜ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%äkÇ\›\Ï\ÒÄfbõÉ','S2','2026-11-08 18:22:53.000000',NULL,'funcionario','Manuteno','HC4',_binary '%â˘o\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ãSJ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 17:22:53.000000','2026-11-08 18:07:53.000000','familiar','Farmacia','COAD',_binary '%ä\…\œ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%åL•\›\Ï\ÒÄfbõÉ','S1','2026-11-08 16:22:53.000000',NULL,'paciente','Oncologia','CPQ',_binary '%ã\”\„\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ç2\›\Ï\ÒÄfbõÉ','S2','2026-11-08 15:22:53.000000','2026-11-08 16:07:53.000000','funcionario','Informatica','DIPAT',_binary '%åü\Œ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%çˇ0\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 14:22:53.000000',NULL,'familiar','Cardiologia','MP',_binary '%çÄ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%é\„\\›\Ï\ÒÄfbõÉ','S1','2026-11-08 13:22:53.000000','2026-11-08 14:07:53.000000','paciente','Manuteno','HC1',_binary '%érí\›\Ï\ÒÄfbõÉ',1,1),(_binary '%èΩS\›\Ï\ÒÄfbõÉ','S2','2026-11-08 12:22:53.000000',NULL,'funcionario','Farmacia','HC2',_binary '%è^æ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ê¥ø\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 11:22:53.000000','2026-11-08 12:07:53.000000','familiar','Oncologia','HC3',_binary '%êBL\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ëw\⁄\›\Ï\ÒÄfbõÉ','S1','2026-11-08 10:22:53.000000',NULL,'paciente','Informatica','HC4',_binary '%ë$™\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%íT_\›\Ï\ÒÄfbõÉ','S2','2026-11-08 09:22:53.000000','2026-11-08 10:07:53.000000','funcionario','Cardiologia','COAD',_binary '%ë\€\‰\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ì:3\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 08:22:53.000000',NULL,'familiar','Manuteno','CPQ',_binary '%íΩ¶\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%î@0\›\Ï\ÒÄfbõÉ','S1','2026-11-08 07:22:53.000000','2026-11-08 08:07:53.000000','paciente','Farmacia','DIPAT',_binary '%ì\’\€\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ï,\›\Ï\ÒÄfbõÉ','S2','2026-11-08 06:22:53.000000',NULL,'funcionario','Oncologia','MP',_binary '%îòr\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ñ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 05:22:53.000000','2026-11-08 06:07:53.000000','familiar','Informatica','HC1',_binary '%ï©\«\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ñ\Á2\›\Ï\ÒÄfbõÉ','S1','2026-11-08 04:22:53.000000',NULL,'paciente','Cardiologia','HC2',_binary '%ñf	\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ó®\ \›\Ï\ÒÄfbõÉ','S2','2026-11-08 03:22:53.000000','2026-11-08 04:07:53.000000','funcionario','Manuteno','HC3',_binary '%óW\√\›\Ï\ÒÄfbõÉ',1,1),(_binary '%òî+\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-08 02:22:53.000000',NULL,'familiar','Farmacia','HC4',_binary '%ò∑\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ôSö\›\Ï\ÒÄfbõÉ','S1','2026-11-08 01:22:53.000000','2026-11-08 02:07:53.000000','paciente','Oncologia','COAD',_binary '%ò\Áµ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%öD\›\Ï\ÒÄfbõÉ','S2','2026-11-08 00:22:53.000000',NULL,'funcionario','Informatica','CPQ',_binary '%ô\¬\ı\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ö\Ï´\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 23:22:53.000000','2026-11-08 00:07:53.000000','familiar','Cardiologia','DIPAT',_binary '%öÇâ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%õ¥Ø\›\Ï\ÒÄfbõÉ','S1','2026-11-07 22:22:53.000000',NULL,'paciente','Manuteno','MP',_binary '%õCã\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%út\Œ\›\Ï\ÒÄfbõÉ','S2','2026-11-07 21:22:53.000000','2026-11-07 22:07:53.000000','funcionario','Farmacia','HC1',_binary '%úü\›\Ï\ÒÄfbõÉ',1,1),(_binary '%ù5h\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 20:22:53.000000',NULL,'familiar','Oncologia','HC2',_binary '%ú\›N\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ûZê\›\Ï\ÒÄfbõÉ','S1','2026-11-07 19:22:53.000000','2026-11-07 20:07:53.000000','paciente','Informatica','HC3',_binary '%ù∏Ü\›\Ï\ÒÄfbõÉ',1,1),(_binary '%üR\ \›\Ï\ÒÄfbõÉ','S2','2026-11-07 18:22:53.000000',NULL,'funcionario','Cardiologia','HC4',_binary '%û€Ç\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%†)9\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 17:22:53.000000','2026-11-07 18:07:53.000000','familiar','Manuteno','COAD',_binary '%ü«ç\›\Ï\ÒÄfbõÉ',1,1),(_binary '%°Cπ\›\Ï\ÒÄfbõÉ','S1','2026-11-07 16:22:53.000000',NULL,'paciente','Farmacia','CPQ',_binary '%†¡ä\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%¢,ö\›\Ï\ÒÄfbõÉ','S2','2026-11-07 15:22:53.000000','2026-11-07 16:07:53.000000','funcionario','Oncologia','DIPAT',_binary '%°æΩ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%£6\\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 14:22:53.000000',NULL,'familiar','Informatica','MP',_binary '%¢™!\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%§$ä\›\Ï\ÒÄfbõÉ','S1','2026-11-07 13:22:53.000000','2026-11-07 14:07:53.000000','paciente','Cardiologia','HC1',_binary '%£Ø\n\›\Ï\ÒÄfbõÉ',1,1),(_binary '%•3\Ï\›\Ï\ÒÄfbõÉ','S2','2026-11-07 12:22:53.000000',NULL,'funcionario','Manuteno','HC2',_binary '%§µz\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%¶\ZZ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 11:22:53.000000','2026-11-07 12:07:53.000000','familiar','Farmacia','HC3',_binary '%•®\r\›\Ï\ÒÄfbõÉ',1,1),(_binary '%¶\\Ã\›\Ï\ÒÄfbõÉ','S1','2026-11-07 10:22:53.000000',NULL,'paciente','Oncologia','HC4',_binary '%¶ï\…\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ß\\›\Ï\ÒÄfbõÉ','S2','2026-11-07 09:22:53.000000','2026-11-07 10:07:53.000000','funcionario','Informatica','COAD',_binary '%ßg∏\›\Ï\ÒÄfbõÉ',1,1),(_binary '%®\·w\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 08:22:53.000000',NULL,'familiar','Cardiologia','CPQ',_binary '%®o\Ÿ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%©÷∂\›\Ï\ÒÄfbõÉ','S1','2026-11-07 07:22:53.000000','2026-11-07 08:07:53.000000','paciente','Manuteno','DIPAT',_binary '%©W\›\Ï\ÒÄfbõÉ',1,1),(_binary '%™\˜%\›\Ï\ÒÄfbõÉ','S2','2026-11-07 06:22:53.000000',NULL,'funcionario','Farmacia','MP',_binary '%™Ä\√\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%´\Àt\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 05:22:53.000000','2026-11-07 06:07:53.000000','familiar','Oncologia','HC1',_binary '%´v>\›\Ï\ÒÄfbõÉ',1,1),(_binary '%¨\ÀD\›\Ï\ÒÄfbõÉ','S1','2026-11-07 04:22:53.000000',NULL,'paciente','Informatica','HC2',_binary '%¨>x\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ÆT.\›\Ï\ÒÄfbõÉ','S2','2026-11-07 03:22:53.000000','2026-11-07 04:07:53.000000','funcionario','Cardiologia','HC3',_binary '%≠z*\›\Ï\ÒÄfbõÉ',1,1),(_binary '%∞á(\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-07 02:22:53.000000',NULL,'familiar','Manuteno','HC4',_binary '%Ø*T\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%≤n\›\Ï\ÒÄfbõÉ','S1','2026-11-07 01:22:53.000000','2026-11-07 02:07:53.000000','paciente','Farmacia','COAD',_binary '%±Ax\›\Ï\ÒÄfbõÉ',1,1),(_binary '%≥≥\›\Ï\ÒÄfbõÉ','S2','2026-11-07 00:22:53.000000',NULL,'funcionario','Oncologia','CPQ',_binary '%≤\◊u\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%µ0\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 23:22:53.000000','2026-11-07 00:07:53.000000','familiar','Informatica','DIPAT',_binary '%¥a\›\Ï\ÒÄfbõÉ',1,1),(_binary '%∂q,\›\Ï\ÒÄfbõÉ','S1','2026-11-06 22:22:53.000000',NULL,'paciente','Cardiologia','MP',_binary '%µ\ˆ!\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%∑æ9\›\Ï\ÒÄfbõÉ','S2','2026-11-06 21:22:54.000000','2026-11-06 22:07:54.000000','funcionario','Manuteno','HC1',_binary '%∂\ﬁ9\›\Ï\ÒÄfbõÉ',1,1),(_binary '%π8¢\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 20:22:54.000000',NULL,'familiar','Farmacia','HC2',_binary '%∏Ç®\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%∫÷æ\›\Ï\ÒÄfbõÉ','S1','2026-11-06 19:22:54.000000','2026-11-06 20:07:54.000000','paciente','Oncologia','HC3',_binary '%π˛ª\›\Ï\ÒÄfbõÉ',1,1),(_binary '%º=õ\›\Ï\ÒÄfbõÉ','S2','2026-11-06 18:22:54.000000',NULL,'funcionario','Informatica','HC4',_binary '%ª_=\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%Ω\Âc\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 17:22:54.000000','2026-11-06 18:07:54.000000','familiar','Cardiologia','COAD',_binary '%Ω\Ó\›\Ï\ÒÄfbõÉ',1,1),(_binary '%øc@\›\Ï\ÒÄfbõÉ','S1','2026-11-06 16:22:54.000000',NULL,'paciente','Manuteno','CPQ',_binary '%æç\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%¿ïw\›\Ï\ÒÄfbõÉ','S2','2026-11-06 15:22:54.000000','2026-11-06 16:07:54.000000','funcionario','Farmacia','DIPAT',_binary '%ø˘Æ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%¡\ÍK\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 14:22:54.000000','2026-11-11 16:23:02.736464','familiar','Oncologia','MP',_binary '%¡XÅ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\¬¡∏\›\Ï\ÒÄfbõÉ','S1','2026-11-06 13:22:54.000000','2026-11-06 14:07:54.000000','paciente','Informatica','HC1',_binary '%\¬W\·\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\√\…7\›\Ï\ÒÄfbõÉ','S2','2026-11-06 12:22:54.000000',NULL,'funcionario','Cardiologia','HC2',_binary '%\√1G\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ƒ¥k\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 11:22:54.000000','2026-11-06 12:07:54.000000','familiar','Manuteno','HC3',_binary '%\ƒAX\›\Ï\ÒÄfbõÉ',1,1),(_binary '%≈¢R\›\Ï\ÒÄfbõÉ','S1','2026-11-06 10:22:54.000000',NULL,'paciente','Farmacia','HC4',_binary '%\≈6Ç\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%∆î@\›\Ï\ÒÄfbõÉ','S2','2026-11-06 09:22:54.000000','2026-11-06 10:07:54.000000','funcionario','Oncologia','COAD',_binary '%\∆x\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\«j\ƒ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 08:22:54.000000',NULL,'familiar','Informatica','CPQ',_binary '%\«\Ó\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\»:z\›\Ï\ÒÄfbõÉ','S1','2026-11-06 07:22:54.000000','2026-11-06 08:07:54.000000','paciente','Cardiologia','DIPAT',_binary '%\«Õ±\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\…F\›\Ï\ÒÄfbõÉ','S2','2026-11-06 06:22:54.000000',NULL,'funcionario','Manuteno','MP',_binary '%»≥\Õ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\…\Ï\r\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 05:22:54.000000','2026-11-06 06:07:54.000000','familiar','Farmacia','HC1',_binary '%…Å\⁄\›\Ï\ÒÄfbõÉ',1,1),(_binary '% ¨\ı\›\Ï\ÒÄfbõÉ','S1','2026-11-06 04:22:54.000000',NULL,'paciente','Oncologia','HC2',_binary '%\ =≤\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Àwâ\›\Ï\ÒÄfbõÉ','S2','2026-11-06 03:22:54.000000','2026-11-06 04:07:54.000000','funcionario','Informatica','HC3',_binary '%\À\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\ÃT\Ô\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-06 02:22:54.000000',NULL,'familiar','Cardiologia','HC4',_binary '%\À\„\›\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Õ0C\›\Ï\ÒÄfbõÉ','S1','2026-11-06 01:22:54.000000','2026-11-06 02:07:54.000000','paciente','Manuteno','COAD',_binary '%Ã©s\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Œ4\›\Ï\ÒÄfbõÉ','S2','2026-11-06 00:22:54.000000',NULL,'funcionario','Farmacia','CPQ',_binary '%Õ®5\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\œû\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 23:22:54.000000','2026-11-06 00:07:54.000000','familiar','Oncologia','DIPAT',_binary '%Œ•z\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\œ\‹\⁄\›\Ï\ÒÄfbõÉ','S1','2026-11-05 22:22:54.000000',NULL,'paciente','Informatica','MP',_binary '%œÇO\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%–∂¶\›\Ï\ÒÄfbõÉ','S2','2026-11-05 21:22:54.000000','2026-11-05 22:07:54.000000','funcionario','Cardiologia','HC1',_binary '%\–NB\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\—|\Ê\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 20:22:54.000000',NULL,'familiar','Manuteno','HC2',_binary '%\—\n\ﬁ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\“9F\›\Ï\ÒÄfbõÉ','S1','2026-11-05 19:22:54.000000','2026-11-05 20:07:54.000000','paciente','Farmacia','HC3',_binary '%\—\„p\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\”\Z^\›\Ï\ÒÄfbõÉ','S2','2026-11-05 18:22:54.000000',NULL,'funcionario','Oncologia','HC4',_binary '%“•\"\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\‘∫\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 17:22:54.000000','2026-11-05 18:07:54.000000','familiar','Informatica','COAD',_binary '%”ùó\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\’\›\Ï\ÒÄfbõÉ','S1','2026-11-05 16:22:54.000000',NULL,'paciente','Cardiologia','CPQ',_binary '%‘üd\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\’\Ï¿\›\Ï\ÒÄfbõÉ','S2','2026-11-05 15:22:54.000000','2026-11-05 16:07:54.000000','funcionario','Manuteno','DIPAT',_binary '%’ö±\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\÷\ \“\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 14:22:54.000000',NULL,'familiar','Farmacia','MP',_binary '%\÷[£\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%◊°\–\›\Ï\ÒÄfbõÉ','S1','2026-11-05 13:22:54.000000','2026-11-05 14:07:54.000000','paciente','Oncologia','HC1',_binary '%\◊ T\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\ÿ]l\›\Ï\ÒÄfbõÉ','S2','2026-11-05 12:22:54.000000',NULL,'funcionario','Informatica','HC2',_binary '%\ÿr\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Ÿsë\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 11:22:54.000000','2026-11-05 12:07:54.000000','familiar','Cardiologia','HC3',_binary '%\ÿ˘f\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\⁄4_\›\Ï\ÒÄfbõÉ','S1','2026-11-05 10:22:54.000000',NULL,'paciente','Manuteno','HC4',_binary '%\Ÿ\€T\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\€\Ê\›\Ï\ÒÄfbõÉ','S2','2026-11-05 09:22:54.000000','2026-11-05 10:07:54.000000','funcionario','Farmacia','COAD',_binary '%⁄©Y\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\€\ﬂb\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 08:22:54.000000',NULL,'familiar','Oncologia','CPQ',_binary '%\€dE\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\›m;\›\Ï\ÒÄfbõÉ','S1','2026-11-05 07:22:54.000000','2026-11-05 08:07:54.000000','paciente','Informatica','DIPAT',_binary '%\‹¯\Ï\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\ﬁ\'v\›\Ï\ÒÄfbõÉ','S2','2026-11-05 06:22:54.000000',NULL,'funcionario','Cardiologia','MP',_binary '%›ª\‰\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\ﬂ!%\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 05:22:54.000000','2026-11-05 06:07:54.000000','familiar','Manuteno','HC1',_binary '%ﬁ®\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\‡®\›\Ï\ÒÄfbõÉ','S1','2026-11-05 04:22:54.000000',NULL,'paciente','Farmacia','HC2',_binary '%ﬂè<\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\‡\ˆ¡\›\Ï\ÒÄfbõÉ','S2','2026-11-05 03:22:54.000000','2026-11-05 04:07:54.000000','funcionario','Oncologia','HC3',_binary '%\‡å4\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\·¯\À\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-05 02:22:54.000000',NULL,'familiar','Informatica','HC4',_binary '%\·~\"\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\‚\œ}\›\Ï\ÒÄfbõÉ','S1','2026-11-05 01:22:54.000000','2026-11-05 02:07:54.000000','paciente','Cardiologia','COAD',_binary '%\‚o\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\„Ãè\›\Ï\ÒÄfbõÉ','S2','2026-11-05 00:22:54.000000',NULL,'funcionario','Manuteno','CPQ',_binary '%\„L\Ó\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\‰\√Q\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 23:22:54.000000','2026-11-05 00:07:54.000000','familiar','Farmacia','DIPAT',_binary '%\‰@¯\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ê5§\›\Ï\ÒÄfbõÉ','S1','2026-11-04 22:22:54.000000',NULL,'paciente','Oncologia','MP',_binary '%\ÂF¨\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Á8J\›\Ï\ÒÄfbõÉ','S2','2026-11-04 21:22:54.000000','2026-11-04 22:07:54.000000','funcionario','Informatica','HC1',_binary '%\Ê¿T\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ë\Û\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 20:22:54.000000',NULL,'familiar','Cardiologia','HC2',_binary '%\Áï7\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Èò\›\Ï\ÒÄfbõÉ','S1','2026-11-04 19:22:54.000000','2026-11-04 20:07:54.000000','paciente','Manuteno','HC3',_binary '%Ë§ô\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Í\Ô\›\Ï\ÒÄfbõÉ','S2','2026-11-04 18:22:54.000000',NULL,'funcionario','Farmacia','HC4',_binary '%\È{O\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Î9ã\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 17:22:54.000000','2026-11-04 18:07:54.000000','familiar','Oncologia','COAD',_binary '%\Íã\\\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ï$¸\›\Ï\ÒÄfbõÉ','S1','2026-11-04 16:22:54.000000',NULL,'paciente','Informatica','CPQ',_binary '%\Î´\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\ÌT\›\Ï\ÒÄfbõÉ','S2','2026-11-04 15:22:54.000000','2026-11-04 16:07:54.000000','funcionario','Cardiologia','DIPAT',_binary '%\ÏÄF\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ì˛U\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 14:22:54.000000',NULL,'familiar','Manuteno','MP',_binary '%\ÌåP\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Ó\Œˇ\›\Ï\ÒÄfbõÉ','S1','2026-11-04 13:22:54.000000','2026-11-04 14:07:54.000000','paciente','Farmacia','HC1',_binary '%\ÓS`\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ôó5\›\Ï\ÒÄfbõÉ','S2','2026-11-04 12:22:54.000000',NULL,'funcionario','Oncologia','HC2',_binary '%\Ô>\Á\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\º\Z\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 11:22:54.000000','2026-11-04 12:07:54.000000','familiar','Informatica','HC3',_binary '%\\Ò\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ò™(\›\Ï\ÒÄfbõÉ','S1','2026-11-04 10:22:54.000000',NULL,'paciente','Cardiologia','HC4',_binary '%\Ò52\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Úêa\›\Ï\ÒÄfbõÉ','S2','2026-11-04 09:22:54.000000','2026-11-04 10:07:54.000000','funcionario','Manuteno','COAD',_binary '%\Ú\ﬁ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\Ûåπ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 08:22:54.000000',NULL,'familiar','Farmacia','CPQ',_binary '%\Û\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\Ùbá\›\Ï\ÒÄfbõÉ','S1','2026-11-04 07:22:54.000000','2026-11-04 08:07:54.000000','paciente','Oncologia','DIPAT',_binary '%\Û\ﬂ\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\ıOe\›\Ï\ÒÄfbõÉ','S2','2026-11-04 06:22:54.000000',NULL,'funcionario','Informatica','MP',_binary '%\Ù\’\Œ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%\ˆ%\È\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 05:22:54.000000','2026-11-04 06:07:54.000000','familiar','Cardiologia','HC1',_binary '%\ı¨\'\›\Ï\ÒÄfbõÉ',1,1),(_binary '%\˜?}\›\Ï\ÒÄfbõÉ','S1','2026-11-04 04:22:54.000000',NULL,'paciente','Manuteno','HC2',_binary '%\ˆ«µ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%¯!^\›\Ï\ÒÄfbõÉ','S2','2026-11-04 03:22:54.000000','2026-11-04 04:07:54.000000','funcionario','Farmacia','HC3',_binary '%\˜±\€\›\Ï\ÒÄfbõÉ',1,1),(_binary '%˘≤\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-04 02:22:54.000000',NULL,'familiar','Oncologia','HC4',_binary '%¯£\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%˘\ık\›\Ï\ÒÄfbõÉ','S1','2026-11-04 01:22:54.000000','2026-11-04 02:07:54.000000','paciente','Informatica','COAD',_binary '%˘â@\›\Ï\ÒÄfbõÉ',1,1),(_binary '%˚\„\›\Ï\ÒÄfbõÉ','S2','2026-11-04 00:22:54.000000',NULL,'funcionario','Cardiologia','CPQ',_binary '%˙x\›\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%¸9\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 23:22:54.000000','2026-11-04 00:07:54.000000','familiar','Manuteno','DIPAT',_binary '%˚å\‘\›\Ï\ÒÄfbõÉ',1,1),(_binary '%˝∫\›\Ï\ÒÄfbõÉ','S1','2026-11-03 22:22:54.000000',NULL,'paciente','Farmacia','MP',_binary '%¸ëÇ\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%˝\Y\›\Ï\ÒÄfbõÉ','S2','2026-11-03 21:22:54.000000','2026-11-03 22:07:54.000000','funcionario','Oncologia','HC1',_binary '%˝~\›\Ï\ÒÄfbõÉ',1,1),(_binary '%˛\’\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 20:22:54.000000',NULL,'familiar','Informatica','HC2',_binary '%˛dã\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '%ˇπ\’\›\Ï\ÒÄfbõÉ','S1','2026-11-03 19:22:54.000000','2026-11-03 20:07:54.000000','paciente','Cardiologia','HC3',_binary '%ˇJ\›\Ï\ÒÄfbõÉ',1,1),(_binary '&\0ê=\›\Ï\ÒÄfbõÉ','S2','2026-11-03 18:22:54.000000',NULL,'funcionario','Manuteno','HC4',_binary '&\0£\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&õ5\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 17:22:54.000000','2026-11-03 18:07:54.000000','familiar','Farmacia','COAD',_binary '&V\›\Ï\ÒÄfbõÉ',1,1),(_binary '&∂=\›\Ï\ÒÄfbõÉ','S1','2026-11-03 16:22:54.000000',NULL,'paciente','Oncologia','CPQ',_binary '&Ct\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&í\À\›\Ï\ÒÄfbõÉ','S2','2026-11-03 15:22:54.000000','2026-11-03 16:07:54.000000','funcionario','Informatica','DIPAT',_binary '&	ª\›\Ï\ÒÄfbõÉ',1,1),(_binary '&òπ\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 14:22:54.000000',NULL,'familiar','Cardiologia','MP',_binary '&\À\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&t\Z\›\Ï\ÒÄfbõÉ','S1','2026-11-03 13:22:54.000000','2026-11-03 14:07:54.000000','paciente','Manuteno','HC1',_binary '&\ı\‘\›\Ï\ÒÄfbõÉ',1,1),(_binary '&c\›\Ï\ÒÄfbõÉ','S2','2026-11-03 12:22:54.000000',NULL,'funcionario','Farmacia','HC2',_binary '&\Â*\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&J\Ò\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 11:22:54.000000','2026-11-03 12:07:54.000000','familiar','Oncologia','HC3',_binary '&“ú\›\Ï\ÒÄfbõÉ',1,1),(_binary '&z\›\Ï\ÒÄfbõÉ','S1','2026-11-03 10:22:54.000000',NULL,'paciente','Informatica','HC4',_binary '&º\Ï\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&	qH\›\Ï\ÒÄfbõÉ','S2','2026-11-03 09:22:54.000000','2026-11-03 10:07:54.000000','funcionario','Cardiologia','COAD',_binary '&¯¸\›\Ï\ÒÄfbõÉ',1,1),(_binary '&\n9\"\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 08:22:54.000000',NULL,'familiar','Manuteno','CPQ',_binary '&	»Ç\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&{\›\Ï\ÒÄfbõÉ','S1','2026-11-03 07:22:54.000000','2026-11-03 08:07:54.000000','paciente','Farmacia','DIPAT',_binary '&\n¨!\›\Ï\ÒÄfbõÉ',1,1),(_binary '&≠\›\Ï\ÒÄfbõÉ','S2','2026-11-03 06:22:54.000000',NULL,'funcionario','Oncologia','MP',_binary '&õò\›\Ï\ÒÄfbõÉ',1,NULL),(_binary '&\r	\·\›\Ï\ÒÄfbõÉ','PILOTIS','2026-11-03 05:22:54.000000','2026-11-03 06:07:54.000000','familiar','Informatica','HC1',_binary '&ë\Ï\›\Ï\ÒÄfbõÉ',1,1);
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

--
-- Dumping routines for database 'entrada_saida_pessoas'
--
/*!50003 DROP PROCEDURE IF EXISTS `popular_200_pessoas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `popular_200_pessoas`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE pessoa_uuid BINARY(16);
    DECLARE unidade_val VARCHAR(10);
    DECLARE setor_val VARCHAR(50);
    DECLARE andar_val VARCHAR(20);
    DECLARE paciente_val VARCHAR(20);
    DECLARE nome_sorteado VARCHAR(100);
    
    
    DECLARE n1, n2, n3, n4, n5, n6, n7, n8, n9 INT;
    DECLARE d1, d2 INT;
    DECLARE soma INT;
    DECLARE cpf_gerado VARCHAR(11);
    
    WHILE i <= 200 DO
        SET pessoa_uuid = UUID_TO_BIN(UUID());
        
        
        SET n1 = FLOOR(RAND() * 9);
        SET n2 = FLOOR(RAND() * 9);
        SET n3 = FLOOR(RAND() * 9);
        SET n4 = FLOOR(RAND() * 9);
        SET n5 = FLOOR(RAND() * 9);
        SET n6 = FLOOR(RAND() * 9);
        SET n7 = FLOOR(RAND() * 9);
        SET n8 = FLOOR(RAND() * 9);
        SET n9 = (i % 9);

        
        SET soma = n1*10 + n2*9 + n3*8 + n4*7 + n5*6 + n6*5 + n7*4 + n8*3 + n9*2;
        SET d1 = 11 - (soma % 11);
        IF d1 >= 10 THEN SET d1 = 0; END IF;

        
        SET soma = n1*11 + n2*10 + n3*9 + n4*8 + n5*7 + n6*6 + n7*5 + n8*4 + n9*3 + d1*2;
        SET d2 = 11 - (soma % 11);
        IF d2 >= 10 THEN SET d2 = 0; END IF;

        
        SET cpf_gerado = CONCAT(n1, n2, n3, n4, n5, n6, n7, n8, n9, d1, d2);

        
        SET nome_sorteado = CONCAT(
            ELT(1 + FLOOR(RAND() * 15), 'Ana Beatriz', 'Carlos Alberto', 'Mariana', 'Roberto', 'Lucas Gabriel', 'Fernanda', 'Joo Paulo', 'Juliana', 'Marcos Vinicius', 'Patrcia', 'Ricardo', 'Camila', 'Eduardo', 'Beatriz', 'Gabriel'),
            ' ',
            ELT(1 + FLOOR(RAND() * 15), 'da Silva', 'Oliveira Santos', 'de Souza', 'Lima', 'Mendes', 'Costa e Silva', 'Ribeiro', 'Martins', 'Rocha', 'Ramos', 'Alencar', 'Viana', 'Costa', 'Souza', 'Dias'),
            ' ',
            i
        );
        
        SET unidade_val = ELT(1 + (i % 8), 'HC1', 'HC2', 'HC3', 'HC4', 'COAD', 'CPQ', 'DIPAT', 'MP');
        SET setor_val = ELT(1 + (i % 5), 'Informatica', 'Cardiologia', 'Manuteno', 'Farmacia', 'Oncologia');
        SET andar_val = ELT(1 + (i % 3), 'S1', 'S2', 'PILOTIS');
        SET paciente_val = ELT(1 + (i % 3), 'paciente', 'funcionario', 'familiar');

        
        INSERT INTO `pessoas` (`id`, `andar`, `cpf`, `identidade`, `nome`, `observacao`, `paciente`, `setor`, `telefone`, `unidade`, `usuario_id`) 
        VALUES (
            pessoa_uuid, 
            andar_val, 
            cpf_gerado, 
            CONCAT('ID', i), 
            nome_sorteado, 
            'Gerado em massa - 200', 
            paciente_val, 
            setor_val, 
            '21999998888', 
            unidade_val, 
            1
        );

        
        INSERT INTO `registros` (`id`, `andar`, `hora_entrada`, `hora_saida`, `paciente`, `setor`, `unidade`, `pessoa_id`, `usuario_entrada_id`, `usuario_saida_id`)
        VALUES (
            UUID_TO_BIN(UUID()), 
            andar_val, 
            DATE_SUB(NOW(), INTERVAL i HOUR), 
            IF(i % 2 = 0, DATE_SUB(DATE_SUB(NOW(), INTERVAL i HOUR), INTERVAL -45 MINUTE), NULL), 
            paciente_val, 
            setor_val, 
            unidade_val, 
            pessoa_uuid, 
            1, 
            IF(i % 2 = 0, 1, NULL)
        );

        SET i = i + 1;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `popular_com_cpfs_validos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `popular_com_cpfs_validos`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE pessoa_uuid BINARY(16);
    DECLARE unidade_val VARCHAR(10);
    DECLARE setor_val VARCHAR(50);
    DECLARE andar_val VARCHAR(20);
    DECLARE paciente_val VARCHAR(20);
    DECLARE nome_sorteado VARCHAR(100);
    
    
    DECLARE n1, n2, n3, n4, n5, n6, n7, n8, n9 INT;
    DECLARE d1, d2 INT;
    DECLARE soma INT;
    DECLARE cpf_gerado VARCHAR(11);
    
    WHILE i <= 120 DO
        SET pessoa_uuid = UUID_TO_BIN(UUID());
        
        
        SET n1 = FLOOR(RAND() * 9);
        SET n2 = FLOOR(RAND() * 9);
        SET n3 = FLOOR(RAND() * 9);
        SET n4 = FLOOR(RAND() * 9);
        SET n5 = FLOOR(RAND() * 9);
        SET n6 = FLOOR(RAND() * 9);
        SET n7 = FLOOR(RAND() * 9);
        SET n8 = FLOOR(RAND() * 9);
        SET n9 = (i % 9); 

        
        SET soma = n1*10 + n2*9 + n3*8 + n4*7 + n5*6 + n6*5 + n7*4 + n8*3 + n9*2;
        SET d1 = 11 - (soma % 11);
        IF d1 >= 10 THEN SET d1 = 0; END IF;

        
        SET soma = n1*11 + n2*10 + n3*9 + n4*8 + n5*7 + n6*6 + n7*5 + n8*4 + n9*3 + d1*2;
        SET d2 = 11 - (soma % 11);
        IF d2 >= 10 THEN SET d2 = 0; END IF;

        
        SET cpf_gerado = CONCAT(n1, n2, n3, n4, n5, n6, n7, n8, n9, d1, d2);

        
        SET nome_sorteado = CONCAT(
            ELT(1 + FLOOR(RAND() * 15), 'Ana Beatriz', 'Carlos Alberto', 'Mariana', 'Roberto', 'Lucas Gabriel', 'Fernanda', 'Joo Paulo', 'Juliana', 'Marcos Vinicius', 'Patrcia', 'Ricardo', 'Camila', 'Eduardo', 'Beatriz', 'Gabriel'),
            ' ',
            ELT(1 + FLOOR(RAND() * 15), 'da Silva', 'Oliveira Santos', 'de Souza', 'Lima', 'Mendes', 'Costa e Silva', 'Ribeiro', 'Martins', 'Rocha', 'Ramos', 'Alencar', 'Viana', 'Costa', 'Souza', 'Dias'),
            ' ',
            i
        );
        
        SET unidade_val = ELT(1 + (i % 8), 'HC1', 'HC2', 'HC3', 'HC4', 'COAD', 'CPQ', 'DIPAT', 'MP');
        SET setor_val = ELT(1 + (i % 5), 'Informatica', 'Cardiologia', 'Manuteno', 'Farmacia', 'Oncologia');
        SET andar_val = ELT(1 + (i % 3), 'S1', 'S2', 'PILOTIS');
        SET paciente_val = ELT(1 + (i % 3), 'paciente', 'funcionario', 'familiar');

        
        INSERT INTO `pessoas` (`id`, `andar`, `cpf`, `identidade`, `nome`, `observacao`, `paciente`, `setor`, `telefone`, `unidade`, `usuario_id`) 
        VALUES (
            pessoa_uuid, 
            andar_val, 
            cpf_gerado, 
            CONCAT('ID', i), 
            nome_sorteado, 
            'Gerado em massa', 
            paciente_val, 
            setor_val, 
            '21999998888', 
            unidade_val, 
            1
        );

        
        INSERT INTO `registros` (`id`, `andar`, `hora_entrada`, `hora_saida`, `paciente`, `setor`, `unidade`, `pessoa_id`, `usuario_entrada_id`, `usuario_saida_id`)
        VALUES (
            UUID_TO_BIN(UUID()), 
            andar_val, 
            DATE_SUB(NOW(), INTERVAL i DAY), 
            IF(i % 2 = 0, DATE_SUB(DATE_SUB(NOW(), INTERVAL i DAY), INTERVAL -2 HOUR), NULL), 
            paciente_val, 
            setor_val, 
            unidade_val, 
            pessoa_uuid, 
            1, 
            IF(i % 2 = 0, 1, NULL)
        );

        SET i = i + 1;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `popular_dados_massa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `popular_dados_massa`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE pessoa_uuid BINARY(16);
    DECLARE unidade_val VARCHAR(10);
    DECLARE setor_val VARCHAR(50);
    DECLARE andar_val VARCHAR(20);
    DECLARE paciente_val VARCHAR(20);
    
    
    
    

    WHILE i <= 120 DO
        
        SET pessoa_uuid = UUID_TO_BIN(UUID());
        
        
        SET unidade_val = ELT(1 + (i % 8), 'HC1', 'HC2', 'HC3', 'HC4', 'COAD', 'CPQ', 'DIPAT', 'MP');
        SET setor_val = ELT(1 + (i % 5), 'Informatica', 'Cardiologia', 'Manuteno', 'Farmacia', 'Oncologia');
        SET andar_val = ELT(1 + (i % 3), 'S1', 'S2', 'PILOTIS');
        SET paciente_val = ELT(1 + (i % 3), 'paciente', 'funcionario', 'familiar');

        
        INSERT INTO `pessoas` (`id`, `andar`, `cpf`, `identidade`, `nome`, `observacao`, `paciente`, `setor`, `telefone`, `unidade`, `usuario_id`) 
        VALUES (
            pessoa_uuid, 
            andar_val, 
            LPAD(i * 11111, 11, '0'), 
            CONCAT('ID', i), 
            CONCAT('Pessoa Teste ', i), 
            'Gerado em massa', 
            paciente_val, 
            setor_val, 
            '21999998888', 
            unidade_val, 
            1
        );

        
        INSERT INTO `registros` (`id`, `andar`, `hora_entrada`, `hora_saida`, `paciente`, `setor`, `unidade`, `pessoa_id`, `usuario_entrada_id`, `usuario_saida_id`)
        VALUES (
            UUID_TO_BIN(UUID()), 
            andar_val, 
            DATE_SUB(NOW(), INTERVAL i DAY), 
            IF(i % 2 = 0, DATE_SUB(DATE_SUB(NOW(), INTERVAL i DAY), INTERVAL -2 HOUR), NULL), 
            paciente_val, 
            setor_val, 
            unidade_val, 
            pessoa_uuid, 
            1, 
            IF(i % 2 = 0, 1, NULL)
        );

        SET i = i + 1;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `popular_nomes_reAIS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `popular_nomes_reAIS`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE pessoa_uuid BINARY(16);
    DECLARE unidade_val VARCHAR(10);
    DECLARE setor_val VARCHAR(50);
    DECLARE andar_val VARCHAR(20);
    DECLARE paciente_val VARCHAR(20);
    DECLARE nome_sorteado VARCHAR(100);
    
    WHILE i <= 120 DO
        SET pessoa_uuid = UUID_TO_BIN(UUID());
        
        
        SET nome_sorteado = CONCAT(
            ELT(1 + FLOOR(RAND() * 15), 'Ana Beatriz', 'Carlos Alberto', 'Mariana', 'Roberto', 'Lucas Gabriel', 'Fernanda', 'Joo Paulo', 'Juliana', 'Marcos Vinicius', 'Patrcia', 'Ricardo', 'Camila', 'Eduardo', 'Beatriz', 'Gabriel'),
            ' ',
            ELT(1 + FLOOR(RAND() * 15), 'da Silva', 'Oliveira Santos', 'de Souza', 'Lima', 'Mendes', 'Costa e Silva', 'Ribeiro', 'Martins', 'Rocha', 'Ramos', 'Alencar', 'Viana', 'Costa', 'Souza', 'Dias'),
            ' ',
            i 
        );
        
        SET unidade_val = ELT(1 + (i % 8), 'HC1', 'HC2', 'HC3', 'HC4', 'COAD', 'CPQ', 'DIPAT', 'MP');
        SET setor_val = ELT(1 + (i % 5), 'Informatica', 'Cardiologia', 'Manuteno', 'Farmacia', 'Oncologia');
        SET andar_val = ELT(1 + (i % 3), 'S1', 'S2', 'PILOTIS');
        SET paciente_val = ELT(1 + (i % 3), 'paciente', 'funcionario', 'familiar');

        
        INSERT INTO `pessoas` (`id`, `andar`, `cpf`, `identidade`, `nome`, `observacao`, `paciente`, `setor`, `telefone`, `unidade`, `usuario_id`) 
        VALUES (
            pessoa_uuid, 
            andar_val, 
            LPAD(i * 111111, 11, '0'), 
            CONCAT('ID', i), 
            nome_sorteado, 
            'Gerado em massa', 
            paciente_val, 
            setor_val, 
            '21999998888', 
            unidade_val, 
            1
        );

        
        INSERT INTO `registros` (`id`, `andar`, `hora_entrada`, `hora_saida`, `paciente`, `setor`, `unidade`, `pessoa_id`, `usuario_entrada_id`, `usuario_saida_id`)
        VALUES (
            UUID_TO_BIN(UUID()), 
            andar_val, 
            DATE_SUB(NOW(), INTERVAL i DAY), 
            IF(i % 2 = 0, DATE_SUB(DATE_SUB(NOW(), INTERVAL i DAY), INTERVAL -2 HOUR), NULL), 
            paciente_val, 
            setor_val, 
            unidade_val, 
            pessoa_uuid, 
            1, 
            IF(i % 2 = 0, 1, NULL)
        );

        SET i = i + 1;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-11 13:23:52
