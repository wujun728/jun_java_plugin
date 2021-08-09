package springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(4444, "按客户自定义,global handlerException", new Payment(2020L, "serial0003"));
    }
    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(4444, "按客户自定义2,global handlerException", new Payment(2020L, "serial0003"));
    }
}
