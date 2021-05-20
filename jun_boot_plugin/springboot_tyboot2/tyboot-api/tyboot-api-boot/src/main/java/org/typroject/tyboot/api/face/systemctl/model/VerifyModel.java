package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class VerifyModel implements Serializable
{
    private String loginId;
    private String smsType;
    private String countryName;
    private String phoneCode;


}
