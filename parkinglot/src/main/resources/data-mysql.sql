LOCK TABLES `role` WRITE;
INSERT IGNORE INTO `role` VALUES (1,'ROLE_ADMIN');
INSERT IGNORE INTO `role` VALUES (2,'ROLE_USER');
UNLOCK TABLES;


INSERT IGNORE INTO `user` (`id`, `email`, `name`, `password`, `current_parkinglot_id`) VALUES
(1, 'anto@gmail.com', 'anto', '$2a$10$pzNrW/UuD1qd.3y4SvtvkO1GH8pMmpshTvfnszQCHPG3TjBVg74xu', NULL),
(2, 'bob@gmail.com', 'bob', '$2a$10$qY/t/hv5SIzmyVWgnfwULeRWwOLygNoqmd9U8.LSClHTHtaMfq2.2', NULL),
(3, 'marco@gmail.com', 'marco', '$2a$10$bl0HFAmjvFHGGGHXs1XVxeik0fVroiwzaPAoDif8gKZYkJFc46sku', NULL),
(4, 'sandro@gmail.com', 'sandro', '$2a$10$8sYvlMfo8IgcKQr4J1Yf.uDpv73JpVXftfl4LuIB7KByMZjG/HSfy', NULL),
(5, 'lucia@gmail.com', 'lucia', '$2a$10$Xrz3t/ECVXgMvbR5.gSFtOfqs/LNDjqg2ThIhreL/Ah5mbD7H388W', NULL),
(6, 'paolo@gmail.com', 'paolo', '$2a$10$sOpTLyrT6pZSU2bE0bFGqu4Tc.294Yo2hIsNeCEFukNqLjjNq5R7u', NULL),
(7, 'roberto@gmail.com', 'Roberto', '$2a$10$umhS1Bf44slSry.Fv8Y0oudb/mELyD.ccG6wzE6/hZxp.RhN3licu', NULL),
(8, 'gennaro@gmail.com', 'gennaro', '$2a$10$R4.c8H9H/ji9psZaBy7BB..Y5iQ3L8HIei/R556CYVp/OcKRqf05K', NULL);


INSERT IGNORE INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2);


INSERT IGNORE INTO `parkinglot` (`id`, `current_state`, `free_seats`, `quarter`, `total_places`, `via`, `added_by_id`) VALUES
(12, 'ACTIVE', 50, 'Stazione Centrale', 100, 'Via Stazione', 1),
(13, 'ACTIVE', 500, 'San Siro', 500, 'Piazzale Lotto', 1),
(14, 'ACTIVE', 1000, 'Famagosta', 1000, 'Via Famagosta', 1);



