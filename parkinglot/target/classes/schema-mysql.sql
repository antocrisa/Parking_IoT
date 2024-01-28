CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_parkinglot_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `parkinglot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_state` varchar(255) DEFAULT NULL,
  `free_seats` int(11) NOT NULL,
  `quarter` varchar(255) DEFAULT NULL,
  `total_places` int(11) NOT NULL,
  `via` varchar(255) DEFAULT NULL,
  `added_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk47btmributd8c448ex8wyvmx` (`added_by_id`),
  CONSTRAINT `FKk47btmributd8c448ex8wyvmx` FOREIGN KEY (`added_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `parkinglot_users` (
  `parkinglot_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`parkinglot_id`,`user_id`),
  KEY `FK655tus0rxdbjar4w1pofjmvht` (`user_id`),
  CONSTRAINT `FK655tus0rxdbjar4w1pofjmvht` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKf5k3ergvl8fyaobktuoplr48o` FOREIGN KEY (`parkinglot_id`) REFERENCES `parkinglot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),  
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
