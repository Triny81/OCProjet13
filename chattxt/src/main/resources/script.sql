CREATE TABLE `USER` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `role_id` integer NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
);

CREATE TABLE `ROLE` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL
);

CREATE TABLE `CONVERSATION` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `admin_id` integer NOT NULL,
  `client_id` integer NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
);

CREATE TABLE `MESSAGE` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `author_id` integer NOT NULL,
  `conversation_id` integer NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
);

ALTER TABLE `USER` ADD FOREIGN KEY (`role_id`) REFERENCES `ROLE` (`id`);

ALTER TABLE `CONVERSATION` ADD FOREIGN KEY (`admin_id`) REFERENCES `USER` (`id`);
ALTER TABLE `CONVERSATION` ADD FOREIGN KEY (`client_id`) REFERENCES `USER` (`id`);

ALTER TABLE `MESSAGE` ADD FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);
ALTER TABLE `MESSAGE` ADD FOREIGN KEY (`conversation_id`) REFERENCES `CONVERSATION` (`id`);

INSERT INTO `ROLE` (`id`,`name`) VALUES (1, 'Admin'), (2, 'Client');

INSERT INTO `user` (`id`,`name`,`first_name`,`email`,`password`, `role_id`, `created_at`, `updated_at`) 
VALUES (1, 'admin', 'admin', 'admin@admin.com', 'admin', 1, '2025-01-31 15:14:55.288000', '2025-01-31 15:14:55.288000'),
(2, 'Jean', 'Dupont', 'jean@test.com', 'jean', 2, '2025-01-31 15:14:55.288000', '2025-01-31 15:14:55.288000'),
(3, 'Michel', 'Durand', 'michel@test.com', 'michel', 2, '2025-01-31 15:14:55.288000', '2025-01-31 15:14:55.288000');
