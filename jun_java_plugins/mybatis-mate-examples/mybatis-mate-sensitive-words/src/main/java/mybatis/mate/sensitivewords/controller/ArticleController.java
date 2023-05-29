package mybatis.mate.sensitivewords.controller;

import mybatis.mate.params.SensitiveWordsProcessor;
import mybatis.mate.sensitivewords.config.ParamsConfig;
import mybatis.mate.sensitivewords.entity.Article;
import mybatis.mate.sensitivewords.entity.ArticleNoneSensitive;
import mybatis.mate.sensitivewords.entity.SensitiveWords;
import mybatis.mate.sensitivewords.mapper.SensitiveWordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示文章敏感词过滤
 */
@RestController
public class ArticleController {
    @Autowired
    private SensitiveWordsMapper sensitiveWordsMapper;

    // 测试访问下面地址观察请求地址、界面返回数据及控制台（ 普通参数 ）
    // 无敏感词 http://localhost:8080/info?content=tom&see=1&age=18
    // 英文敏感词 http://localhost:8080/info?content=my%20content%20is%20tomcat&see=1&age=18
    // 汉字敏感词 http://localhost:8080/info?content=%E7%8E%8B%E5%AE%89%E7%9F%B3%E5%94%90%E5%AE%8B%E5%85%AB%E5%A4%A7%E5%AE%B6&see=1
    // 多个敏感词 http://localhost:8080/info?content=%E7%8E%8B%E5%AE%89%E7%9F%B3%E6%9C%89%E4%B8%80%E5%8F%AA%E7%8C%ABtomcat%E6%B1%A4%E5%A7%86%E5%87%AF%E7%89%B9&see=1&size=6
    // 插入一个字变成非敏感词 http://localhost:8080/info?content=%E7%8E%8B%E7%8C%AB%E5%AE%89%E7%9F%B3%E6%9C%89%E4%B8%80%E5%8F%AA%E7%8C%ABtomcat%E6%B1%A4%E5%A7%86%E5%87%AF%E7%89%B9&see=1&size=6
    @GetMapping("/info")
    public String info(Article article) throws Exception {
        return ParamsConfig.toJson(article);
    }


    // 添加一个敏感词然后再去观察是否生效 http://localhost:8080/add
    // 观察【猫】这个词被过滤了 http://localhost:8080/add?content=%E7%8E%8B%E5%AE%89%E7%9F%B3%E6%9C%89%E4%B8%80%E5%8F%AA%E7%8C%ABtomcat%E6%B1%A4%E5%A7%86%E5%87%AF%E7%89%B9&see=1&size=6
    // 嵌套敏感词处理 http://localhost:8080/info?content=%E7%8E%8B%E7%8C%AB%E5%AE%89%E7%9F%B3%E6%9C%89%E4%B8%80%E5%8F%AA%E7%8C%ABtomcat%E6%B1%A4%E5%A7%86%E5%87%AF%E7%89%B9&see=1&size=6
    // 多层嵌套敏感词 http://localhost:8080/info?content=%E7%8E%8B%E7%8E%8B%E7%8C%AB%E5%AE%89%E7%9F%B3%E5%AE%89%E7%9F%B3%E6%9C%89%E4%B8%80%E5%8F%AA%E7%8C%ABtomcat%E6%B1%A4%E5%A7%86%E5%87%AF%E7%89%B9&see=1&size=6
    @GetMapping("/add")
    public String add() throws Exception {
        Long id = 3L;
        if (null == sensitiveWordsMapper.selectById(id)) {
            System.err.println("插入一个敏感词：" + sensitiveWordsMapper.insert(new SensitiveWords(id, "猫")));
            // 插入一个敏感词，刷新算法引擎敏感词
            SensitiveWordsProcessor.reloadSensitiveWords();
        }
        return "ok";
    }

    // 测试访问下面地址观察控制台（ 请求json参数 ）
    // idea 执行 resources 目录 TestJson.http 文件测试
    @PostMapping("/json")
    public String json(@RequestBody Article article) throws Exception {
        return ParamsConfig.toJson(article);
    }

    // 这里未实现 Sensitived 接口 SensitiveRequestBodyAdvice 不调用脱敏
    @PostMapping("/test")
    public String test(@RequestBody ArticleNoneSensitive article) throws Exception {
        return ParamsConfig.toJson(article);
    }
}
