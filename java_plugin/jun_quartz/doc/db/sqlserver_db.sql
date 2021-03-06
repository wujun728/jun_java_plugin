CREATE TABLE QRTZ_BLOB_TRIGGERS(
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[BLOB_DATA] [image] NULL
)

CREATE TABLE QRTZ_CALENDARS(
[CALENDAR_NAME] [varchar](200) NOT NULL,
[CALENDAR] [image] NOT NULL
)




CREATE TABLE QRTZ_CRON_TRIGGERS(
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[CRON_EXPRESSION] [varchar](120) NOT NULL,
[TIME_ZONE_ID] [varchar](80) NULL
)


CREATE TABLE QRTZ_FIRED_TRIGGERS(
[ENTRY_ID] [varchar](95) NOT NULL,
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[IS_VOLATILE] [varchar](1) NOT NULL,
[INSTANCE_NAME] [varchar](200) NOT NULL,
[FIRED_TIME] [bigint] NOT NULL,
[PRIORITY] [int] NOT NULL,
[STATE] [varchar](16) NOT NULL,
[JOB_NAME] [varchar](200) NULL,
[JOB_GROUP] [varchar](200) NULL,
[IS_STATEFUL] [varchar](1) NULL,
[REQUESTS_RECOVERY] [varchar](1) NULL
)

CREATE TABLE QRTZ_JOB_DETAILS(
[JOB_NAME] [varchar](200) NOT NULL,
[JOB_GROUP] [varchar](200) NOT NULL,
[DESCRIPTION] [varchar](250) NULL,
[JOB_CLASS_NAME] [varchar](250) NOT NULL,
[IS_DURABLE] [varchar](1) NOT NULL,
[IS_VOLATILE] [varchar](1) NOT NULL,
[IS_STATEFUL] [varchar](1) NOT NULL,
[REQUESTS_RECOVERY] [varchar](1) NOT NULL,
[JOB_DATA] [image] NULL
)

CREATE TABLE QRTZ_JOB_LISTENERS(
[JOB_NAME] [varchar](200) NOT NULL,
[JOB_GROUP] [varchar](200) NOT NULL,
[JOB_LISTENER] [varchar](200) NOT NULL
)

CREATE TABLE QRTZ_LOCKS(
[LOCK_NAME] [varchar](40) NOT NULL
)

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS(
[TRIGGER_GROUP] [varchar](200) NOT NULL
)

CREATE TABLE QRTZ_SCHEDULER_STATE(
[INSTANCE_NAME] [varchar](200) NOT NULL,
[LAST_CHECKIN_TIME] [bigint] NOT NULL,
[CHECKIN_INTERVAL] [bigint] NOT NULL
) 

CREATE TABLE QRTZ_SIMPLE_TRIGGERS(
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[REPEAT_COUNT] [bigint] NOT NULL,
[REPEAT_INTERVAL] [bigint] NOT NULL,
[TIMES_TRIGGERED] [bigint] NOT NULL
)

CREATE TABLE QRTZ_TRIGGER_LISTENERS(
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[TRIGGER_LISTENER] [varchar](200) NOT NULL
)

CREATE TABLE QRTZ_TRIGGERS(
[TRIGGER_NAME] [varchar](200) NOT NULL,
[TRIGGER_GROUP] [varchar](200) NOT NULL,
[JOB_NAME] [varchar](200) NOT NULL,
[JOB_GROUP] [varchar](200) NOT NULL,
[IS_VOLATILE] [varchar](1) NOT NULL,
[DESCRIPTION] [varchar](250) NULL,
[NEXT_FIRE_TIME] [bigint] NULL,
[PREV_FIRE_TIME] [bigint] NULL,
[PRIORITY] [int] NULL,
[TRIGGER_STATE] [varchar](16) NOT NULL,
[TRIGGER_TYPE] [varchar](8) NOT NULL,
[START_TIME] [bigint] NOT NULL,
[END_TIME] [bigint] NULL,
[CALENDAR_NAME] [varchar](200) NULL,
[MISFIRE_INSTR] [smallint] NULL,
[JOB_DATA] [image] NULL
)