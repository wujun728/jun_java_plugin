# UML介绍

### 基本介绍

* UML——Unified modeling language（统一建模语言），是一种用于软件系统分析和设计的语言工具，
它用于帮助软件开发人员进行思考和记录思路的结果
* UML本身是一套符号的规定，就像数学符号和化学符号一样，这些符号用于描述软件模型中的各个元素和他们之间的关系，
比如类、接口、实现、泛化、依赖、组合、聚合等
* 使用UML来建模，常用的工具有Rational Rose，也可以使用一些插件来建模

### UML图

画UML图与写文章差不多，都是把自己的思想描述给别人看，关键在于思路和条理

UML图分类

* 用例图
* 静态结构图：类图、对象图、包图、组件图、部署图
* 动态行为图：交互图（时序图与协作图）、状态图、活动图

类图是描述类与类之间的关系的，是UML图中最核心的

# 类图

### UML类图

* 用于描述系统中的类（对象）本身的组成和类（对象）之间的各种静态关系
* 类之间的关系：依赖、泛化（继承）、实现、关联、聚合与组合

```java
public class Person {

    /**
     * ID
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### 依赖关系

只要是在类中用到了对方，那么他们之间就存在依赖关系。如果没有对方，连编译都通过不了

```java
public class PersonServiceBean {

    /**
     * 以成员变量的形式 依赖了 PersonDao 类
     */
    private PersonDao personDao;

    public void save(Person person) {
        // 以方法参数的形式 依赖了 Person 类
    }

    public IdCard getIdCard(Integer personId) {
        // 以方法返回类型的形式 依赖了 IdCard 类
        return new IdCard();
    }

    public void modify() {
        // 以局部变量的形式 依赖了 Department 类；这里违反了迪米特法则，但是依赖关系是存在的
        Department department = new Department();
    }
}

class PersonDao {}

class IdCard {}

class Person {}

class Department {}
```

### 泛化关系

泛化关系实际上就是继承关系，他是依赖关系的特例

```java
public abstract class AbstractDaoSupport{
    public void save(Object entity){
        
    }
    
    public void delete(Object id) {

    }
}

public class PersonServiceBean extends AbstractDaoSupport{
    
}
```

### 实现关系

实现关系实际上就是A类实现B类，他是依赖关系的特例

```java
public interface PersonService{
    
    void delete(Integer id);
}

public class PersonServiceImpl implements PersonService{
    
    @Override
    void delete(Integer id) {

    }
    
}
```

### 关联关系

关联关系就是类与类之间的联系，他是依赖关系的特例



