package com.feri.fyw.service.intf;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 10:01
 */
public interface CaptchaService {
    void createImg(HttpSession session,HttpServletResponse response) throws IOException;
}
