/**
 * Created by kira on 16/8/2.
 */
var SimpleGridMixin = {
  methods: {
    //构造列表过滤字符串
    _getFilterStr: function (filterEntity) {
      let tableFilter = "";
      if (filterEntity != null) {
        if (filterEntity['filterLists'] != null) {
          for (let i = 0; i < filterEntity.filterLists.length; i++) {
            if (filterEntity.filterLists[i]['value'] != null) {
              tableFilter += "&" + filterEntity.filterLists[i].key + "=" + filterEntity.filterLists[i].value;
            }
          }
        }
      }
      return tableFilter;
    },
  }
}
export  default SimpleGridMixin;
