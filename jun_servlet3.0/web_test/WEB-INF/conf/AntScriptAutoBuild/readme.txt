更新：优化原jar任务为base + jar任务，base及jar任务默认选中(base任务为必选)；2013-9-26



Version: 1.1   2013-4-4


程序作用: 
          传入Eclipse的Java Project、Dynamic Web Project或者MyEclipse的Web Project绝对路径地址，
          在此路径下自动生成Ant构建属性文件build.properties及构建脚本build.xml。



独立版本(antScriptAutoBuild-1.1.zip)启动说明: java -jar antScriptAutoBuild-1.1.jar 参数1 参数2
1，只接受传入两个参数：
        第一个是Eclipse的Java Project、Dynamic Web Project或者MyEclipse的Web Project绝对路径地址，
        第二个是Ant Task任务名称串(以半角逗号分隔)，任务名称即目录conf/AntScriptAutoBuild/script/下的文件名称，可以额外增加更多Ant构建脚本片段文件。



其他说明:
1，自动生成的Ant构建属性文件build.properties从文件夹conf/AntScriptAutoBuild/properties/中的属性文件拼接而成，文件编码UTF-8，
        根据项目属性替换掉以下六个属性的值: project.name、project.compile.source、project.compile.target、dir.web、dir.src、dir.classes，其他属性及值原样不变；
2，自动生成的Ant构建脚本build.xml从文件夹conf/AntScriptAutoBuild/script/中的任务文件拼接而成，文件编码UTF-8；
3，Ant Task任务对应conf/AntScriptAutoBuild/script/下的文件名称，增加一个文件即增加一个Ant构建脚本，而../../properties/文件夹下并不要求有对应属性文件。
        



从Project的配置文件中读取相关属性：Eclipse的Java Project、Dynamic Web Project、MyEclipse的Web Project
1，解析文件'.project'以获取Project Name；
2，解析文件'.settings/org.eclipse.jdt.core.prefs'以获取Project Compile Source以及Project Compile Target；
        如果没有'.settings/org.eclipse.jdt.core.prefs'文件，则解析文件'.settings/org.eclipse.wst.common.project.facet.core.xml'以获取"installed java facet version"；
3，解析文件'.classpath'以获取Java Sources Path以及Compile Output Path；
4，解析文件'.settings/.jsdtscope'以获取Webapp Path。
其中Java Project没有第4个文件'.settings/.jsdtscope'解析以获取Webapp Path。

