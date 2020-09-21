/**
 * CronTriggerMeta.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 描述 : CronTriggerMeta.java
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CronTriggerMeta extends TriggerMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : cron表达式
     */
    private String cron;

    /**
     * 描述 : 时区ID
     */
    private String timeZoneId;

}
