CREATE TABLE IF NOT EXISTS `account` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `NAME` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `USER_ID_INDEX` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE IF NOT EXISTS `clock_record` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(255) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE IF NOT EXISTS `clock_statistics` (
  `ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `USER_ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `RECORD_DAY` date NOT NULL,
  `EARLIEST_TIME` datetime DEFAULT NULL,
  `LATEST_TIME` datetime DEFAULT NULL,
  `TIME_DIFF` decimal(5,2) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `REMARK` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci