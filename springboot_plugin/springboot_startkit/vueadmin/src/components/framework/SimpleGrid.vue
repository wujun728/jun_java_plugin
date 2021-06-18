<template>
  <div class="col-xs-12">
    <div class="card">
      <div v-if="loading" style="margin-top: 20%;margin-left: 50%">
        <pulse-loader></pulse-loader>
      </div>
      <div v-else>
        <grid-title :grid-name="$route.params.name"></grid-title>
        <grid-filter :table-entity.sync="tableEntity"></grid-filter>
        <div class="card-body">
          <grid-content :table-entity="tableEntity" :grid-key="$route.params.key"></grid-content>
          <pagenation-widget :table-entity="tableEntity"></pagenation-widget>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery';
  import PulseLoader from 'vue-spinner/src/PulseLoader.vue'
  import GridTitle from '../widget/GridTitle.vue'
  import GridFilter from '../widget/GridFilter.vue'
  import GridContent from '../widget/GridContent.vue'
  import PagenationWidget from '../widget/PagenationWidget.vue'
  import SimpleGridMixin from '../../mixin/SimpleGridMixin';
  import request from 'superagent';
  require('../../assets/layer/skin/layer.css');
  require('../../assets/layer/layer');

  export default{
    mixins: [SimpleGridMixin],

    //禁止vue-router重用
    route: {
      canReuse: function () {
        return false;
      }
    },

    asyncData() {
      this._loadData(0, null);
    },

    methods: {
      _loadData(index){
        var _self = this;
        let tableFilter = this._getFilterStr(this.tableEntity);
        request
          .get(window.serverUrl + 'admin/simplegrid/list')
          .query({uid: this.$route.params.key})
          .query({limit: this.limit})
          .query({offset: this.offset})
          .query(tableFilter)
          .end(function (err, res) {
            _self.loading = false;
            _self.tableEntity = res.body;
            _self.$broadcast('child-reload', res.body, index);
          });
      },
      _deleteEntity: function (id) {
        let _self = this;
        request
          .get(window.serverUrl + '/admin/simplegrid/delete')
          .query({uid: this.$route.params.key})
          .query({id: id})
          .end(function (err, res) {
            layer.msg('删除成功');
            _self._loadData(0);
          })
      }
    },

    events: {
      'reload': function () {
        this._loadData(0);
      },
      'filter': function () {
        this._loadData(0);
      },
      'page': function (offset) {
        this.offset = offset;
        this._loadData(offset);
      },
      'delete': function (id) {
        this._deleteEntity(id);
      }
    },

    data(){
      return {
        limit: 50,
        offset: 0,
        loading: true,
        tableEntity: [],
      }
    },

    components: {
      GridTitle,
      GridFilter,
      PulseLoader,
      GridContent,
      PagenationWidget
    }
  }
</script>
