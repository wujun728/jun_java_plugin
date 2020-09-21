基于SpringMVC + spring-data-redis,可简单实现对象的CRUD操作,具体实现可查看app.common.RedisDao.java

```

app.common.RedisDao.save(Entity) // 添加对象

app.common.RedisDao.update(Entity) // 修改对象

app.common.RedisDao.get(Object) // 获取对象

app.common.RedisDao.getTotal(String) // 获取总记录数

app.common.RedisDao.find(String, int, int) // 获取记录集合

app.common.RedisDao.del(Entity) // 删除对象

```

---



前提:
> - 有redis基础
> - 事先安装好redis

---

部署:

> - 下载项目,进入项目根目录,运行mvn eclipse:eclipse,然后导入到eclipse中;
> - 修改配置config.properties;
> - 找到src/test/java/app/下两个测试文件自行体验.

