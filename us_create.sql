CREATE TABLE `permissions` (
  `id_permission` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id_role` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id_role`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_role_UNIQUE` (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles_has_permissions` (
  `roles_id_role` varchar(255) NOT NULL,
  `permissions_id_permission` varchar(255) NOT NULL,
  PRIMARY KEY (`roles_id_role`,`permissions_id_permission`),
  KEY `fk_roles_has_permissions_permissions1_idx` (`permissions_id_permission`),
  KEY `fk_roles_has_permissions_roles1_idx` (`roles_id_role`),
  CONSTRAINT `fk_roles_has_permissions_permissions1` FOREIGN KEY (`permissions_id_permission`) REFERENCES `permissions` (`id_permission`),
  CONSTRAINT `fk_roles_has_permissions_roles1` FOREIGN KEY (`roles_id_role`) REFERENCES `roles` (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id_user` varchar(255) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `id_role` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `id_user_UNIQUE` (`id_user`),
  KEY `id_role_idx` (`id_role`),
  CONSTRAINT `id_role` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
