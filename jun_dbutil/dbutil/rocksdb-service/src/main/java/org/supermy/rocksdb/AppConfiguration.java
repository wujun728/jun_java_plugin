package org.supermy.rocksdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.http.TypedData;
import ratpack.spring.config.EnableRatpack;
import ratpack.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Service {
    String message();
}

@SpringBootApplication
@EnableConfigurationProperties
//@EnableWebMvc
@EnableRatpack
public class AppConfiguration { //extends WebMvcConfigurerAdapter  {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AppConfiguration.class);

    @Resource
    private Environment env;

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:/index.html");
//	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**").addResourceLocations("/");
//	}

    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class, args);
    }

    @Bean
    Gson gson() {
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().setVersion(1.0).create();
    }


    /**
     * if (db != null) db.close();
     * options.dispose();
     *
     * @return
     */
    @Bean
    public RocksDB RocksDbConfig() {
        String filename = env.getRequiredProperty("db.file");


        log.debug("rocks db path:", filename);

        RocksDB.loadLibrary();
        // the Options class contains a set of configurable DB options
        // that determines the behavior of a database.
        Options options = new Options();
        try {
            options.setCreateIfMissing(true)
                    .createStatistics()
                    .setWriteBufferSize(8 * SizeUnit.KB)
                    .setMaxWriteBufferNumber(3)
                    .setMaxBackgroundCompactions(10)
                    .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                    .setCompactionStyle(CompactionStyle.UNIVERSAL);
        } catch (final IllegalArgumentException e) {
            assert (false);
        }

        final Filter bloomFilter = new BloomFilter(10);
        final ReadOptions readOptions = new ReadOptions()
                .setFillCache(false);
        final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10);

        options.setMemTableConfig(
                new HashSkipListMemTableConfig()
                        .setHeight(4)
                        .setBranchingFactor(4)
                        .setBucketCount(2000000));

        options.setMemTableConfig(
                new HashLinkedListMemTableConfig()
                        .setBucketCount(100000));
        options.setMemTableConfig(
                new VectorMemTableConfig().setReservedSize(10000));

        options.setMemTableConfig(new SkipListMemTableConfig());

        options.setTableFormatConfig(new PlainTableConfig());
        // Plain-Table requires mmap read
        options.setAllowMmapReads(true);

        options.setRateLimiter(rateLimiter);

        final BlockBasedTableConfig table_options = new BlockBasedTableConfig();
        table_options.setBlockCacheSize(64 * SizeUnit.KB)
                .setFilter(bloomFilter)
                .setCacheNumShardBits(6)
                .setBlockSizeDeviation(5)
                .setBlockRestartInterval(10)
                .setCacheIndexAndFilterBlocks(true)
                .setHashIndexAllowCollision(false)
                .setBlockCacheCompressedSize(64 * SizeUnit.KB)
                .setBlockCacheCompressedNumShardBits(10);

        options.setTableFormatConfig(table_options);
        //options.setCompressionType(CompressionType.SNAPPY_COMPRESSION).setCreateIfMissing(true);

        RocksDB db = null;
        try {
            // a factory method that returns a RocksDB instance
            //String filename = "/Users/moyong/project/env-myopensource/1-spring/12-spring/rocksdb-service/src/main/resources/data";
            //db = factory.open(new File("example"), options);

            db = RocksDB.open(options, filename);
            // do something
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return db;
    }

//
//	@Bean
//	public Action<Chain> index() {
//		return chain -> chain
//				.get(ctx -> ctx
//						.render(greeting("Alex"))
//				);
//	}
//
//	public String greeting(String name) {
//		return String.format("Hello, %s", name);
//	}

    /**
     * http://127.0.0.1:9008/
     *
     * @return
     */
    @Bean
    public Action<Chain> home() {
        return chain -> chain
                .get(ctx -> ctx
                        .render("Hello " + service().message())
                );
    }


    @Bean
    public Service service() {
        return () -> "World!";
    }

    /**
     * http://127.0.0.1:9008/api
     *
     * @return
     */
    @Bean
    public Action<Chain> api() {
        return chain -> chain
                .prefix("apis", pchain -> pchain
                        .all(ctx -> ctx
                                .byMethod(method -> method
                                        .get(() -> ctx.render("Received GET request"))
                                        .post(() -> ctx.render("Received POST request"))
                                        .put(() -> ctx.render("Received PUT request"))
                                        .delete(() -> ctx.render("Received DELETE request"))
                                )
                        )
                );

    }

    @Bean
    public Action<Chain> apiusers() {
        return chain -> chain
                .prefix("api/users", pchain -> pchain
                        .prefix(":username", uchain -> uchain
                                .all(ctx -> {
                                    String username = ctx.getPathTokens().get("username");
                                    ctx.byMethod(method -> method
                                            .get(() -> ctx.render("Received request for user: " + username))
                                            .put(() -> {
                                                Promise<TypedData> body = ctx.getRequest().getBody();
                                                body.map(typedData -> typedData.getText()).
                                                        then(json -> ctx.render("Received request to create a new user with JSON: " + json));
                                            })
                                            .delete(() -> ctx.render("Received delete request for user: " + username))
                                    );
                                })
                        )
                        .all(ctx -> ctx
                                .byMethod(method -> method
                                        .post(() -> {
                                            Promise<TypedData> body = ctx.getRequest().getBody();

                                            body.map(typedData -> typedData.getText()).
                                                    then(json -> ctx.render("Received request to create a new user with JSON: " + json));

                                            //ctx.render("Received request to create a new user with JSON: " + json);
                                        })
                                        .get(() -> ctx.render("Received request to list all users"))  //http://127.0.0.1:9008/api/users

                                ))
                );


    }

    /**
     * key/value
     *
     * @param mydb
     * @param gson
     * @return
     */
    @Bean
    public Action<Chain> apimydb(RocksDB mydb, Gson gson) {
        return chain -> chain
                .prefix("api/mydb", pchain -> pchain
                                .prefix(":key", uchain -> uchain
                                        .all(ctx -> {
                                            String key = ctx.getPathTokens().get("key");
                                            ctx.byMethod(method -> method
                                                    //.get(() -> {ctx.render("Received request for user: " + key)})
                                                    .get(() -> {  //http://127.0.0.1:9008/api/mydb/ab ab=key
                                                        byte[] value = mydb.get(key.getBytes());
                                                        //ctx.render(gson.toJson(toJson(true,"数据获取成功",toData(key,value))));
                                                        ctx.render(gson.toJson(new CommonJson<Map>(true, "获取数据成功!", toJsonData(key, value, gson))));
                                                    })
                                                    .put(() -> {  //http://127.0.0.1:9008/api/mydb/ab ab=key body =value
                                                        Promise<TypedData> body = ctx.getRequest().getBody();
                                                        body.map(typedData -> typedData.getText()).
                                                                then(json -> {
                                                                    //Map map=gson.fromJson(json,HashMap.class);
                                                                    mydb.put(key.getBytes(), json.getBytes());
                                                                    ctx.render(gson.toJson(new CommonJson<Map>(true, "设置数据成功!", toJsonData(key, json.getBytes(), gson))));
                                                                });
                                                    })
                                                    .delete(() -> {

                                                        mydb.delete(key.getBytes());
                                                        ctx.render(gson.toJson(new CommonJson<Map>(true, "删除数据成功!", toJsonData(key, "".getBytes(), gson))));

                                                    })
                                            );
                                        })
                                )
                                .all(ctx -> ctx
                                        .byMethod(method -> method
                                                        .delete(() -> {
                                                            MultiValueMap<String, String> queryParams = ctx.getRequest().getQueryParams();
                                                            List<String> pre = queryParams.getAll("pre");
                                                            String prefix = pre == null ? "" : pre.get(0);

                                                            log.debug("pre {} {}", prefix, "依据前缀删除");

                                                            if (!prefix.equals("")) {
                                                                final List<byte[]> keys = new ArrayList<>();
                                                                try (final RocksIterator iterator = mydb.newIterator()) {
                                                                    //iterator.seek("a".getBytes());  iterator.seekToLast()
                                                                    for (iterator.seek(prefix.getBytes()); iterator.isValid() && new String(iterator.key()).startsWith(prefix); iterator.next()) {

                                                                        keys.add(iterator.key());
                                                                        if (keys.size() >= 10000) {
                                                                            //数据大于1万条直接退出
                                                                            break;
                                                                        }
                                                                    }
                                                                }

                                                                //批量删除
                                                                try (final WriteOptions writeOpt = new WriteOptions()) {
                                                                    try (final WriteBatch batch = new WriteBatch()) {
                                                                        for (byte[] key1 : keys) {
                                                                            batch.remove(key1);
                                                                        }
                                                                        mydb.write(writeOpt, batch);
                                                                    }
                                                                }

                                                                ctx.render(gson.toJson(new CommonJson<String>(true, "删除数据成功!(最多删除1万条)", prefix)));

                                                            } else {

                                                                ctx.render(gson.toJson(new CommonJson<Map>(true, "删除数据失败!", toJsonData("pre", "不能为空".getBytes(), gson))));
                                                            }
                                                        })

                                                        .post(() -> {  //

                                                            Promise<TypedData> body = ctx.getRequest().getBody();

                                                            body.map(typedData -> typedData.getText()).
                                                                    then(json -> {
                                                                        log.debug("pre {} {}", json, "批量数据更新");

                                                                        Map<String, Object> map = gson.fromJson(json, HashMap.class);
//														for (String key:map.keySet()) {
//															mydb.put(key.getBytes(),gson.toJson(map.get(key)).getBytes());
//														}
                                                                        // write batch test
                                                                        try (final WriteOptions writeOpt = new WriteOptions()) {
                                                                            try (final WriteBatch batch = new WriteBatch()) {
                                                                                for (String key : map.keySet()) {
                                                                                    batch.put(key.getBytes(), gson.toJson(map.get(key)).getBytes());
                                                                                }
                                                                                mydb.write(writeOpt, batch);
                                                                            }
                                                                        }
                                                                        ctx.render(gson.toJson(new CommonJson<String>(true, "设置数据成功!", json)));
                                                                    });

                                                        })
                                                        .get(() -> {
                                                            MultiValueMap<String, String> queryParams = ctx.getRequest().getQueryParams();
                                                            List<String> pre = queryParams.getAll("pre");
                                                            String prefix = pre == null ? "" : pre.get(0);

                                                            log.debug("pre {} {}", prefix, "批量数据获取");

                                                            final List<byte[]> keys = new ArrayList<>();
                                                            try (final RocksIterator iterator = mydb.newIterator()) {
                                                                //iterator.seek("a".getBytes());  iterator.seekToLast()
                                                                for (iterator.seek(prefix.getBytes()); iterator.isValid() && new String(iterator.key()).startsWith(prefix); iterator.next()) {

                                                                    keys.add(iterator.key());
                                                                    if (keys.size() >= 10000) {
                                                                        //数据大于1万条直接退出
                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                            Map<byte[], byte[]> values = mydb.multiGet(keys);
                                                            //assert (values.size() == keys.size());
                                                            Map obj = new HashMap();
                                                            for (byte[] value : values.keySet()) {
                                                                String v = new String(values.get(value));
                                                                if (v.startsWith("{") && v.endsWith("}")) {
                                                                    log.debug(v);
                                                                    obj.put(new String(value), gson.fromJson(new String(values.get(value)), Map.class));
                                                                } else {
                                                                    obj.put(new String(value), new String(values.get(value)));
                                                                }
                                                            }

                                                            ctx.render(gson.toJson(new CommonJson<Map>(true, "获取数据成功（最多只返回1万条数据）,前缀：" + pre + "数据量：" + obj.keySet().size(), obj)));
                                                        })

                                        ))
                );


    }

    public Map toJsonData(String key, byte[] value, Gson gson) {
        Map data1 = new HashMap();
        byte[] obj = value == null ? "".getBytes() : value;
        log.debug("{}", obj);

        log.debug(new String(obj));

        data1.put(key, gson.fromJson(new String(obj), HashMap.class));
        return data1;
    }


}




