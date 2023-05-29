package mybatis.mate.ddl.postgres;

import mybatis.mate.ddl.SimpleDdl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PostgresDdl extends SimpleDdl {

    /**
     * 执行 SQL 脚本方式
     */
    @Override
    public List<String> getSqlFiles() {
        return Arrays.asList(
                // 内置包方式
                "db/tag-schema.sql",

                // 文件绝对路径方式（修改为你电脑的地址）
                "D:\\IdeaProjects\\mybatis-mate-examples\\mybatis-mate-ddl-postgres\\src\\main\\resources\\db\\tag-data.sql"
        );
    }
}
