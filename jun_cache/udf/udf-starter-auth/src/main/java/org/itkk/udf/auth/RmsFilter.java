package org.itkk.udf.auth;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.exception.AuthException;
import org.itkk.udf.rms.Constant;
import org.itkk.udf.rms.RmsProperties;
import org.itkk.udf.rms.meta.ApplicationMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

/**
 * RmsFilter
 */
@Component
@Slf4j
public class RmsFilter extends ZuulFilter {

    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    /**
     * 描述 : 配置
     */
    @Autowired
    private RmsProperties rmsProperties;

    @Override
    public Object run() {
        //日志
        log.debug("RmsFilter.run");
        //获得request
        RequestContext ctx = getCurrentContext();
        //获得应用元数据
        ApplicationMeta applicationMeta = rmsProperties.getApplication().get(springApplicationName);
        //判空
        if (applicationMeta == null) {
            throw new AuthException("unrecognized systemTag:" + springApplicationName);
        }
        //获得secret
        String secret = applicationMeta.getSecret();
        //计算sign
        String sign = Constant.sign(springApplicationName, secret);
        //设置头
        ctx.addZuulRequestHeader(Constant.HEADER_RMS_APPLICATION_NAME_CODE, springApplicationName);
        ctx.addZuulRequestHeader(Constant.HEADER_RMS_SIGN_CODE, sign);
        //执行
        return null;
    }

    @Override
    public boolean shouldFilter() {
        //获得request
        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //获得服务代码
        String rmsServiceCode = request.getHeader(Constant.HEADER_SERVICE_CODE_CODE);
        if (StringUtils.isBlank(rmsServiceCode)) {
            rmsServiceCode = request.getParameter(Constant.HEADER_SERVICE_CODE_CODE);
        }
        //只拦截头信息带有rmsServiceCode值的请求
        return StringUtils.isNotBlank(rmsServiceCode);
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return org.itkk.udf.auth.Constant.ORDER_RMS;
    }
}
