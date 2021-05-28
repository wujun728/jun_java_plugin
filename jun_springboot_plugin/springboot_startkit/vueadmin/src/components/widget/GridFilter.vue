<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="card-body well">
    <form class="form-inline">
      <template v-for="item in tableEntity.filterLists">
        <div class="form-group" v-if="item.type=='input'" style="margin-left:1%">
          <label for="{{item.name}}">{{item.name}}</label>
          <input type="text" class="form-control" id="{{item.name}}" v-model="item.value">
        </div>
      </template>
      <template v-for="selectorItem in tableEntity.filterLists">
        <div class="form-group" v-if="selectorItem.type.split('_')[0]=='selector'" style="margin-left:1%">
          <label for="{{selectorItem.name}}">{{selectorItem.name}}</label>
          <select class="form-control" id="{{selectorItem.name}}" v-model="selectorItem.value">
            <option value="" >请选择</option>
            <template v-for="item in selectorItem.datasources">
              <option value="{{item.key}}">{{item.value}}</option>
            </template>
          </select>
          <!--<input type="text" class="form-control" id="{{selectorItem.name}}" v-model="selectorItem.value">-->
        </div>
      </template>
      <button type="button" class="btn btn-info pull-right">新建</button>
      <a href="javascript:void(0)" v-on:click="filterTable()" class="btn btn-success pull-right"
         style="margin-right:1%">搜索</a>
    </form>
  </div>
</template>
<script>
  export default{
    props: ['tableEntity'],
    data(){
      return {}
    },
    methods: {
      filterTable: function () {
        this.$dispatch('filter', this.tableEntity);
      }
    }
  }
</script>
