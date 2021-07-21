CREATE TABLE d_open_survey (
	f_id varchar NOT NULL,
	f_allowreport integer,
	f_createtime timestamp,
	f_description varchar,
	f_name varchar,
	f_pagecount integer,
	f_questioncount integer,
	f_samplecount integer,
	f_samplesum integer,
	f_show integer,
	f_surveyurl varchar,
	f_tag varchar,
	f_type integer,
	f_typename varchar,
	f_uid varchar,
	f_usersurveycount integer,
	f_viewersum integer,
	PRIMARY KEY (f_id)
);

CREATE TABLE t_news (
	ID varchar NOT NULL,
	ADDRESS varchar,
	CREATE_TIME timestamp,
	DESCRIPTION varchar,
	NEWS_TIME timestamp,
	TITLE varchar,
	USER_ID varchar,
	PRIMARY KEY (ID)
);

CREATE TABLE t_sys_permission (
	ID varchar NOT NULL,
	HIDE integer,
	SKEY varchar,
	NAME varchar,
	PARENT_KEY varchar,
	SORT integer,
	URL varchar,
	PRIMARY KEY (ID)
);

CREATE TABLE t_sys_role (
	ID varchar NOT NULL,
	CODE varchar,
	NAME varchar,
	REMARK varchar,
	PRIMARY KEY (ID)
);

CREATE TABLE t_sys_role_permission (
	ID varchar NOT NULL,
	PERMISSION_ID varchar,
	ROLE_ID varchar,
	PRIMARY KEY (ID)
);

CREATE TABLE t_sys_user (
	ID varchar NOT NULL,
	CREATE_TIME timestamp,
	EMAIL varchar,
	LAST_LOGIN_TIME timestamp,
	MODIFY_TIME timestamp,
	NAME varchar,
	ORGANIZE_ID varchar,
	PASSWORD varchar,
	SALT varchar,
	STATUS integer,
	TRUE_NAME varchar,
	PRIMARY KEY (ID)
);

CREATE TABLE t_sys_user_role (
	ID varchar NOT NULL,
	ROLE_ID varchar,
	USER_ID varchar,
	PRIMARY KEY (ID)
);

