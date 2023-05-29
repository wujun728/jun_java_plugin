package com.jun.plugin.util4j.common.game.cdkey;

/**
 * 还有一种根据激活码信息转换为字节数组,然后编码为base64,
 * 字节数组的信息可以用枚举byte值来表示激活码的各种属性,以减少数组长度
 * @author Administrator
 */
public interface CdkeyFactory {

	public String build();
}
