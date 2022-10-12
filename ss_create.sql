CREATE TABLE `areas_has_customizations` (
  `id_customization` varchar(255) NOT NULL,
  `id_customization_area` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_customization`),
  CONSTRAINT `id_customization` FOREIGN KEY (`id_customization`) REFERENCES `customizations` (`id_customization`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `customizated_products` (
  `id_customizated_product` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `name` varchar(45) NOT NULL,
  `id_shop` varchar(255) NOT NULL,
  `id_base_product` varchar(255) DEFAULT NULL,
  `id_customization` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_customizated_product`,`id_shop`,`id_customization`),
  UNIQUE KEY `id_customizated_product_UNIQUE` (`id_customizated_product`),
  KEY `fk_customizated_products_shops1_idx` (`id_shop`),
  KEY `fk_customizated_products_customizations1_idx` (`id_customization`),
  CONSTRAINT `fk_customizated_products_customizations1` FOREIGN KEY (`id_customization`) REFERENCES `customizations` (`id_customization`),
  CONSTRAINT `fk_customizated_products_shops1` FOREIGN KEY (`id_shop`) REFERENCES `shops` (`id_shop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `customizations` (
  `id_customization` varchar(255) NOT NULL,
  `name` varchar(245) NOT NULL,
  `price` float NOT NULL,
  `content` varchar(245) NOT NULL,
  `id_customization_type` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_customization`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `payment_methods` (
  `id_payment_method` varchar(255) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `id_shop` varchar(255) NOT NULL,
  PRIMARY KEY (`id_payment_method`,`id_shop`),
  UNIQUE KEY `id_payments_methods_UNIQUE` (`id_payment_method`),
  KEY `fk_payments_methods_shops1_idx` (`id_shop`),
  CONSTRAINT `fk_payments_methods_shops1` FOREIGN KEY (`id_shop`) REFERENCES `shops` (`id_shop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `posts` (
  `id_post` varchar(255) NOT NULL,
  `state` varchar(45) NOT NULL,
  `id_customizated_product` varchar(255) NOT NULL,
  `id_shop` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_post`,`id_customizated_product`,`id_shop`),
  UNIQUE KEY `id_post_UNIQUE` (`id_post`),
  KEY `fk_posts_customizated_products1_idx` (`id_customizated_product`),
  KEY `fk_posts_shops1_idx` (`id_shop`),
  CONSTRAINT `fk_posts_customizated_products1` FOREIGN KEY (`id_customizated_product`) REFERENCES `customizated_products` (`id_customizated_product`),
  CONSTRAINT `fk_posts_shops1` FOREIGN KEY (`id_shop`) REFERENCES `shops` (`id_shop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `shops` (
  `id_shop` varchar(255) NOT NULL,
  `id_user` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(245) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id_shop`),
  UNIQUE KEY `id_shop_UNIQUE` (`id_shop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
