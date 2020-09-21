/**
 * SimpleTriggerMeta.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartz.SimpleTrigger;

import java.io.Serializable;

/**
 * 描述 : SimpleTriggerMeta.java
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleTriggerMeta extends TriggerMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 重复次数
     */
    private Integer repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;

    /**
     * 描述 : 执行间隔
     */
    private Long intervalInMilliseconds;

}
