package mybatis.mate.sm.config;

import mybatis.mate.annotation.FieldBind;
import mybatis.mate.sets.IDataBind;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class DataBind implements IDataBind {

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
        System.err.println("字段类型：" + fieldBind.type() + "，绑定属性值：" + fieldValue);
        metaObject.setValue(fieldBind.target(), "bind_md_" + fieldValue);
    }
}
