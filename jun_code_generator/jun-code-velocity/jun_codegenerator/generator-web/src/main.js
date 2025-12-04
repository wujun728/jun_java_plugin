// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import Element from "element-ui";
import plugins from "./plugins"; // plugins
import directive from "./directive"; // directive
import "./assets/icons"; // icon
import "element-ui/lib/theme-chalk/index.css";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar";
// 分页组件
import Pagination from "@/components/Pagination";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree
} from "@/utils/ruoyi";

Vue.component("Pagination", Pagination);
Vue.component("RightToolbar", RightToolbar);

Vue.config.productionTip = false;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.handleTree = handleTree;
Vue.use(Element);
Vue.use(directive);
Vue.use(plugins);
/* eslint-disable no-new */

new Vue({
  el: "#app",
  router,
  render: h => h(App)
});
