package mybatis.mate.datascope.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private Long departmentId;
    private String username;
    private String mobile;

}
