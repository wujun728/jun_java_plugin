CREATE database test;

use test;

CREATE TABLE IF NOT EXISTS `test`.`user` (
  `id` INT NULL AUTO_INCREMENT,
  `username` VARCHAR(128) NULL,
  `password` VARCHAR(128) NULL,
  `age` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `test`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL,
  `price` DECIMAL(8,2) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

INSERT INTO `test`.`book` (`name`, `price`) VALUES ('book1', '10.1');
INSERT INTO `test`.`book` (`name`, `price`) VALUES ('book2', '9.9');

CREATE TABLE IF NOT EXISTS `mydb`.`userbook` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `book_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB

INSERT INTO `test`.`userbook` (`user_id`, `book_id`) VALUES ('1', '1');
INSERT INTO `test`.`userbook` (`user_id`, `book_id`) VALUES ('1', '2');
