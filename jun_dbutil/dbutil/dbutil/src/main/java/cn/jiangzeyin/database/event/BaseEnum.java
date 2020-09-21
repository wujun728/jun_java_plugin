//package cn.jiangzeyin.database.event;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * Created by jiangzeyin on 2017/2/27.
// */
//public interface BaseEnum {
//
//    int getCode();
//
//
//    String getDesc();
//
//
//    static JSONObject toJSONObject(BaseEnum[] baseEnums) {
//        JSONObject jsonObject = new JSONObject();
//        for (BaseEnum item : baseEnums) {
//            jsonObject.put(String.valueOf(item.getCode()), item.getDesc());
//        }
//        return jsonObject;
//    }
//}
