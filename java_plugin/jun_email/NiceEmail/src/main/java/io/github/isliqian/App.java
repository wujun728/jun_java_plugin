package io.github.isliqian;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, InvocationTargetException, IllegalAccessException {

        //查询操作
        System.out.println( "Hello Sex-JDBC!" );
        List<User> list= SexJDBC.select
                ("select * from tb_user order by id desc",User.class);

        System.out.println(list.size());

        for (User user:list){
            System.out.println("Sex-JDBC:"+user.getUsername());
        }
        /*
         //删除操作
        SexJDBC.delete("delete from tb_user where password=111");
        */
        //添加操作
        User user=new User();
        user.setId(null);
        user.setNow("23424234");
        user.setUsername("test");
        user.setPassword("lala");
        user.setState(true);
        SexJDBC.insert("insert into tb_user (id,username,password,now,state) VALUES(?,?,?,?,?)", user);

      /*  SexJDBC.update("update tb_user set username = ? , password = ? , now = ? ,state = ? where id = ? ",user);*/

    }

}
