import modal from "./modal";
import tab from "./tab";
import download from "./download";

export default {
  install(Vue) {
    Vue.prototype.$modal = modal;
    Vue.prototype.$tab = tab;
    Vue.prototype.$download = download;
  }
};
