
package org.typroject.tyboot.core.auth.authentication;

import org.typroject.tyboot.core.auth.enumeration.IdType;
import org.typroject.tyboot.core.auth.face.model.AuthModel;
import org.typroject.tyboot.core.auth.face.model.LoginInfoModel;
import org.typroject.tyboot.core.foundation.enumeration.UserType;

/**
 * Created by yaohelang on 2017/9/20.
 */
public interface LoginAuthenticatorHandler {
    
     LoginInfoModel doAuthenticate(IdType idType, UserType userType, AuthModel authModel);
}
