package org.typroject.tyboot.core.auth.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class AuthModel implements Serializable {


    private static final long serialVersionUID = 326567545234532L;

    private String loginId;
}
