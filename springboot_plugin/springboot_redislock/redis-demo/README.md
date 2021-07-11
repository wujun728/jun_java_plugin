#### redis高并发秒杀测试

> 测试项目： https://github.com/14251104246/redis-demo.git


#### 准备
- 使用`docker-compose`命令启动redis服务器（可以用其他方式启动）
- idea启动测试项目
- jmeter测试脚本
    - [高并发秒杀-重现超卖问题.jmx](https://github.com/14251104246/redis-demo/blob/master/src/test/jmeter/%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A7%92%E6%9D%80-%E9%87%8D%E7%8E%B0%E8%B6%85%E5%8D%96%E9%97%AE%E9%A2%98.jmx)
    - [高并发秒杀-有事务方式减少库存.jmx](https://github.com/14251104246/redis-demo/blob/master/src/test/jmeter/%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A7%92%E6%9D%80-%E6%9C%89%E4%BA%8B%E5%8A%A1%E6%96%B9%E5%BC%8F%E5%87%8F%E5%B0%91%E5%BA%93%E5%AD%98.jmx)
#### 重现秒杀时出现的超卖问题
- 核心测试代码如下：
```java
/**
 * 用于测试redis秒杀
 */
@RestController
@RequestMapping("/api/spike")
@Slf4j
public class SpikeController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    
    //记录实际卖出的商品数量
    private AtomicInteger successNum = new AtomicInteger(0);

    @RequestMapping(value = "/initSku", method = RequestMethod.GET)
    public String initSku() {
        //初始化库存数量
        stringRedisTemplate.opsForValue().set("product_sku", "5");
        //初始化实际卖出的商品数量0
        successNum.set(0);
        return "初始化库存成功";
    }

    /**
     * 会出现超卖情况的减少库存方式
     * @return
     */
    @RequestMapping(value = "/reduceSku", method = RequestMethod.GET)
    public String reduceSku() {
        Integer sku = Integer.parseInt(stringRedisTemplate.opsForValue().get("product_sku"));
        sku = sku - 1;
        if (sku < 0) {
            return "库存不足";
        }

        stringRedisTemplate.opsForValue().set("product_sku", sku.toString());
        //记录实际卖出的商品数量
        return "减少库存成功,共减少" + successNum.incrementAndGet();
    }

    @RequestMapping(value = "/successNum", method = RequestMethod.GET)
    public String successNum() {
        return "顾客成功抢到的商品数量：" + successNum.get();
    }
}
```

- 测试api：

```
API{初始化库存数量} >> http://127.0.0.1:8090/api/spike/initSku
API{减少库存数量} >> http://127.0.0.1:8090/api/spike/reduceSku
API{查看共减少库存数量} >> http://127.0.0.1:8090/api/spike/successNum
```

- 第一个api用于：初始化库存中的商品数量为5
- 第二个api用于：减少库存1个商品（即客户购买一个商品）
- 第三个api用于：查看用户实际购买的商品


- 少量用户请求的情况展示：
    - 首先初始商品库存：http://127.0.0.1:8090/api/spike/initSku
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-569fb412ed3204c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - redis数据库中商品库存记录，结果为5
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-3c373cf1058a0da6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    - 查看用户实际购买的商品，结果为0
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-a2019253556b9b5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 客户购买5次商品（调用5次`减少库存数量`api）,下面只列出3个图
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-5e210855a352cdb8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-3c27d4abc48392c4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-cce7dad4d0f446f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 客户继续购买（继续调用`减少库存数量`api）时，会提示库存不足
    
    - 再次查看redis数据库中商品库存记录，结果为0
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-c2c4f000761a989e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 再次查看用户实际购买的商品，结果为5
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-6afe5f8e1efeef59.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 大量用户请求的情况（高并发秒杀）展示
    - 首先初始商品库存：http://127.0.0.1:8090/api/spike/initSku
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-569fb412ed3204c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    - redis数据库中商品库存记录，结果为5
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-3c373cf1058a0da6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    - 查看用户实际购买的商品，结果为0
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-a2019253556b9b5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 使用jmeter打开测试脚本，可以看到基本配置如下
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-6e396d593cf087c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - jmeter并发配置如下（当用户数达到 1000 的时候才开始测试）
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-f986edb5bb69f971.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 点击jmeter的`start`按钮，开始1000个并发请求
    
    - 再次查看redis数据库中商品库存记录，结果为0
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-c2c4f000761a989e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - **注意：再次查看用户实际购买的商品，结果超过5，出现超卖情况！！！**
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-912903c714c61d78.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 超卖问题原因分析
- 从上面测试结果，我们知道，高并发请求`http://127.0.0.1:8090/api/spike/reduceSku`，会出现超卖的情况
- 下面我们看下超卖问题的原因

```
/**
 * 会出现超卖情况的减少库存方式
 * @return
 */
@RequestMapping(value = "/reduceSku", method = RequestMethod.GET)
public String reduceSku() {
    Integer sku = Integer.parseInt(stringRedisTemplate.opsForValue().get("product_sku"));
    sku = sku - 1;
    if (sku < 0) {
        return "库存不足";
    }

    stringRedisTemplate.opsForValue().set("product_sku", sku.toString());
    //记录实际卖出的商品数量
    return "减少库存成功,共减少" + successNum.incrementAndGet();
}
```

- 从代码片可以看出，问题原因是库存数量`sku`的读和写操作不在同一个原子操作上，导致类似`不可重复读`的现象。可以类比多线程的问题。

#### 通过redis事务解决超卖问题
##### 使用redis原生的sdk
- 如下改造`reduceSku()`方法，作为一个新接口`http://127.0.0.1:8090/api/spike/reduceSku3`

```
    /**
     * 加入事务的减少库存方式
     * @return
     */
    @RequestMapping(value = "/reduceSku3", method = RequestMethod.GET)
    public String reduceSku3() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        List<Object> result ;
        Transaction transaction = null;
        try {
            jedis.watch("product_sku");
            int sku = Integer.parseInt(jedis.get("product_sku"));
            if (sku > 0) {
                transaction = jedis.multi();
                transaction.set("product_sku", String.valueOf(sku - 1));
//                int exp = 1/0;
                result = transaction.exec();
                if (result == null || result.isEmpty()) {
                    System.out.println("Transaction error...");// 可能是watch-key被外部修改，或者是数据操作被驳回
//                    transaction.discard();  //watch-key被外部修改时，discard操作会被自动触发
                    return "Transaction error...";
                }
            } else {
                return "库存不足";
            }
            return "减少库存成功,共减少" + successNum.incrementAndGet();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.discard();
            return "fail";
        }
    }
```

- 大量用户请求`reduceSku3`接口的情况（高并发秒杀）展示
    - 首先初始商品库存：http://127.0.0.1:8090/api/spike/initSku
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-569fb412ed3204c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - redis数据库中商品库存记录，结果为5
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-3c373cf1058a0da6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 查看用户实际购买的商品，结果为0
    
    >![image.png](https://upload-images.jianshu.io/upload_images/7176877-a2019253556b9b5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 使用jmeter打开测试脚本，可以看到基本配置如下
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-6aa46b4f50985874.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - jmeter并发配置如下（当用户数达到 1000 的时候才开始测试）
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-f986edb5bb69f971.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - 点击jmeter的`start`按钮，开始1000个并发请求
    
    - 再次查看redis数据库中商品库存记录，结果为0
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-c2c4f000761a989e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
    - **注意：再次查看用户实际购买的商品，结果为5，超卖情况消失**
    
    > ![image.png](https://upload-images.jianshu.io/upload_images/7176877-6afe5f8e1efeef59.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 上面是直接用redis原生的sdk对象`jredis`执行的事务

##### spring的`redisTemplate`执行事务

- 注意： 若要使用spring的`redisTemplate`执行事务，需要在开启事务后执行一个redis的查询操作（但不能使用查询到的值）。原因有两点：
    - spring对redis事务的`exec()`方法返回结果做了处理（把返回值的 `OK`结果删掉）。
        - 导致在事务中只有`set`等更新操作时，事务执行失败与成功返回的结果一样
    - 事务过程中查询redis的值只会在事务执行成功后才放回。而在事务执行过程中只会返回`null`
- 接口`http://127.0.0.1:8090/api/spike/reduceSku3`是使用spring的`redisTemplate`执行事务的例子。代码如下
```
@RequestMapping(value = "/reduceSku2", method = RequestMethod.GET)
public String reduceSku2() {
    stringRedisTemplate.setEnableTransactionSupport(true);
    List<Object> results = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
        @Override
        public List<Object> execute(RedisOperations operations) throws DataAccessException {
            operations.watch("product_sku");
            String product_sku = (String) operations.opsForValue().get("product_sku");
            operations.multi();
            operations.opsForValue().get("product_sku");//必要的空查询
            Integer sku = Integer.parseInt(product_sku);
            sku = sku - 1;
            if (sku < 0) {
                return null;
            }
            operations.opsForValue().set("product_sku", sku.toString());
            return operations.exec();
//                operations.unwatch(); //执行exec()后自动unwatch()

        }
    });

    if (results != null && results.size() > 0) {
        return "减少库存成功,共减少" + successNum.incrementAndGet();
    }

    return "库存不足";
//        return result.toString();
}
```
- 测试结果为：成功解决超卖问题
- 不再另外贴图片出来
##### spring的`redisTemplate`执行事务（使用`zset`）
- 接口`http://127.0.0.1:8090/api/set/reduceSku`是使用`zset`的方式
```
@RequestMapping(value = "/reduceSku", method = RequestMethod.GET)
public String reduceSku5(String pid) {
    pid = pid==null? String.valueOf(1) :pid;
    String finalPid = pid;
    List<Object> results = redisTemplate.execute(new SessionCallback<List<Object>>() {
        @Override
        public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
            String key = "product";
            redisOperations.watch(key);
            ZSetOperations<String, String> kvzSetOperations = redisOperations.opsForZSet();
            Object score = kvzSetOperations.score(key, finalPid);
            redisOperations.multi();
            if (score != null && Double.valueOf(score.toString()) > 0) {
                kvzSetOperations.incrementScore("product", finalPid, -1);
            }
            return redisOperations.exec();

        }
    });

    if (results != null && results.size() > 0) {
        return "减少库存成功,共减少" + successNum.incrementAndGet();
    }

    return "库存不足";
}
```
- 测试结果为：成功解决超卖问题
- 不再另外贴图片出来
#### 通过加锁方式解决超卖问题
- 如下改造`reduceSku()`方法，作为一个新接口`http://127.0.0.1:8090/api/spike/reduceSku4`

```
@RequestMapping(value = "/reduceSku4", method = RequestMethod.GET)
public String reduceSku4() {
    RLock rLock = redissonClient.getLock("product_sku");
    try {
        rLock.lock();

        Integer sku = Integer.parseInt(stringRedisTemplate.opsForValue().get("product_sku"));
        sku = sku - 1;
        if (sku < 0) {
            return "库存不足";
        }

        stringRedisTemplate.opsForValue().set("product_sku", sku.toString());

        return "减少库存成功,共减少" + successNum.incrementAndGet();
    } finally {
        rLock.unlock();
    }

}
```
- 测试结果为：成功解决超卖问题
- 不再另外贴图片出来