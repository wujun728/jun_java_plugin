# Maven聚合工程

### 目录树

- [Maven聚合工程](#Maven_1)
- - [1.Maven聚合工程概念](#1Maven_2)
  - [2.创建Maven聚合工程](#2Maven_6)
  - - [2.1 创建Maven父工程](#21_Maven_7)
    - [2.2 创建Module](#22_Module_14)
    - [2.3 注意](#23__21)
  - [3.Maven聚合工程依赖继承](#3Maven_30)
  - - [3.1 依赖继承](#31__31)
    - [3.2 依赖版本管理](#32__34)
  - [4.个人操作案例](#4_39)
  - - [4.1 创建Maven父工程zeromall](#41_Mavenzeromall_40)
    - [4.2 创建Maven，SpringBoot子工程](#42_MavenSpringBoot_43)
    - - [4.2.1 创建Maven子工程](#421_Maven_44)
      - [4.2.2 创建SpringBoot子工程](#422_SpringBoot_63)
      - - [注意点](#_69)
    - [4.3 依赖继承图](#43__97)
    - [总结图](#_102)
    - [4.4 Maven聚合工程依赖分析](#44_Maven_105)

# [Maven](https://so.csdn.net/so/search?q=Maven&spm=1001.2101.3001.7020)聚合工程

## 1.Maven聚合工程概念

> Maven聚合工程：就是可以在一个Maven父工程中创建`多个组件(项目)`，多个组件之间可以相互依赖，实现组件的复用。

![在这里插入图片描述](img/630a7ee727a346ea82c827ad870c9ba2.png)

## 2.创建Maven聚合工程

### 2.1 创建Maven父工程

见项目maven-parent

> Maven聚合工程的父工程packing必须为pom

- 创建一个Maven工程
- 修改父工程的pom.xml，设置打包方式为pom
  ![在这里插入图片描述](img/c2990768d3e64649918cfe252849518c.png)
- 父工程用于管理子工程，不进行业务实现，因此src目录可以选择性删除

### 2.2 创建Module

- 选择父工程—右键—New—Module
- 输入子工程名称（g和v都从父工程继承）
- 子工程的pom文件
  ![在这里插入图片描述](img/bc5882ba5818447fa0ffa0a5232b7cc9.png)
- 父工程的pom文件
  ![在这里插入图片描述](img/5b90b03d53064abcbd85a18c6100e164.png)

### 2.3 注意

- 当创建好后，子工程里面的pom会显示自动继承parent，而父工程的pom会自动显示子工程组件；当删掉子工程后，需要`手动`到父工程里面删掉对应的子工程组件依赖。
- 当子工程`用户系统`需要引用`公共模块`，只需要在引入方的pom.xml文件中添加依赖即可。
  **1.**
  ![在这里插入图片描述](img/bf4dcdb9cf194285a4b1a183856d8de9.png)
  **2.**
  ![在这里插入图片描述](img/621be726f51143959b8863cd22381367.png)
  **3.**
  ![在这里插入图片描述](img/19e89cf5ab454487930aa7af3f7a99c6.png)

## 3.Maven聚合工程依赖继承

### 3.1 依赖继承

- 在父工程的pom.xml文件添加的依赖，会被子工程继承。
  ![在这里插入图片描述](img/e2958126960040d99179a663d9563815.png)

### 3.2 依赖版本管理

> 在父工程的pom.xml文件的dependencyManagement中添加依赖，表示定义子工程中此依赖的默认版本
> (此定义不会让子工程中添加当前依赖。子工程自己手动添加，版本就是前面默认的，可以自己修改版本)
> ![在这里插入图片描述](img/887a0ffd31064925b7ccd24384d63370.png)

## 4.个人操作案例

### 4.1 创建Maven父工程zeromall

- 在pom.xml中加入`<packaging>pom</packaging>`，说明它就是一个父工程了。
- 删除src目录，因为代码全在子工程里，这个代码目录没用。

### 4.2 创建Maven，SpringBoot子工程

#### 4.2.1 创建Maven子工程

- 在父工程基础上创建四个子工程`common、beans、mapper、service`
  ![在这里插入图片描述](img/a89194ac5a9b4097933690367a832da3.png)

> maven子工程继承maven父工程；
>
> - 会发现子工程的pom文件中会自动写明继承的父工程；
> - 而父工程pom文件中也会自动声明自己的子工程。

> 如图
> 子工程的pom文件：
> ![在这里插入图片描述](img/b4bf03349c2b41df9a2bfb4aa8d91889.png)
> 比如beans子工程，还需要在pom文件中加入`<packaging>jar</packaging>`，这样其他子模块就能用beans子模块。
> `前提`就是：其他子模块中要添加beans的依赖
> ![在这里插入图片描述](img/562d2dbe29b546e883192c7e3a4022a1.png)
> 就比如mapper层要调用beans层，这时就能调用得到。

> 父工程的pom文件：
> ![在这里插入图片描述](img/c94a11c324e34f968d99c3f2b6cf90b9.png)
> 各模块关系：只有一个root
> ![在这里插入图片描述](img/d6c4c69dc94546b59bab32cd070cbf8a.png)

#### 4.2.2 创建SpringBoot子工程

最后的`controller`层，我就用`api`来命名。
**1.**
![在这里插入图片描述](img/5a67b9fc0a01401581fff36f576241f3.png)
**2.**
![在这里插入图片描述](img/9ca13e48cf394b3f912602ca63203c76.png)

##### 注意点

> 创建springboot子工程和maven子工程最大`区别`：前者会创建一个启动类，并且需要手动改配置

- 会发现，创建的boot子工程和maven父工程都是各自的主体root，并不存在继承关系
  ![在这里插入图片描述](img/4092be521efa4864957f06d8b57e7fd0.png)
  所以需要手动配置让两者存在关系
- 1 将boot工程api的pom文件中继承的springboot依赖组`剪切`到maven父工程的pom文件中去。
  让maven父工程继承springboot依赖组（`boot的其他依赖和<build>也要从boot项目pom中剪切到maven父工程pom中`）

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.6</version>
        <relativePath/>
    </parent>

123456
```

- 2 让boot工程api的pom文件中继承maven父工程
  ![在这里插入图片描述](img/1e5372ce287c40a2ac2873b0c87f535a.png)
  `并且将将spring boot的依赖配置到父工程zeromall的pom.xml中`
  springboot项目的pom文件应该只剩下面这些
  ![在这里插入图片描述](img/ab0f9af6493a4a1d8ca6fb3064e815fc.png)
- 3 在maven父工程的pom文件中`手动`添加子模块的声明
  ![在这里插入图片描述](img/15b562cf31f342b79a598b3edbbfb5f0.png)
- 4 root主体
  ![在这里插入图片描述](img/be9afe5dcbd24903b522868c088998ba.png)
  只有一个root主体，说明都继承拿一个root父工程！

### 4.3 依赖继承图

![在这里插入图片描述](img/89e11e149b7b4941b01691f7274fbc2a.png)
**依赖关系图：模块依赖可以传递**
![在这里插入图片描述](img/ea391597c2cc44ee9a16fe1a9c8a0c51.png)
依赖关系按照第二张图配置依赖关系。

### 总结图

![在这里插入图片描述](img/eb25c7b09ebb4aba91ca0b24d29b7c7d.png)

### 4.4 Maven聚合工程依赖分析

> 如果将依赖添加到父工程的pom中，根据依赖的继承关系，所有的子工程中都会继承父工程的依赖
>
> - `好处`：当有多个子工程都需要相同的依赖时，无需在子工程中重复添加依赖
>
> - `缺点`：如果某些子工程不需要这个依赖，还是会被强行继承（即打包某一个子工程时，会进来很多无关的依赖）
>
>   如果在父工程中没有添加统一依赖，则每个子工程所需的依赖都要在对应的子工程的pom中手动添加。
>
>   `(加入两个子模块，common和beans，都加了lombok依赖，此时一个1.18.18版本，一个1.18.16版本。其实两者并不影响。但是又有一个模块，它继承了上面两个模块，那它继承那一个版本呢？那它的lombok版本就可以在父工程的pom中定义依赖版本管理器，只是版本管理作用，具体依赖还要自己添加)`

![在这里插入图片描述](img/922d4e9f30ef4fbb918ee5bf85654844.png)
`依赖配置说明`

> 1.在父工程的pom文件中一次性添加各个子工程所需的依赖
>
> 2.在各个子工程中单独添加当前子工程的依赖