```java

@Controller
public class CollectionController {

    @Resource
    private RedissonCollection redissonCollection;

    /**
     * map操作
     * @param user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/collection1")
    @ResponseBody
    public String collection1(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("test1","test11");
        map.put("test2","test22");
        map.put("test3","test33");
        map.put("test4","test44");
        //设置值
        redissonCollection.setMapValues("test",map);

        //获取值
        RMap<String, String> test = redissonCollection.getMap("test");
        System.out.println(test);
        return "11";
    }

    /**
     * list操作
     * @param user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/collection2")
    @ResponseBody
    public String collection2(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> list=new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        list.add("test5");
        //设置值
        redissonCollection.setListValues("list",list);

        //获取值
        RList<Object> list1 = redissonCollection.getList("list");
        System.out.println(list1);
        return "11";
    }
    /**
     * set操作
     * @param user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/collection3")
    @ResponseBody
    public String collection3(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Set<String> set=new HashSet<>();
        set.add("test1");
        set.add("test2");
        set.add("test3");
        set.add("test4");
        set.add("test5");
        //设置值
        redissonCollection.setSetValues("set",set);

        //获取值
        RSet<Object> set1 = redissonCollection.getSet("set");
        System.out.println(set1);
        return "11";
    }


}
```