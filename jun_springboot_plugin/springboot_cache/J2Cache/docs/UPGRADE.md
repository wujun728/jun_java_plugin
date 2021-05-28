J2Cache 版本更新流程

1. 完成代码开发和测试
2. 修改对应模块 pom.xml 中关于版本的定义 (version)
3. 修改 README.md 中关于 maven 部分的引用版本
4. 在 CHANGES.md 中描述改进内容
5. 发布到 maven 中央库 (mvn deploy -P release -DskipTests=true) 
6. 到 https://oss.sonatype.org/#stagingRepositories 发布新版
7. 推送代码到仓库
8. 打标签、创建 Release 发行版
9. 社区投递更新新闻