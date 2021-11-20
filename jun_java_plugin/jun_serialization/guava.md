
        ImmutableList<String> list = ImmutableList.of("1", "2");
        List lists = Lists.newArrayList("abc","abcd","123");
        System.err.println(Joiner.on(",").join(lists));
        Map map = Maps.newHashMap();
        Set set = Sets.newHashSet("1", "2");
        ImmutableMap<String, String> map2 = ImmutableMap.of("key1", "value1", "key2", "value2");
        System.err.println(Joiner.on(",").withKeyValueSeparator("=").join(map2));
//        System.err.println(map2.get("key2"));
//        map.forEach((key,value)->{System.out.println(key+""+value);});
       
        Multimap<String, String> m = ArrayListMultimap.create();
        m.put("a", "1");
        m.put("a", "2");
        m.forEach((key,value)->{System.out.println(key+"-"+value);});
//        System.err.println(m.get("a"));
       
        Stopwatch sw = Stopwatch.createStarted();
        String str = "1-2-3-4-5-6";
        System.err.println(Splitter.on("-").splitToList(str));
        String str2 = "xiaoming=11,xiaohong=23";
        System.err.println(Splitter.on(",").withKeyValueSeparator("=").split(str2));
        Strings.isNullOrEmpty("");
//        List<String> list = Splitter.on("-").splitToList(str);
        long time = sw.elapsed(TimeUnit.MILLISECONDS);
        System.err.println(time);
        Thread.currentThread().sleep(500);
        time = sw.elapsed(TimeUnit.MILLISECONDS);
        System.err.println(time);