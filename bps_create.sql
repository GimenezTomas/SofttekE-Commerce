CREATE TABLE `base_products` (
  `id_base_product` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` float NOT NULL,
  `description` varchar(255) NOT NULL,
  `days_fabrication_time` int NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_base_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customization_areas` (
  `id_customization_area` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_customization_area`),
  UNIQUE KEY `id_customization_area_UNIQUE` (`id_customization_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customization_has_base_products` (
  `type_has_area_id_customization_type` varchar(255) NOT NULL,
  `type_has_area_id_customization_area` varchar(255) NOT NULL,
  `base_products_id_base_product` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`type_has_area_id_customization_type`,`type_has_area_id_customization_area`,`base_products_id_base_product`),
  KEY `fk_type_has_area_has_base_products_base_products1_idx` (`base_products_id_base_product`),
  KEY `fk_type_has_area_has_base_products_type_has_area1_idx` (`type_has_area_id_customization_type`,`type_has_area_id_customization_area`),
  CONSTRAINT `fk_type_has_area_has_base_products_base_products1` FOREIGN KEY (`base_products_id_base_product`) REFERENCES `base_products` (`id_base_product`),
  CONSTRAINT `fk_type_has_area_has_base_products_type_has_area1` FOREIGN KEY (`type_has_area_id_customization_type`, `type_has_area_id_customization_area`) REFERENCES `type_has_area` (`id_customization_type`, `id_customization_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customization_types` (
  `id_customization_type` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_customization_type`),
  UNIQUE KEY `id_customization_type_UNIQUE` (`id_customization_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `type_has_area` (
  `id_customization_type` varchar(255) NOT NULL,
  `id_customization_area` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_customization_type`,`id_customization_area`),
  KEY `id_customization_area_idx` (`id_customization_area`),
  CONSTRAINT `id_customization_area` FOREIGN KEY (`id_customization_area`) REFERENCES `customization_areas` (`id_customization_area`),
  CONSTRAINT `id_customization_type` FOREIGN KEY (`id_customization_type`) REFERENCES `customization_types` (`id_customization_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
