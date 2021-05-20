<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
  <table class="table table-bordered">
    <thead>
    <tr>
      <template v-for="item in tableEntity.tableHeader">
        <th v-bind:style="item.styleName" v-if="index!=0">{{item.name}}</th>
      </template>
      <th style="width:15%">操作</th>
    </tr>
    </thead>
    <tbody>

    <template v-for="item in tableEntity.tableContent">
      <tr>
        <td v-for="value in item" v-if="index!=0">{{value}}</td>
        <td>
          <button type="button" class="btn btn-warning ">编辑</button>
          <button type="button" v-on:click="deleteEntity(item.id)" class="btn btn-danger "
                  style="margin-right:1%">删除
          </button>
        </td>
      </tr>
    </template>
    </tbody>
  </table>
</template>
<script>

  export default{
    props: ['tableEntity', 'gridKey'],
    data(){
      return {}
    },
    components: {
    },
    methods: {
      deleteEntity: function (id) {
        this.$dispatch('delete',id);
      }
    }
  }
</script>
