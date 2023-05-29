package mybatis.mate.dict.config;

import mybatis.mate.annotation.FieldBind;
import mybatis.mate.dict.entity.StatusEnum;
import mybatis.mate.dict.mapper.UserMapper;
import mybatis.mate.sets.IDataBind;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataBind implements IDataBind {
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 从数据库或缓存中获取
     */
    private Map<String, String> SEX_MAP = new ConcurrentHashMap<String, String>() {{
        put("0", "女");
        put("1", "男");
    }};

    /**
     * 设置元数据对象<br>
     * 根据源对象映射绑定指定属性（自行处理缓存逻辑）
     *
     * @param fieldBind  数据绑定注解
     * @param fieldValue 属性值
     * @param metaObject 元数据对象 {@link MetaObject}
     * @return
     */
    @Override
    public void setMetaObject(FieldBind fieldBind, Object fieldValue, MetaObject metaObject) {

        // 调用数据库获取数据，这里采用 getBean 方式避免 spring boot 检查循环依赖问题
        Long userCount = applicationContext.getBean(UserMapper.class).selectCount(null);
        System.out.println("userCount=" + userCount);

        System.err.println("字段类型：" + fieldBind.type() + "，绑定属性值：" + fieldValue);
        // 数据库中数据转换
        if (BindType.USER_SEX.equals(fieldBind.type())) {
            metaObject.setValue(fieldBind.target(), SEX_MAP.get(String.valueOf(fieldValue)));
        }
        // 枚举数据转换
        else if (BindType.USER_STATUS.equals(fieldBind.type())) {
            metaObject.setValue(fieldBind.target(), StatusEnum.get((Integer) fieldValue));
        }
    }
}
