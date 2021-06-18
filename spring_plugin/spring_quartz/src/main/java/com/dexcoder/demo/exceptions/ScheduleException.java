package com.dexcoder.demo.exceptions;

import com.dexcoder.commons.exceptions.DexcoderException;

/**
 * 自定义异常
 * 
 * Created by liyd on 12/19/14.
 */
public class ScheduleException extends DexcoderException {

    /** serialVersionUID */
    private static final long serialVersionUID = -1921648378954132894L;

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
        super(e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public ScheduleException(String code, String message) {
        super(code, message);
    }
}
