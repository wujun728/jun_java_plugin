package com.lzb.onlinejava.complier.dto;

public class JavaComileResult {

    private Boolean state;  // 整体状态码

    private ResultItem[] resultItems;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public ResultItem[] getResultItems() {
        return resultItems;
    }

    public void setResultItems(ResultItem[] resultItems) {
        this.resultItems = resultItems;
    }

    public static class ResultItem {
         String testData; // 测试数据
         private Boolean state; //测试是否通过

        public String getTestData() {
            return testData;
        }

        public void setTestData(String testData) {
            this.testData = testData;
        }

        public Boolean getState() {
            return state;
        }

        public void setState(Boolean state) {
            this.state = state;
        }
    }

}
