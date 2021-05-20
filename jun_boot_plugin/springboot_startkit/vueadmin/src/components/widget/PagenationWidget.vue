<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <div class="pull-right">
    <nav>
      <ul class="pagination">
        <li>
          <a href="javascript:void(0)" v-on:click="pageAction(0)" aria-label="Previous">
            <span aria-hidden="true">首页</span>
          </a>
        </li>
        <template v-for="item in pageInfo">
          <li v-bind:class="item.active"><a href="javascript:void(0)"
                                            v-on:click="pageAction($index)">{{item.name}}</a></li>
        </template>
        <li>
          <a href="javascript:void(0)" aria-label="Next" v-on:click="pageAction(pageInfo.length-1)">
            <span aria-hidden="true">尾页</span>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
  export default{
    props: ['tableEntity'],
    data(){
      let pageInfo = this._getPageInfo(this.tableEntity, 0);
      return {
        pageInfo: pageInfo,
      }
    },
    components: {},

    events: {
      'child-reload': function (tableEntity, index) {
        this.pageInfo = this._getPageInfo(tableEntity, index);
      }
    },

    methods: {
      pageAction: function (index) {
        for (let i = 0; i < this.pageInfo.length; i++) {
          this.pageInfo[i].active = "";
        }
        this.pageInfo[index].active = "active";
        this.$dispatch('page', this.pageInfo[index].name - 1);
      },
      _getPageInfo: function (tableEntity, activeIndex) {
        let pageEntity = [];
        let start = 1;
        let end = tableEntity.total + 1;
        if (tableEntity.total > 8) {
          if (tableEntity.currentpage - 8 >= 0
            && (tableEntity.offset / tableEntity.limit) + 1 < start - 4) {
            start = tableEntity.currentpage - 4;
            end = tableEntity.currentpage + 4;
          }
          if (tableEntity.currentpage - 8 >= 0
            && (tableEntity.offset / tableEntity.limit) + 1 >= start - 4) {
            start = tableEntity.currentpage - 4;
            end = tableEntity.total;
          }
          if (tableEntity.currentpage - 8 < 0) {
            start = 1;
            end = tableEntity.total;
          }
        }

        let pageInfo = [];

        let flag = 0;
        for (var i = start; i < end; i++) {
          let pageEntity = {name: i, active: flag == activeIndex ? "active" : ""}
          pageInfo.push(pageEntity);
          flag++;
        }
        return pageInfo;
      }
    }
  }
</script>
