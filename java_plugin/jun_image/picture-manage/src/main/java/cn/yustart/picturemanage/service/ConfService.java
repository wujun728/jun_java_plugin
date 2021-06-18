package cn.yustart.picturemanage.service;

import cn.yustart.picturemanage.entity.ConfEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:11
 */
public interface ConfService extends IService<ConfEntity> {
    boolean set(String key, String value);

    String get(String key, String defaultValue);

    String get(String key);

    boolean del(String key);
}
