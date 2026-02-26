package com.jun.plugin.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 无任何配置文件 + 手动new Druid数据源 + 全量MyBatis操作示例
 */
public class MyBatisDruidMainTest {
    // 全局SqlSessionFactory（单例）
    private static SqlSessionFactory sqlSessionFactory;

    // 静态代码块：初始化MyBatis环境（Druid数据源 + 配置）
    static {
        // 步骤1：手动构建Druid数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3307/db_test?useSSL=false&serverTimezone=UTC&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("mysqladmin");
        // Druid连接池优化配置
        druidDataSource.setInitialSize(5);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMinIdle(3);

        // 步骤2：构建MyBatis Environment
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, druidDataSource);

        // 步骤3：构建MyBatis Configuration（注册Mapper + 配置全局属性 + 注册类型转换器）
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true); // 下划线转驼峰
        configuration.addMapper(UserMapper.class); // 注册UserMapper
        configuration.addMapper(BlogMapper.class); // 注册BlogMapper
       configuration.getTypeHandlerRegistry().register(DateTypeHandler.class); // 注册自定义类型转换器

        // 步骤4：构建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    // ==================== 核心测试方法：main（调用所有示例） ====================
    public static void main(String[] args) {
        // 查询单个
        queryOne();

        // 查询多个
        queryAll();

        // 增加
        add();

        // 删除
        delete();

        // 修改
        update();

        // 带转换器查询
        selectBlogByIdWithConverter();

        // 带转换器新增
        addBlogWithConverter();

        // 使用map作为输入参数
        selectBlogWithHashMap();

        // 返回值是HashMap
        resultByMap();

        // 使用foreach 迭代List
        listForeach();

        // 使用foreach 迭代数组
        array();

        // 使用foreach 迭代对象数组
        objectForeach();

        // 使用foreach 迭代对象集合
        batchInsertBlogList();
    }

    // ==================== 测试方法实现 ====================
    /**
     * 1. 查询单个（根据ID查询User）
     */
    private static void queryOne() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findUserById(1);
            System.out.println("===== 查询单个用户 =====");
            System.out.println(user == null ? "用户不存在" : user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("单个查询失败");
        }
    }

    /**
     * 2. 查询多个（查询所有User）
     */
    private static void queryAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.findAllUser();
            System.out.println("\n===== 查询所有用户 =====");
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("批量查询失败");
        }
    }

    /**
     * 3. 增加（新增单个User）
     */
    private static void add() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User newUser = new User("MyBatis Add Test", 25, new Date());
            int affectedRows = userMapper.insertUser(newUser);
            System.out.println("\n===== 新增用户 =====");
            System.out.println("新增受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新增失败");
        }
    }

    /**
     * 4. 删除（根据ID删除User）
     */
    private static void delete() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int affectedRows = userMapper.deleteUserById(2); // 假设删除ID=2的用户
            System.out.println("\n===== 删除用户 =====");
            System.out.println("删除受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    /**
     * 5. 修改（更新User信息）
     */
    private static void update() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User updateUser = new User();
            updateUser.setId(1);
            updateUser.setUserName("MyBatis Update Test");
            updateUser.setAge(29);
            int affectedRows = userMapper.updateUser(updateUser);
            System.out.println("\n===== 修改用户 =====");
            System.out.println("修改受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败");
        }
    }

    /**
     * 6. 带转换器查询（根据发布时间查询Blog）
     */
    private static void selectBlogByIdWithConverter() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Date publishTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2026-01-19 10:00:00");
            Blog blog = blogMapper.findBlogByPublishTime(publishTime);
            System.out.println("\n===== 带转换器查询博客 =====");
            System.out.println(blog == null ? "博客不存在" : blog);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("带转换器查询失败");
        }
    }

    /**
     * 7. 带转换器新增（新增Blog并使用自定义日期转换器）
     */
    private static void addBlogWithConverter() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Blog newBlog = new Blog("转换器测试博客", "这是一篇带日期转换器的博客", new Date(), 1);
            int affectedRows = blogMapper.insertBlogWithConverter(newBlog);
            System.out.println("\n===== 带转换器新增博客 =====");
            System.out.println("新增受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("带转换器新增失败");
        }
    }

    /**
     * 8. 使用Map作为输入参数（查询Blog）
     */
    private static void selectBlogWithHashMap() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Map<String, Object> paramMap = new HashMap<>();
            List<Integer> authorIds = Arrays.asList(1, 2, 3);
            paramMap.put("authorIds", authorIds);
            List<Blog> blogList = blogMapper.findBlogByMap(paramMap);
            System.out.println("\n===== Map参数查询博客 =====");
            blogList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Map参数查询失败");
        }
    }

    /**
     * 9. 返回值是HashMap（查询User并返回Map）
     */
    private static void resultByMap() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> userMap = userMapper.findUserMapById(1);
            System.out.println("\n===== 返回值为HashMap =====");
            userMap.forEach((key, value) -> System.out.println(key + "：" + value));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("HashMap返回失败");
        }
    }

    /**
     * 10. 使用foreach 迭代List（批量查询User）
     */
    private static void listForeach() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<Integer> idList = Arrays.asList(1, 3, 5);
            List<User> userList = userMapper.findUserListByIds(idList);
            System.out.println("\n===== foreach迭代List =====");
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("List foreach迭代失败");
        }
    }

    /**
     * 11. 使用foreach 迭代数组（批量删除User）
     */
    private static void array() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Integer[] idArray = new Integer[]{3, 4, 5};
            int affectedRows = userMapper.deleteUserByArray(idArray);
            System.out.println("\n===== foreach迭代数组 =====");
            System.out.println("批量删除受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数组 foreach迭代失败");
        }
    }

    /**
     * 12. 使用foreach 迭代对象数组（批量查询Blog）
     */
    private static void objectForeach() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Blog[] blogArray = new Blog[2];
            blogArray[0] = new Blog();
            blogArray[0].setId(1);
            blogArray[1] = new Blog();
            blogArray[1].setId(2);
            List<Blog> blogList = blogMapper.findBlogByObjectArray(blogArray);
            System.out.println("\n===== foreach迭代对象数组 =====");
            blogList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("对象数组 foreach迭代失败");
        }
    }

    /**
     * 13. 使用foreach 迭代对象集合（批量新增Blog）
     */
    private static void batchInsertBlogList() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            List<Blog> blogList = new ArrayList<>();
            blogList.add(new Blog("批量新增博客1", "批量插入内容1", new Date(), 1));
            blogList.add(new Blog("批量新增博客2", "批量插入内容2", new Date(), 1));
            int affectedRows = blogMapper.batchInsertBlog(blogList);
            System.out.println("\n===== foreach迭代对象集合 =====");
            System.out.println("批量新增博客受影响行数：" + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("对象集合 foreach迭代失败");
        }
    }
}