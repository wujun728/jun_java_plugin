package com.zh.springbootbloomfilter.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.zh.springbootbloomfilter.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 布隆过滤器
 * 适用于大批量数据下内存紧张的情况,爬虫url重复抓取判别；垃圾邮件过滤；
 * 通过多次hash将元素映射到布隆过滤器对应的位置上,0标识该位置不存在元素,1表示该位置存在元素
 * 缺点是有误伤概率和不能删除元素
 * 为了降低误伤概率guava的bloomfilter可以通过降低误判概率来实现
 * 还可以配合白名单来避免误伤
 * @author Wujun
 * @date 2019/7/1
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    public static int size = 1000000;

    private static double fpp = 0.001;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),size,fpp);

    static {
        for (int i = 0;i < size; i++){
            bloomFilter.put(i);
        }
    }

    @Override
    public void printAccidentHit(){
        int sum = 0;
        for (int i = size + 10000;i < size + 20000; i++){
            if (bloomFilter.mightContain(i)){
                sum += 1;
                log.info("悲催的{}号老铁被误伤~~(>_<)~~",i);
            }
        }
        log.info("{}%的概率下,一共有{}个悲催的老铁被误伤~~(>_<)~~",fpp * 100,sum);
    }

    @Override
    public boolean checkExist(Integer num){
        return bloomFilter.mightContain(num);
    }

}
