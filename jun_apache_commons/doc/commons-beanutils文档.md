**commons-beanutils****文档**

[开源框架学习](http://blog.163.com/among_1985/blog/) 2008-12-11 08:28:48 阅读191 评论1  字号：大中小 订阅 

**1.**  **概述**

commons-beanutil开源库是apache组织的一个基础的开源库，为apache中许多类提供工具方法，学习它是学习其他开源库实现的基础。

Commons-beanutil中包含大量和JavaBean操作有关的工具方法，使用它可以轻松利用Java反射机制来完成代码中所需要的功能，而不需要详细研究反射的原理和使用，同时，该类库中提出了动态Bean的概念，不但提供现有JavaBean的所有功能，而且还可以在运行时动态的对Bean中的属性数据类型进行修改以及增删属性。

本文研究的是v1.7版本的commons-utils类库。

 

**2.**  **转换器**

**2.1.** **概述**

转换器用来将输入数据转换成需要的数据类型，同时提供统一的接口，方便客户代码使用和扩展。

Commons-beanutils包中，所有转换器都从org.apache.commons.beanutils.Converter接口集成，添加自己需要的实现。

转换器分为以下三个部分：

l     数组转换器

l     普通转换器

l     地区敏感的转换器

l     转换器工具类

Converter子类包含的都是转换器的实现，一般情况下，不需要直接实例化这些类，只需要使用ConvertUtil中convert方法，就可以进行数据类型的转换。高级用户不但可以使用默认的转换方式，还可以向ConvertUtils中注册新的或替代原有的转换器，实现需要的业务逻辑。

 

**2.2.**  **转换器接口**

转换器接口的详细信息如下：

 

| **类名**  | **描述**                                                     |
| --------- | ------------------------------------------------------------ |
| Converter | BeanUtil框架中使用的类型转换接口，可以将输入数据转换成需要的类型 |

 

**2.3.** **数组转换器**

**2.3.1****.**  **概述**

数组转换器的实现被封装在org.apache.commons.beanutils.converters包中。它的功能是将一定格式的输入字符串转换成不同类型的数组，输入数据以逗号分隔，开头和结尾可以用大括号括起来，例如：“{1, 2, 3, 4, 5}”。

所有数组转换器实现都从一个名为AbstractArrayConverter的抽象基类中集成，这个类提供了解析输入字符串的工具方法。

**2.3.2****.**  **类说明**

数组转换器的详细类说明如下：

 

| **类名**                | **描述**                                                     |
| ----------------------- | ------------------------------------------------------------ |
| AbstractArrayConverter  | 用来将输入字符串转换成数组的抽象类，提供了所有ArrayConverter需要的公共方法。 |
| BooleanArrayConverter   | 将输入的任何对象转换成boolean数组，传入对象要满足以下几个条件后才能正确转换：  1.   传入对象为boolean数组，直接返回。  2.   传入对象为String数组，只要数组中的每个元素满足特定条件，就可以正常解析为boolean数组。  3.   传入对象为其他类型，只要对象的toString()方法返回的字符串为逗号分隔的格式，并且每部分满足特定条件，就可以解析为boolean数组。  **可以向boolean类型转换的字符串如下**：  l      yes，y，true，on，1被转换为true  l      no，n，false，off，0被转换成false  l      其他字符串为非法字符串，如果遇到就停止转换，抛出异常或返回默认值。  **原有代码实现的缺陷和改进方案**：  1.   字符串数组解析算法重复：可以通过提取公共函数的方法消除重复。  2.   try/catch嵌套混乱：解决方法同上，只要提取公共方法后自然就可以解决这个问题。  3.   特殊字符串被硬编码，例如yes，y，no，n等：将这些特殊字串提取成常量，放入映射表中维护，减少复杂的判断语句。 |
| ByteArrayConverter      | 将传入对象转换为byte数组，如果转换失败，就抛出异常。  仍然有重复代码的问题。 |
| CharacterArrayConverter | 将对象转换为char数组                                         |
| DoubleArrayConverter    | 将对象转换成double数组                                       |
| FloatArrayConverter     | 将对象转换成float数组                                        |
| IntegerArrayConverter   | 将对象转换成int数组                                          |
| LongArrayConverter      | 将对象转换成long数组                                         |
| ShortArrayConverter     | 将对象转换成short数组                                        |
| StringArrayConverter    | javadoc中说是将String数组转换成String数组，但不知道这样有什么意义。  查看代码后发现算法只能转换int型的数组为String数组，其他的类型比如long型数组均不能正常转换。  最好不用这个类。 |

 

**2.3.3****.**  **类图**

以上类构成了如下的类结构图：

 

 

**2.3.4****.**  **小结**

通过阅读数组转换器的代码，发现代码存在以下问题：

\1.    代码冗余：代码不够简洁，每个类中都或多或少的存在代码复制粘贴的痕迹。

\2.    部分类的类型转换时存在缺陷，不能正常转换。

 

**2.4.**  **普通转换器**

**2.4.1****.**  **概述**

普通转换器提供了将字符串转换成Java中的数字、时间日期类型和其他类型对象的方法。

普通转换器都直接从Converter接口集成，实现其中的抽象方法。

用户不但可以直接使用这些工具方法，也可以自己实现一些特殊业务需求的转换器，只要实现Converter接口即可。

**2.4.2****.**  **类说明**

普通转换器的类说明如下：

 

| **类名**              | **描述**                                                     |
| --------------------- | ------------------------------------------------------------ |
| BigDecimalConverter   | 将字符串转换成BigDecimal类型数据。转换失败时可以抛出异常，也可以返回默认值。 |
| BigIntegerConverter   | 将数组转换成java.math.BigInteger类型对象，如果转换失败，可以抛出异常，也可以直接返回默认值。 |
| BooleanConverter      | 将字符串转换成boolean类型对象。 如果转换失败，可以抛出异常，也可以返回默认值。 |
| ByteConverter         | 将字符串转换成byte类型，如果转换失败，抛出异常或返回默认值。 |
| CharacterConverter    | 将字符串转换成char，如果转换失败，抛出异常或返回默认值。     |
| ClassConverter        | 从当前上下文的ClassLoader中加载类，如果类不存在，可以抛出异常，也可以直接返回默认值。 |
| DoubleConverter       | 将输入字符串转换成double类型。如果转换失败，可以抛出异常，也可以返回默认值。 |
| FileConverter         | 根据输入字符串初始化File对象，如果对象创建失败，抛出异常或返回默认值。 |
| FloatConverter        | 将字符串转换成Float类型，如果转换失败，可以抛出异常，可以返回默认值。 |
| IntegerConverter      | 将字符串转换成Integer类型对象，如果转换失败，可以抛出异常，也可以返回默认值。 |
| LongConverter         | 将字符串转换成Long类型对象，如果转换失败，可以抛出异常，也可以返回默认值。 |
| ShortConverter        | 将字符串转换成short类型对象，如果转换失败，可以抛出异常，也可以返回默认值。 |
| SqlTimeConverter      | 将字符串转换成java.sql.Time对象，如果转换失败，可以抛出异常，也可以返回默认值。 |
| SqlTimestampConverter | 将字符串转换成javax.sql.Timestamp对象，如果转换失败，可以抛出异常，也可以返回默认值。 |
| StringConverter       | 将字符串对象转换成字符串对象。  单独使用没有什么意义，但是在面向接口编程中实现了一种通用的转换方法，比较有用。 |
| URLConverter          | 将字符串转换成URL对象，如果转换失败，抛出异常，或者直接返回默认值。 |
|                       |                                                              |

 

 

**2.4.3.**  **类图**

通用转换器的类结构图如下：

 

 

**2.4.4.**  **小结**

通用转换器存在的问题是：

对于默认值的校验不到位，没有针对具体Converter的类型进行校验，一旦转换失败，直接返回默认值后可能导致后续代码出现ClassCastException，没有从源头杜绝这种情况发生，虽然这样设计更加灵活，但弊大于利，最好是在类创建时进行校验。

 

**2.5.**  **地区敏感转换器**

**2.5.1.**  **概述**

地区敏感转换器都被封装在org.apache.commons.beanutils.locale和org.apache.commons.beanutils.locale.converters包中，前者提供一个集成自Converter的通用接口，后者提供这个接口的具体实现。

地区敏感转换器主要实现了当需要分地区进行转换时，需要进行的操作。主要功能是将带有不同地区特征的字符串转换成数字和时间日期类型对象。

**2.5.2.**  **类说明**

地区敏感转换器的类说明如下：

 

| **类名**                    | **描述**                                                     |
| --------------------------- | ------------------------------------------------------------ |
| LocaleConverter             | 进行地区敏感的数据类型的转换                                 |
| BaseLocaleConverter         | 封装所有地区敏感conveter的公共方法                           |
| DateLocaleConverter         | 将地区敏感对象转换成java.util.Date对象。                     |
| SqlDateLocaleConverter      | 将输入对象转换成java.sql.Date 对象                           |
| SqlTimeLocaleConverter      | 将输入对象转换成java.sql.Time对象                            |
| SqlTimestampLocaleConverter | 将输入对象转换成java.sql.Timestamp的格式                     |
| DecimalLocaleConverter      | 将地区敏感的输入转换成java.lang.Decimal对象                  |
| BigDecimalLocaleConverter   | 将输入的地区敏感字符串转换成java.math.BigDecimal对象。  没有重写任何方法，应该只是为了和其他实现样式一致编写的方法。 |
| BigIntegerLocaleConverter   | 将输入的地区敏感的对象转换成java.math.BigInteger 对象  没有重写任何方法，应该只是为了和其他实现样式一致编写的方法。 |
| ByteLocaleConverter         | 将输入的地区敏感的字符串转换成java.lang.Byte 对象            |
| DoubleLocaleConverter       | 将地区敏感的对象转换成java.lang.Double对象                   |
| FloatLocaleConverter        | 将地区敏感的字符串转换成java.lang.Float对象                  |
| IntegerLocaleConverter      | 将地区敏感的字符串转换成java.lang.Integer对象                |
| LongLocaleConverter         | 将地区敏感的字符串转换成java.lang.Long对象                   |
| ShortLocaleConverter        | 将地区敏感的字符串转换成java.lang.Short对象                  |
| StringLocaleConverter       | 将字符串转换成数字的字符串形式。  好像没有什么实际的作用     |

 

**2.5.3.**  **类图**

地区敏感转换器的类图如下：

 

 

 

**2.5.4.**  **小结**

地区敏感转换器中所有参数参数都是直接从构造函数中输入的，没有get和set，代码冗余度很大，需要重构。

 

**2.6.**  **转换器工具类**

转换器相关的工具类是外界实际使用的接口，默认情况下，系统会向工具类中注册上述各种类型数据的转换器对象，用户可以自定义这些注册信息，添加，修改或删除自己不需要的转换器，还可以将自己实现的类型注册到转换器中。

转换器工具类的详细信息如下：

 

| **类名**               | **描述**                                                     |
| ---------------------- | ------------------------------------------------------------ |
| ConvertUtils           | 将字符串对象转换成相应类型的对象。如，将String对象转换成Integer类型的对象，或将String对象转换成Integer数组对象。  默认使用系统自定义的转换器，但接口开放，可以自定义转换器进行数据类型转换。 |
| ConvertUtilsBean       | 实际进行数据转换的类。                                       |
| LocaleConvertUtils     | 和ConvertUtils作用类似，在转换的过程中根据不同的地区进行不用的转换，适用于地区敏感的数据。 |
| LocaleConvertUtilsBean |                                                              |

 

 

**2.7.**  **转换器总结**

转换器这一套代码中实现了字符串向Java中各种数据类型的转换，这样在转换数据类型时不需要了解各种数据类型的转换API，只需要知道Converter接口即可，方便了开发人员编写代码。

但这套代码也有很多不足，其中最大的就是代码冗余的问题，小到函数内部的实现，大到整个的类结构，或多或少的都存在代码复制粘贴的影子，可以通过重构让代码变得更加清晰。

 

**3.**  **动态****bean**

**3.1.** **概述**

我们知道，每一个JavaBean对象中包含一个Class对象，这个对象是单实例的并且在当前类加载器中全局唯一,由JVM维护，用来存储Bean中的属性描述信息，这些信息在运行时无法修改。

动态bean符合JavaBean架构的基本思想，每一个DynaBean实例有一个DynaClass对象，这个对象在同类DynaBean中唯一，但可以动态的对属性进行增删改的操作。这样就弥补了原来JavaBean架构中当Bean定义后不能对Bean中属性进行扩展的缺点，同时，提供了对bean中属性进行get/set的统一工具类，这些工具类的接口可以兼容动态Bean、标准Bean，以及映射(map)。

**3.2.** **动态Bean属性**

动态Bean中的属性使用DynaProperty对象进行描述，

 

| **类名**     | **描述**                                                     |
| ------------ | ------------------------------------------------------------ |
| DynaProperty | 动态bean中的属性，由属性名，属性类型两部分组成，对于数组、链表这类复杂类型，还加入了内容类型的概念，用来描述这些复杂数据接口内部对象的类型。 |

 

**3.3.** **动态****Bean****的****Class****对象**

**3.3.1.**  **概述**

动态Bean的Class对象描述了Bean中包含的属性以及属性的数据类型，分为DynaClass和MutableDynaClass两个接口，其中MutableDynaClass接口继承自DynaClass接口，同时提供对Bean属性进行修改的方法。

详细描述如下：

 

| **接口名**       | **描述**                                                     |
| ---------------- | ------------------------------------------------------------ |
| DynaClass        | 动态类模仿java.lang.Class 的实现。使用DynaClass创建DynaBean 对象，所有DynaBean  对象共享一个DynaClass实例。 |
| MutableDynaClass | 对于DynaClass的特殊扩展，允许动态的添加和移除类的属性        |

 

**3.3.2.**  **类说明**

有多个类扩展了以上两个接口，详细信息如下：

 

| **类名**           | **描述**                                                     |
| ------------------ | ------------------------------------------------------------ |
| BasicDynaClass     | 对DynaClass接口的基本实现，提供了最基本的功能。              |
| JDBCDynaClass      | 实现JDBC逻辑的动态类                                         |
| ResultSetDynaClass | 封装java.sql.ResultSet对象，提供和其他对象一样访问方式的类。 |
| RowSetDynaClass    | 从ResultSet中读取所有数据，封装在RowSetDynaBean中。          |
| LazyDynaClass      |                                                              |
| DynaProperty       | 动态bean中的属性                                             |
| WrapDynaClass      | 封装标准JavaBean的动态bean的DynaClass对象                    |

 

**3.3.3.**  **类图**

上述类之间的关系如下：

 

 

**3.4.** **动态****Bean**

**3.4.1.**  **概述**

所有动态Bean实例都继承自DynaBean接口，可以创建DynaBean的实例，并对其属性进行修改。

接口详细信息如下：

 

| **接口名** | **描述**                                           |
| ---------- | -------------------------------------------------- |
| DynaBean   | 提供了属性类型，名称，内容可以动态修改的JavaBean。 |

 

**3.4.2.**  **类说明**

以下类实现了DynaBean接口：

 

| **类名**               | **描述**                                                  |
| ---------------------- | --------------------------------------------------------- |
| WrapDynaClass          | 封装标准JavaBean的动态bean的DynaClass对象                 |
| BasicDynaBean          | 对DynaBean接口的最小实现                                  |
| ResultSetIterator      | 封装ResultSetDynaClass的DynaBean                          |
| LazyDynaBean           | 可以动态添加属性的Bean                                    |
| WrapDynaBean           | 封装标准的JavaBean，提供DynaBean的访问方式                |
| ConvertingWrapDynaBean | WrapDynaBean的子类，在set数据时可以提供必要的数据类型转换 |

 

**3.4.3.**  **类图**

DynaBean相关类之间关系如下：

 

 

**4.**  **工具类**

**4.1.** **概述**

和任何成熟的开源包一样，commons-beanutils作为一个工具包，提供了对JavaBean以及动态Bean进行操作的工具类，即使没有使用动态Bean，仍然可以放心的使用这些工具类。

常用的工具类有以下几个部分：

l     PropertyUtils：对JavaBean中的属性值进行操作。

l     MethodUtils：使用反射的方式请求bean中的方法。

l     ConstructorUtils：使用反射的方式构造Bean的新实例。

l     BeanUtils：对JavaBean提供拷贝，赋值等操作。

l     LocaleBeanUtils：和BeanUtils功能类似，但还可以提供地区敏感数据的操作。

l     ContextClassLoaderLocal：为不同线程保存需要数据的工具类。

 

**4.2.** **详细说明**

工具类的详细说明如下：

 

| **类名**                | **描述**                                                     |
| ----------------------- | ------------------------------------------------------------ |
| ContextClassLoaderLocal | 提供保存不同线程数据的工具类，在JDK1.5中已经可以用ThreadLocal代替. |
| PropertyUtils           | 使用Java反射API编写的工具类，用于方便的进行get和set。  本工具类的所有实现都是对PropertyUtilsBean的封装和代理。 |
| PropertyUtilsBean       | 使用Java反射API进行set和get的工具类。                        |
| MethodUtils             | 封装以放射方式请求方法的工具方法                             |
| ConstructorUtils        | 使用反射方法请求构造函数创建新实例的工具类，可以简化程序中使用反射方式创建对象的代码。 |
| BeanUtilsBean           | 提供对标准JavaBean和动态bean的操作。主要功能是复制bean中的内容，拷贝bean，为bean中的内容赋值和读取bean中内容。 |
| BeanUtils               |                                                              |
| LocaleBeanUtils         | 和BeanUtils作用类似，但在执行相应方法时可以进行地区敏感数据的转换。 |
| LocaleBeanUtilsBean     |                                                              |

 

**4.3.** **PropertyUtilsBean****的方法**

通过研读commons-beanutils的源代码，整理了PropertyUtilsBean中的相关方法，如下所示：

 

| **PropertyUtilsBean的方法名** | **描述**                                                     |
| ----------------------------- | ------------------------------------------------------------ |
| copyProperties                | bean属性拷贝(copyProperties)，可以拷贝bean中所有属性，拷贝时遵循原来bean中的访问控制策略：  l      动态bean向动态bean拷贝  l      动态bean向标准bean拷贝  l      MAP向动态bean拷贝  l      Map向标准bean拷贝  l      标准bean向动态bean拷贝  l      标准bean向标准bean拷贝 |
| describe                      | 将bean属性拷贝到Map中。  只拷贝源bean中可读的属性，忽略其他属性。 |
| getIndexedProperty            | 得到bean中的索引属性值：  有两种形式，一种的参数是string，另一种的参数是属性名和位置，前者是“name[1]”的形式，后者是“name, 1”的形式。  例如，要取出bean中名为name属性的第2个对象，可以使用getIndexedProperty(bean,  “name[1]”)的形式，也可以使用getIndexedProperty(bean,  “name”, 1)的形式。  l      如果输入是动态bean，可以得到动态bean的索引属性。  如果属性是数组或列表，可以得到相应属性。 |
| getMappedProperty             | 得到bean中的映射属性值：  本方法有两种原型，可以输入(bean, “name(key)”)取出bean中名为name映射属性中以key为键的属性值；也可以输入(bean,  “name”, “key”)的方式取出bean中名为name映射属性中以key为键的属性值。 |
| getNestedProperty             | 得到bean中的嵌套属性值。获取值的bean需要有get方法，还要有public访问权限，否则BeanUtils中的类无法访问。  适合在web页面上进行bean值的读取。 |
| getPropertyDescriptor         | 得到bean中相应属性的属性描述符                               |
| getPropertyDescriptors        | 得到bean中所有属性的属性描述符                               |
| getPropertyEditorClass        | 得到bean中的属性编辑器类                                     |
| getPropertyType               | 得到bean中相应属性类型                                       |
| getReadMethod                 | 得到属性描述符中的get方法                                    |
| getSimpleProperty             | 得到bean中简单属性的值                                       |
| getWriteMethod                | 得到属性描述符中的写方法                                     |
| isReadable                    | 判断bean中的指定属性是否可读                                 |
| isWriteable                   | 判断bean中的对应方法是否可写                                 |
| setIndexedProperty            | 向bean中的索引属性赋值                                       |
| setMappedProperty             | 向bean中的映射属性赋值                                       |
| setNestedProperty             | 向bean中的内嵌属性赋值                                       |
| setProperty                   | 为bean中的属性赋值（包括简单属性和索引属性）                 |
| setSimpleProperty             | 为bean中的简单属性赋值                                       |

 

**5.**  **总结**

Commons-beanutils是一款优秀的工具类库。不但提供了一种可以动态扩展属性的JavaBean，同时封装了Java的反射机制，使用者可以更加容易的对反射进行操作，而不需要了解那么多和反射相关的知识。

 

 