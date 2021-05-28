create database IF NOT EXISTS `test`;

use test;

CREATE TABLE IF NOT EXISTS `t_order_0` (
  `order_id` INT NOT NULL,
  `user_id`  INT NOT NULL,
  PRIMARY KEY (`order_id`)
);
CREATE TABLE IF NOT EXISTS `t_order_1` (
  `order_id` INT NOT NULL,
  `user_id`  INT NOT NULL,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE IF NOT EXISTS `t_order_item_0` (
  `item_id`  INT NOT NULL,
  `order_id` INT NOT NULL,
  `user_id`  INT NOT NULL,
  PRIMARY KEY (`item_id`)
);
CREATE TABLE IF NOT EXISTS `t_order_item_1` (
  `item_id`  INT NOT NULL,
  `order_id` INT NOT NULL,
  `user_id`  INT NOT NULL,
  PRIMARY KEY (`item_id`)
);