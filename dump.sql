-- Дамп структуры для таблицы scholarship_db.specialities
CREATE TABLE IF NOT EXISTS `specialities` (
  `id` int unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ratio_5` float unsigned DEFAULT NULL,
  `ratio_6` float unsigned DEFAULT NULL,
  `ratio_7` float unsigned DEFAULT NULL,
  `ratio_8` float unsigned DEFAULT NULL,
  `ratio_9` float unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.specialities
INSERT INTO `specialities` (`id`, `name`, `ratio_5`, `ratio_6`, `ratio_7`, `ratio_8`, `ratio_9`) VALUES
(1, 'Электронный маркетинг', 0, 1, 1.1, 1.3, 1.5),
(2, 'Программное обеспечение информационных технологий', 1, 1.2, 1.2, 1.4, 1.6),
(3, 'Экономика электронного бизнеса', 0, 1, 1.1, 1.3, 1.5),
(4, 'Информационные системы и технологии (в экономике)', 1, 1.2, 1.2, 1.4, 1.6);

-- Дамп структуры для таблицы scholarship_db.subjects
CREATE TABLE IF NOT EXISTS `subjects` (
  `id` int unsigned NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `hours` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.subjects
INSERT INTO `subjects` (`id`, `name`, `hours`) VALUES
(1, 'Основы алгоритмизации и программирования', 130),
(2, 'Маркетинговые исследования', 140),
(3, 'Программирование сетевых приложений', 100),
(4, 'Экономическая теория', 90),
(5, 'Эконометрика', 75);

-- Дамп структуры для таблицы scholarship_db.base_scholarship
CREATE TABLE IF NOT EXISTS `base_scholarship` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `value` float unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.base_scholarship
INSERT INTO `base_scholarship` (`id`, `value`) VALUES
(1, 112.58);


-- Дамп структуры для таблицы scholarship_db.specialities_has_subjects
CREATE TABLE IF NOT EXISTS `specialities_has_subjects` (
  `specialities_id` int unsigned NOT NULL,
  `subjects_id` int unsigned NOT NULL,
  PRIMARY KEY (`specialities_id`,`subjects_id`),
  KEY `fk_specialities_has_subjects_subjects1_idx` (`subjects_id`),
  KEY `fk_specialities_has_subjects_specialities1_idx` (`specialities_id`),
  CONSTRAINT `fk_specialities_has_subjects_specialities1` FOREIGN KEY (`specialities_id`) REFERENCES `specialities` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_specialities_has_subjects_subjects1` FOREIGN KEY (`subjects_id`) REFERENCES `subjects` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.specialities_has_subjects
INSERT INTO `specialities_has_subjects` (`specialities_id`, `subjects_id`) VALUES
(1, 1), (1, 2), (1, 3), (2, 1), (2, 4), (3, 1), (3, 3), (3, 4), (3, 5), (4, 1), (4, 5);

-- Дамп структуры для таблицы scholarship_db.students
CREATE TABLE IF NOT EXISTS `students` (
  `id` int unsigned NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `patronymic` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `education_form` enum('paid','free') DEFAULT 'paid',
  `specialities_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_students_specialities1_idx` (`specialities_id`),
  CONSTRAINT `fk_students_specialities1` FOREIGN KEY (`specialities_id`) REFERENCES `specialities` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.students
INSERT INTO `students` (`id`, `last_name`, `first_name`, `patronymic`, `email`, `phone`, `address`, `education_form`, `specialities_id` ) VALUES
(1,'Козлова', 'Александра', 'Андреевна', 'kozlova.sasha2003@mail.ru', '+375293615057', 'ул. Семеняко, 4-12', 'free', 1),
(2,'Бельская', 'Валерия', 'Алексеевна', 'valeryiabelskaya@gmail.com', '+375295963195', 'ул. Якуба Коласа, 28', 'free', 1),
(3,'Марчик', 'Божена', 'Валерьевна', 'bozhenam@gmail.com', '+3752447124548', 'ул. Фанипольская, 1', 'free', 2),
(4,'Зуёнок', 'Арина', 'Сергеевна', 'arinazuenok@mail.ru', '+375445103126', 'ул. Голубева, 36', 'paid', 3),
(5,'Тимчук', 'Юлия', 'Сергеевна', 'jullytimchuk@mail.ru', '+375296102210', 'ул. Каменногорская, 12', 'free', 4);

-- Дамп структуры для таблицы scholarship_db.special_scholarship
CREATE TABLE IF NOT EXISTS `special_scholarship` (
  `id` int unsigned NOT NULL,
  `social_scholarship` float unsigned DEFAULT NULL,
  `president_scholarship` float unsigned DEFAULT NULL,
  `special_scholarship` float unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `students_id_UNIQUE` (`id`),
  KEY `fk_special_scholarship_students1_idx` (`id`),
  CONSTRAINT `fk_special_scholarship_students1` FOREIGN KEY (`id`) REFERENCES `students` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.special_scholarship
INSERT INTO `special_scholarship` (`id`, `social_scholarship`, `president_scholarship`, `special_scholarship`) VALUES
(1, 90.06, 1290.6, 228.91);

-- Дамп структуры для таблицы scholarship_db.performance
CREATE TABLE IF NOT EXISTS `performance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_score` float unsigned DEFAULT NULL,
  `missed_hours` int unsigned DEFAULT NULL,
  `students_id` int unsigned NOT NULL,
  `subjects_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_performance_students1_idx` (`students_id`),
  KEY `fk_performance_subjects1_idx` (`subjects_id`),
  CONSTRAINT `fk_performance_students1` FOREIGN KEY (`students_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_performance_subjects1` FOREIGN KEY (`subjects_id`) REFERENCES `subjects` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.performance
INSERT INTO `performance` (`id`, `total_score`, `missed_hours`, `students_id`, `subjects_id`) VALUES
(1, 9, 0, 1, 1), (2, 10, 0, 1, 2), (3, 9.2, 2, 1, 3),
(4, 8, 0, 2, 1), (5, 9, 0, 2, 2), (6, 8.2, 0, 2, 3),
(7, 8.4, 0, 3, 1), (8, 8.2, 0, 3, 4),
(9, 9, 0, 4, 1), (10, 8, 0, 4, 3), (11, 8.75, 0, 4, 4), (12, 8.5, 0, 4, 5),
(13, 9.1, 0, 5, 1), (14, 9.8, 0, 5, 5);

-- Дамп структуры для таблицы scholarship_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('Administrator','Student') NOT NULL DEFAULT 'Student',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE = InnoDB  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.users
INSERT INTO `users` (`id`, `login`, `password`, `role`) VALUES
(1, 'stud', 'stud', 'Student'),
(2, 'admin', 'admin', 'Administrator');

-- Дамп структуры для таблицы scholarship_db.users_has_students
CREATE TABLE IF NOT EXISTS `users_has_students` (
`users_id` int unsigned NOT NULL,
`students_id` int unsigned NOT NULL,
 PRIMARY KEY (`users_id`,`students_id`),
KEY `fk_users_has_students_students1_idx` (`students_id`),
KEY `fk_users_has_students_users1_idx` (`users_id`),
CONSTRAINT `fk_users_has_students_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
CONSTRAINT `fk_users_has_students_students1` FOREIGN KEY (`students_id`) REFERENCES `students` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- Дамп данных для таблицы scholarship_db.users_has_students
INSERT INTO `users_has_students` (`users_id`, `students_id`) VALUES
(1, 1);