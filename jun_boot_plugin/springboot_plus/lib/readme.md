oracle 先本地安装驱动,进入当前目录


mvn install:install-file -Dfile=ojdbc6-11.2.0.1.0.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar



<dependency
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc6</artifactId>
    <version>11.2.0</version>
</dependency>

