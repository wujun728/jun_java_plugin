package com.kind.springboot.common.manager;

import com.kind.springboot.common.token.TokenDto;

/**
 * Function:Token管理接口.<br/>
 * Date:     2016年8月11日 下午7:28:22 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     *
     * @param userId指定用户的id
     * @return 生成的token
     */
    public TokenDto createToken(long userId);

    /**
     * 检查token是否有效
     *
     * @param modelToken
     * @return 是否有效
     */
    public boolean checkToken(TokenDto model);

    /**
     * 从字符串中解析token
     *
     * @param authentication加密后的字符串
     * @return
     */
    public TokenDto getToken(String authentication);

    /**
     * 清除token
     *
     * @param userId登录用户的id
     */
    public void removeToken(long userId);

}
