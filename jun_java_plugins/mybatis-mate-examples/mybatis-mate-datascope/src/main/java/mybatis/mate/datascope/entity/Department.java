package mybatis.mate.datascope.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Department {
    private Long id;
    private Long pid;
    private String name;
    private Integer sort;

}
