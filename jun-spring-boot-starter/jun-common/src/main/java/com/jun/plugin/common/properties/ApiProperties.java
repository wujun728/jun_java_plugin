//package com.jun.plugin.common.properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * 配置属性
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Component("apiProperties")
//public class ApiProperties {
//
//    /**
//     * 服务名称
//     * 所有数据以此作隔离
//     */
//	@Value("${project.groovy-api.servicename:jun-engine-api}")
//    private String servicename;
//
//    @Value("${project.groovy-api.enable:true}")
//    private String enable;
//    /**
//     * 基础注册路径
//     */
//	@Value("${project.groovy-api.context:/api}")
//    private String context;
//
//    @Value("${project.groovy-api.datasource.dbtype:}")
//    private String dbtype;
//
//    @Value("${project.groovy-api.datasource.url:}")
//    private String url;
//
//    @Value("${project.groovy-api.datasource.username:}")
//    private String username;
//
//    @Value("${project.groovy-api.datasource.password:}")
//    private String password;
//
//    @Value("${project.groovy-api.datasource.driver:}")
//    private String driver;
//
//    @Value("${project.groovy-api.api_config:api_config}")
//    private String apiconfig;
//
//
//    /**
//     * 密钥KEY
//     */
////    private String secretKey = "12345687";
//
//    /**
//     * 驼峰自动转换配置，默认true
//     */
//    private boolean mapUnderscoreToCamelCase = true;
//
//    /**
//     * 启用远程发布入口，默认true,生产环境如果访问地址暴露在了外网，建议关闭
//     * 当为false时，不接收远程发布过来的API更新或者新增
//     * 当为true时，配合secretKey使用,来达到安全远程部署的目的
//     */
//    private boolean syncEnabled = true;
//
//    /**
//     * 启用YML配置，默认false
//     */
//    private boolean configEnabled = false;
//
//
//    /**
//     * 表名称自定义
//     * 用于自定义Rocket-API表名称时使用
//     */
//    private ApiTableName tableName = new ApiTableName();
//
//    @Data
//    public static class ApiTableName{
//        private String api_config = "api_config";
//        private String api_directory = "api_directory";
//        private String api_example = "api_example";
//        private String api_info = "api_info";
//        private String api_info_history = "api_info_history";
//
//    }
//
//
//
//    /**
//     * 纯SQL模式下，通过脚本能自动识别增删改查数据库行为，但查询又分为分页，计数，返回一条，和列表，四种不种的返回格式
//     * 这四种行为将通过url后缀形式来识别，配置如下：
//     * 未定义的将返回列表结构
//     */
//    private ApiSqlModel sqlModel = new ApiSqlModel();
//
//    private DataSource dataSource = new DataSource();
//
//
//    @Data
//    public static class DataSource{
//
//        private DatasourceCheck check = new DatasourceCheck();
//
//        @Data
//        public static class DatasourceCheck{
//            /**
//             * 是否开启验证，默认为true,会定时从数据源配置中连接是否存在
//             */
//            private Boolean enabled = true;
//            /**
//             * 定时检测周期，默认一分钟,需要在Application类中开启@EnableScheduling 才能使用
//             */
//            private String fixedDelay = "PT1M";
//        }
//
//    }
//
//    @Data
//    public static class ApiSqlModel{
//        /**
//         * 分页查询，api将返回分页查询
//         */
//        private String pagerSuffix = "/page";
//        /**
//         * 计数查询，api将返回数量
//         */
//        private String countSuffix = "/count";
//        /**
//         * 返回首条，api将返回列表中的第一条记录
//         */
//        private String findOneSuffix = "/first";
//
//        /**
//         * 其他返回list,所有查询结果
//         */
//    }
//
//    /**
//     * 分页参数配置
//     */
//    private Pager pager = new Pager();
//
//    @Data
//    public static class Pager{
//        /**
//         * 每页大小变量名
//         */
//        private String pageSizeVarName = "pageSize";
//        /**
//         * 第几页变量名
//         */
//        private String pageNoVarName = "pageNo";
//        /**
//         * limit offset变量名
//         */
//        private String offsetVarName = "offset";
//
//        /**
//         * 分页list查询结果变量名
//         */
//        private String dataVarName = "data";
//
//        /**
//         * 分页count查询结果变量名
//         */
//        private String totalRecordsVarName = "totalRecords";
//
//        /**
//         * 总页数变量名
//         */
//        private String totalPagesVarName = "totalPages";
//
//        /**
//         * 默认每页大小
//         */
//        private Integer defaultPageSizeValue = 15;
//        /**
//         * 默认第几页
//         */
//        private Integer defaultPageNoValue = 1;
//    }
//}
