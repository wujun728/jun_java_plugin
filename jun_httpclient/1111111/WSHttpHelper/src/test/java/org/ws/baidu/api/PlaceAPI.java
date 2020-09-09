package org.ws.baidu.api;

import org.ws.httphelper.annotation.Parameter;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.WSAbstractHttpRequest;
import org.ws.httphelper.request.WSAnnotationHttpRequest;

/**
 * Created by Administrator on 15-12-11.
 * 按照以下接口介绍开发对应接口实例
 * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-placeapi
 */
@WSRequest(
        name = "Place API 提供区域检索POI服务与POI详情服务",
        url = "http://api.map.baidu.com/place/v2/search",
        method = WSRequest.MethodType.GET,
        responseType = WSRequest.ResponseType.JSON,
        charset = "UTF-8",
        parameters = {
                @Parameter(name="q",description = "检索关键字",required = true,example = "中关村、ATM、百度大厦"),
                @Parameter(name="region",description = "检索区域",required = true,example = "北京"),
                @Parameter(name="output",description = "输出格式",defaultValue = "json",example = "日式烧烤/铁板烧、朝外大街"),
                @Parameter(name="scope",description = "检索结果详细程度",required = true,defaultValue = "1",example = ""),
                @Parameter(name="filter",description = "检索过滤条件",example = ""),
                @Parameter(name="page_size",description = "范围记录数量",defaultValue = "10",example = ""),
                @Parameter(name="page_num",description = "范围记录数量",defaultValue = "0",example = ""),
                @Parameter(name="ak",description = "用户的访问密钥",required = true,example = ""),
                @Parameter(name="sn",description = "用户的权限签名",example = ""),
                @Parameter(name="timestamp",description = "设置sn后该值必填",example = "")
        }
)
public class PlaceAPI extends WSAnnotationHttpRequest {
    public void init(WSRequestContext context){
        context.addInputData("ak","E4805d16520de693a3fe707cdc962045");
    }
}
