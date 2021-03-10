package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/*
 * @Description: 企业部门
 * @version V1.0
 */
public class QYDept extends BaseWeixinResponse{
    private List<QYDeptInfo> department;//部门列表数据。以部门的order字段从小到大排列

    public static class QYDeptInfo {
        private int id;
        private String name;
        @JSONField(name = "parentid")
        private int parentId; // 父亲部门id。根部门为1
        private int order; // 在父部门中的次序值。order值小的排序靠前。

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    public List<QYDeptInfo> getDepartment() {
        return department;
    }

    public void setDepartment(List<QYDeptInfo> department) {
        this.department = department;
    }
}
