package org.typroject.tyboot.core.rdbms.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by magintursh on 2017-06-17.
 */

@Data
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 457432767545432436L;


    protected Long  sequenceNbr;

    protected Date recDate;

    protected String recUserId;
}
