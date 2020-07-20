Bean工具-BeanUtil
===

## 什么是Bean
把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。实际上JavaBean就是一个Java类，在这个Java类中就默认形成了一种规则——对属性进行设置和获得。而反之将说Java类就是一个JavaBean，这种说法是错误的，因为一个java类中不一定有对属性的设置和获得的方法（也就是不一定有set和get方法）。

通常Java中对Bean的定义是包含setXXX和getXXX方法的对象，在Hutool中，采取一种简单的判定Bean的方法：是否存在只有一个参数的setXXX方法。

Bean工具类主要是针对这些setXXX和getXXX方法进行操作，比如将Bean对象转为Map等等。

## 方法

### 是否为Bean对象
`BeanUtil.isBean`方法根据是否存在只有一个参数的setXXX方法来判定是否是一个Bean对象。这样的判定方法主要目的是保证至少有一个setXXX方法用于属性注入。

```java
boolean isBean = BeanUtil.isBean(HashMap.class);//false
```

### 内省 Introspector
把一类中需要进行设置和获得的属性访问权限设置为private（私有的）让外部的使用者看不见摸不着，而通过public（共有的）set和get方法来对其属性的值来进行设置和获得，而内部的操作具体是怎样的？外界使用的人不用不知道，这就称为内省。

Hutool中对内省的封装包括：

1. `BeanUtil.getPropertyDescriptors` 获得Bean字段描述数组

```java
PropertyDescriptor[] propertyDescriptors = BeanUtil.getPropertyDescriptors(SubPerson.class);
```

2. `BeanUtil.getFieldNamePropertyDescriptorMap` 获得字段名和字段描述Map
3. `BeanUtil.getPropertyDescriptor` 获得Bean类指定属性描述

### Bean属性注入
`BeanUtil.fillBean`方法是bean注入的核心方法，此方法传入一个ValueProvider接口，通过实现此接口来获得key对应的值。CopyOptions参数则提供一些注入属性的选项。

CopyOptions的配置项包括：
1. `editable` 限制的类或接口，必须为目标对象的实现接口或父类，用于限制拷贝的属性，例如一个类我只想复制其父类的一些属性，就可以将editable设置为父类。
2. `ignoreNullValue` 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
3. `ignoreProperties` 忽略的属性列表，设置一个属性列表，不拷贝这些属性值
4. `ignoreError` 是否忽略字段注入错误

可以通过`CopyOptions.create()`方法创建一个默认的配置项，通过setXXX方法设置每个配置项。

ValueProvider接口需要实现两个方法：
1. `value`方法是通过key和目标类型来从任何地方获取一个值，并转换为目标类型，如果返回值不和目标类型匹配，将会自动调用`Convert.convert`方法转换。
2. `containsKey`方法主要是检测是否包含指定的key，如果不包含这个key，其对应的属性将会忽略注入。

首先定义两个bean：

```java
public class Person{
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}

public class SubPerson extends Person {
	public static final String SUBNAME = "TEST";

	private UUID id;
	private String subName;
	private Boolean isSlow;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public Boolean isSlow() {
		return isSlow;
	}
	public void setSlow(Boolean isSlow) {
		this.isSlow = isSlow;
	}
}
```

然后注入这个bean：
```java
Person person = BeanUtil.fillBean(new Person(), new ValueProvider<String>(){

	@Override
	public Object value(String key, Class<?> valueType) {
		switch (key) {
			case "name":
				return "张三";
			case "age":
				return 18;
		}
		return null;
	}

	@Override
	public boolean containsKey(String key) {
		//总是存在key
		return true;
	}
	
}, CopyOptions.create());

Assert.assertEquals(person.getName(), "张三");
Assert.assertEquals(person.getAge(), 18);
```

同时，Hutool还提供了`BeanUtil.toBean`方法，此处并不是传Bean对象，而是Bean类，Hutool会自动调用默认构造方法创建对象。

基于`BeanUtil.fillBean`方法Hutool还提供了Map对象键值对注入Bean，其方法有：

1. `BeanUtil.fillBeanWithMap` 使用Map填充bean

```java
HashMap<String, Object> map = CollUtil.newHashMap();
map.put("name", "Joe");
map.put("age", 12);
map.put("openId", "DFDFSDFWERWER");

SubPerson person = BeanUtil.fillBeanWithMap(map, new SubPerson(), false);
```

2. `BeanUtil.fillBeanWithMapIgnoreCase` 使用Map填充bean，忽略大小写

```java
HashMap<String, Object> map = CollUtil.newHashMap();
map.put("Name", "Joe");
map.put("aGe", 12);
map.put("openId", "DFDFSDFWERWER");
SubPerson person = BeanUtil.fillBeanWithMapIgnoreCase(map, new SubPerson(), false);
```

同时提供了map转bean的方法，与fillBean不同的是，此处并不是传Bean对象，而是Bean类，Hutool会自动调用默认构造方法创建对象。当然，前提是Bean类有默认构造方法（空构造），这些方法有：

1. `BeanUtil.mapToBean`

```java
HashMap<String, Object> map = CollUtil.newHashMap();
map.put("a_name", "Joe");
map.put("b_age", 12);
// 设置别名，用于对应bean的字段名
HashMap<String, String> mapping = CollUtil.newHashMap();
mapping.put("a_name", "name");
mapping.put("b_age", "age");
Person person = BeanUtil.mapToBean(map, Person.class, CopyOptions.create().setFieldMapping(mapping));
```

2. `BeanUtil.mapToBeanIgnoreCase`

```java
HashMap<String, Object> map = CollUtil.newHashMap();
map.put("Name", "Joe");
map.put("aGe", 12);

Person person = BeanUtil.mapToBeanIgnoreCase(map, Person.class, false);
```

### Bean转为Map
`BeanUtil.beanToMap`方法则是将一个Bean对象转为Map对象。

```java
SubPerson person = new SubPerson();
person.setAge(14);
person.setOpenid("11213232");
person.setName("测试A11");
person.setSubName("sub名字");

Map<String, Object> map = BeanUtil.beanToMap(person);
```

### Bean转Bean
Bean之间的转换主要是相同属性的复制，因此方法名为`copyProperties`，此方法支持Bean和Map之间的字段复制。

`BeanUtil.copyProperties`方法同样提供一个`CopyOptions`参数用于自定义属性复制。

```java
SubPerson p1 = new SubPerson();
p1.setSlow(true);
p1.setName("测试");
p1.setSubName("sub测试");

Map<String, Object> map = MapUtil.newHashMap();

BeanUtil.copyProperties(p1, map);
```

