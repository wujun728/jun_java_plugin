package top.linxz.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.linxz.sell.dto.OrderDTO;
import top.linxz.sell.enums.ResultEnum;
import top.linxz.sell.exception.SellException;
import top.linxz.sell.service.OrderService;
import top.linxz.sell.service.PayService;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController  {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);


        return new ModelAndView("pay/create");
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {

        payService.notify(notifyData);

        return new ModelAndView("pay/success");

    }
}
