package com.xxl.apm.admin.test.registry;

import com.xxl.registry.client.XxlRegistryBaseClient;
import com.xxl.registry.client.XxlRegistryClient;
import com.xxl.registry.client.model.XxlRegistryDataParamVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxueli 2018-12-28 11:20:53
 */
public class RegistryTest {

    public static void main(String[] args) throws InterruptedException {
        test2();
    }


    public static void test1() throws InterruptedException {
        XxlRegistryBaseClient registryClient = new XxlRegistryBaseClient("http://localhost:8080/xxl-apm-admin/", null, "xxl-apm", "default");

        // registry test
        List<XxlRegistryDataParamVO> registryDataList = new ArrayList<>();
        registryDataList.add(new XxlRegistryDataParamVO("service01", "address01"));
        registryDataList.add(new XxlRegistryDataParamVO("service02", "address02"));
        System.out.println("registry:" + registryClient.registry(registryDataList));
        TimeUnit.SECONDS.sleep(2);

        // discovery test
        Set<String> keys = new TreeSet<>();
        keys.add("service01");
        keys.add("service02");
        System.out.println("discovery:" + registryClient.discovery(keys));


        // remove test
        System.out.println("remove:" + registryClient.remove(registryDataList));
        TimeUnit.SECONDS.sleep(2);

        // discovery test
        System.out.println("discovery:" + registryClient.discovery(keys));

        // monitor test
        TimeUnit.SECONDS.sleep(10);
        System.out.println("monitor...");
        registryClient.monitor(keys);
    }

    public static void test2() throws InterruptedException {
        XxlRegistryClient registryClient = new XxlRegistryClient("http://localhost:8080/xxl-apm-admin/", null, "xxl-apm", "default");

        // registry test
        List<XxlRegistryDataParamVO> registryDataList = new ArrayList<>();
        registryDataList.add(new XxlRegistryDataParamVO("service01", "address01"));
        registryDataList.add(new XxlRegistryDataParamVO("service02", "address02"));
        System.out.println("registry:" + registryClient.registry(registryDataList));
        TimeUnit.SECONDS.sleep(2);

        // discovery test
        Set<String> keys = new TreeSet<>();
        keys.add("service01");
        keys.add("service02");
        System.out.println("discovery:" + registryClient.discovery(keys));

        while (true) {
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
