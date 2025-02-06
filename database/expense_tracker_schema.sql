-- Table structure for table `budgets`
DROP TABLE IF EXISTS `budgets`;

CREATE TABLE `budgets` (
  `budget_id` bigint NOT NULL AUTO_INCREMENT,
  `budget_amount` double NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`budget_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `budgets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

-- Table structure for table `categories`
DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`)
);

-- Table structure for table `category_types`
DROP TABLE IF EXISTS `category_types`;

CREATE TABLE `category_types` (
  `category_type_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_type_id`)
);

-- Table structure for table `expenses`
DROP TABLE IF EXISTS `expenses`;

CREATE TABLE `expenses` (
  `expense_id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `expense_amount` double NOT NULL,
  `description` text,
  `purchase_location` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`expense_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `expenses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

-- Table structure for table `goals`
DROP TABLE IF EXISTS `goals`;

CREATE TABLE `goals` (
  `goal_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `goal_type` varchar(50) DEFAULT NULL,
  `goal_amount` double NOT NULL,
  `target_date` date NOT NULL,
  `progress` int DEFAULT '0',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`goal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `goals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

-- Table structure for table `items`
DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `item_amount` double NOT NULL,
  `expense_id` bigint DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `expense_id` (`expense_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`expense_id`) REFERENCES `expenses` (`expense_id`),
  CONSTRAINT `items_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
);

-- Table structure for table `monthly`
DROP TABLE IF EXISTS `monthly`;

CREATE TABLE `monthly` (
  `monthly_id` bigint NOT NULL AUTO_INCREMENT,
  `monthly_percentage` double DEFAULT NULL,
  `monthly_amount` double DEFAULT NULL,
  `spent_percentage` double DEFAULT NULL,
  `spent_amount` double DEFAULT NULL,
  `remaining_percentage` double DEFAULT NULL,
  `remaining_amount` double DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`monthly_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `monthly_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
);

-- Table structure for table `users`
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `new_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
);

-- Table structure for table `weekly`
DROP TABLE IF EXISTS `weekly`;

CREATE TABLE `weekly` (
  `weekly_id` bigint NOT NULL AUTO_INCREMENT,
  `week_number` int NOT NULL,
  `weekly_percentage` double DEFAULT NULL,
  `weekly_amount` double DEFAULT NULL,
  `spent_percentage` double DEFAULT NULL,
  `spent_amount` double DEFAULT NULL,
  `remaining_percentage` double DEFAULT NULL,
  `remaining_amount` double DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`weekly_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `weekly_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
);
