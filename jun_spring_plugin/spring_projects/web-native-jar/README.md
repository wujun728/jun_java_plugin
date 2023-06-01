1、将jar包拷贝到项目的文件夹中，一般在lib目录下

2、在maven项目pom.xml中添加需要引入的包，重点添加scope与systemPath

        <dependency>
            <groupId>ueditor</groupId>
            <artifactId>ueditor</artifactId>
            <version>1.1.2</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/ueditor-1.1.2.jar</systemPath>
        </dependency>
        
3、将引入的包在打war包的时候复制到war中，添加一个war打包插件

    <!-- 打包war文件时，配置manifest文件，加入lib包的jar依赖 -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.6</version>
        <configuration>
            <webResources>
                <resource>
                    <directory>${project.basedir}/lib</directory>
                    <targetPath>WEB-INF/lib</targetPath>
                    <filtering>false</filtering>
                    <includes>
                        <include>**/*.jar</include>
                    </includes>
                </resource>
            </webResources>
        </configuration>
    </plugin>
    
3、这样就能在maven构造的web中使用本地的jar包了