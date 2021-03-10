package com.demo.weixin.enums;

/**
 * @author Wujun
 * @description
 * @date 2017/7/31
 * @since 1.0
 */
public enum MenuType {
    click,//点击推事件 微信服务器会通过消息接口推送消息类型为event 的结构给开发者 并且带上按钮中开发者填写的key值
    view,// 跳转URL
    scancode_push,//微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
    scancode_waitmsg,//扫码推事件且弹出“消息接收中”提示框
    pic_sysphoto,//弹出系统拍照发图
    pic_photo_or_album,//弹出拍照或者相册发图
    pic_weixin,// 	弹出微信相册发图器
    location_select;// 	弹出地理位置选择器
}
