import axios from "axios";

import errorCode from "@/utils/errorCode";
import { tansParams, blobValidate } from "@/utils/ruoyi";

import { saveAs } from "file-saver";

let downloadLoadingInstance;
// 是否显示重新登录
export let isRelogin = { show: false };

axios.defaults.headers["Content-Type"] = "application/json;charset=utf-8";
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: "/dev-api",
  // 超时
  timeout: 10000
});

// request拦截器
service.interceptors.request.use(
  config => {
    return config
  }
);
// 响应拦截器
service.interceptors.response.use(res => {
   return res.data
});

// 通用下载方法
export function download(url, params, filename, config) {
  downloadLoadingInstance = Loading.service({
    text: "正在下载数据，请稍候",
    spinner: "el-icon-loading",
    background: "rgba(0, 0, 0, 0.7)"
  });
  return service
    .post(url, params, {
      transformRequest: [
        params => {
          return tansParams(params);
        }
      ],
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      responseType: "blob",
      ...config
    })
    .then(async data => {
      const isLogin = await blobValidate(data);
      if (isLogin) {
        const blob = new Blob([data]);
        saveAs(blob, filename);
      } else {
        const resText = await data.text();
        const rspObj = JSON.parse(resText);
        const errMsg =
          errorCode[rspObj.code] || rspObj.msg || errorCode["default"];
        Message.error(errMsg);
      }
      downloadLoadingInstance.close();
    })
    .catch(r => {
      console.error(r);
      Message.error("下载文件出现错误，请联系管理员！");
      downloadLoadingInstance.close();
    });
}

export default service;
