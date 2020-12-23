<template>
    <div class="helpDesc">
        <h2>1.组件介绍</h2>
        <p>
          行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。
        </p>
        <h2>2.类型展示</h2>
        <img src="./../../assets/image/clickWord.png" alt="">
        <img src="./../../assets/image/blockPuzzle.png" alt="">
        <h2>3.概率术语描述</h2>
        <el-table
            :data="tableData"
            border
            style="width: 100%;margin-top:10px;">
            <el-table-column
                prop="name"
                label="术语"
                width="180">
            </el-table-column>
            <el-table-column
                prop="desc"
                label="描述"
                >
            </el-table-column>
        </el-table>
        <h2>4 基本设计描述</h2>
        <p>

            <p>①用户访问产品应用页面，请求显示行为验证码</p>
            <p>②用户按照提示要求完成验证码拼图/点击</p>
            <p>③用户提交表单</p>
            <p>④验证数据随表单提交到产品后台后，产品后台需要将验证数据发送到集成jar包的/captcha/verify接口做二次校验，目的是核实验证数据的有效性。</p>
            <p>⑤集成jar包返回校验通过/失败到产品应用后端，再返回到前端</p>
            <img src="./../../assets/image/liucheng.png" alt="">

        </p>
    </div>
</template>

<script>
export default {
    name:'helpDesc',
    data() {
        return {
            tableData:[
                {name:'验证码类型',desc:'1）滑动拼图 blockPuzzle 2）文字点选 clickWord'},
                {name:'验证',desc:'用户拖动/点击一次验证码拼图即视为一次“验证”，不论拼图/点击是否正确'},
                {name:'二次校验',desc:'验证数据随表单提交到产品后台后，产品后台需要将验证数据发送到集成jar包的/captcha/verify接口做二次校验，目的是核实验证数据的有效性。'},
            ]
        }
    },

}
</script>

<style lang="less" scoped>
    .helpDesc{
        h2{
            background: #eee;
            line-height: 36px;
            text-indent: 1rem;
            margin-top: 20px;
        }
        p{
            line-height: 36px;
            text-indent: 2em;
        }
    }
</style>
