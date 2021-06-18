
CREATE TABLE `d_open_survey` (
  `f_id` varchar(32) NOT NULL,
  `f_allowreport` int(11) DEFAULT NULL,
  `f_createtime` datetime DEFAULT NULL,
  `f_description` varchar(255) DEFAULT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `f_pagecount` int(11) DEFAULT NULL,
  `f_questioncount` int(11) DEFAULT NULL,
  `f_samplecount` int(11) DEFAULT NULL,
  `f_samplesum` int(11) DEFAULT NULL,
  `f_show` int(11) DEFAULT NULL,
  `f_surveyurl` varchar(255) DEFAULT NULL,
  `f_tag` varchar(255) DEFAULT NULL,
  `f_type` int(11) DEFAULT NULL,
  `f_typename` varchar(255) DEFAULT NULL,
  `f_uid` varchar(255) DEFAULT NULL,
  `f_usersurveycount` int(11) DEFAULT NULL,
  `f_viewersum` int(11) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_news` (
  `ID` varchar(255) NOT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NEWS_TIME` datetime DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_sys_permission` (
  `ID` varchar(32) NOT NULL,
  `HIDE` int(11) DEFAULT NULL,
  `SKEY` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PARENT_KEY` varchar(255) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_sys_role` (
  `ID` varchar(32) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_sys_role_permission` (
  `ID` varchar(32) NOT NULL,
  `PERMISSION_ID` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_sys_user` (
  `ID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `LAST_LOGIN_TIME` datetime DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORGANIZE_ID` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SALT` varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `TRUE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_jhdc5ipoa6kdxy91r16g3df4i` (`EMAIL`),
  UNIQUE KEY `UK_mocatd9fh3tj7mv0815baepmq` (`NAME`),
  UNIQUE KEY `UK_ar8vsmvija6p1mlnw7vkw7aql` (`TRUE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_sys_user_role` (
  `ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
