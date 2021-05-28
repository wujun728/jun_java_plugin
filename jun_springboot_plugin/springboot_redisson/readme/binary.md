```java
@Controller
public class BinaryController {

    @Resource
    private RedissonBinary redissonBinary;

    /**
     * 存放图片
     * @param user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/binary1")
    @ResponseBody
    public String binary1(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        redissonBinary.setValue("binary",new FileInputStream(new File("f:/1.png")));
        return "11";
    }

    /**
     * 获取图片
     * @param user
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/binary2")
    public void binary2(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        redissonBinary.getValue("binary",response.getOutputStream());

    }
}
```