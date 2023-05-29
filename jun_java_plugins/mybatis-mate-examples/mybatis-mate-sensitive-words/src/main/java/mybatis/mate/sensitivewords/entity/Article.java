package mybatis.mate.sensitivewords.entity;

import lombok.Getter;
import lombok.Setter;
import mybatis.mate.sensitivewords.config.Sensitived;

@Getter
@Setter
public class Article implements Sensitived {
    // 内容
    private String content;
    // 阅读数
    private Integer see;
    // 字数
    private Long size;

}
