package com.jun.plugin.dfs.api.response;

import com.jun.plugin.dfs.api.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerData extends BaseResponse {

    private ServerDataBody body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ServerDataBody {

        /**
         * tracker服务器可以是多个,如:10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
         */
        private String trackerServers;

        /**
         * 当前appKey对应的组
         */
        private String groupName;
    }
}
