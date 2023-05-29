package mybatis.mate.encrypt.entity.vo;

import lombok.Getter;
import lombok.Setter;
import mybatis.mate.encrypt.entity.User;
import mybatis.mate.encrypt.entity.UserInfo;

import java.util.List;

@Getter
@Setter
public class UserDto extends User {
    private List<UserInfo> userInfos;

}
