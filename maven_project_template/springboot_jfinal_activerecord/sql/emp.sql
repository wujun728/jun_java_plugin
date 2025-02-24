CREATE TABLE `emp` (
  `id` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `age` int(11) DEFAULT 18
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `emp`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `emp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

CREATE TABLE `emp_balance` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `balance` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `emp_balance`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `emp_balance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;