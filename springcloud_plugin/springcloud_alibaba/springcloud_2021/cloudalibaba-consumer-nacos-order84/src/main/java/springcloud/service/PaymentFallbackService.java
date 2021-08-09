package springcloud.service;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> PaymentSQL(Long id) {
        return new CommonResult<>(44444,"服务降级返回，----PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
