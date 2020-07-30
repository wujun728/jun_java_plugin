package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test2 {

    static class Param {
        List<Yinzi> yinziList;

        public List<Yinzi> getYinziList() {
            return yinziList;
        }

        public void setYinziList(List<Yinzi> yinziList) {
            this.yinziList = yinziList;
        }
    }

    static class Yinzi {
        List<String> item;

        public List<String> getItem() {
            return item;
        }

        public void setItem(List<String> item) {
            this.item = item;
        }
    }

    public static void main(String[] args) {
        Yinzi mem = new Yinzi();
        mem.setItem(Arrays.asList("16G", "32G"));

        Yinzi color = new Yinzi();
        color.setItem(Arrays.asList("红", "黑"));

        Yinzi guobao = new Yinzi();
        guobao.setItem(Arrays.asList("国行"));

        Param param = new Param();
        param.setYinziList(Arrays.asList(mem, color, guobao));

        List<Yinzi> yinziList = param.getYinziList();

        List<String> result = combine(yinziList.get(0), yinziList, 0);
        System.out.println(result);

    }

    public static List<String> combine(Yinzi first, List<Yinzi> yinziList, int index) {
        List<String> firstItem = first.getItem();
        List<String> ret = new ArrayList<>();
        for (String item : firstItem) {
            int nextIndex = index + 1;
            if (nextIndex < yinziList.size()) {
                List<String> nextRet = combine(yinziList.get(nextIndex), yinziList, nextIndex);
                for (String string : nextRet) {
                    StringBuilder chain = new StringBuilder();
                    chain.append(item).append("/").append(string);
                    ret.add(chain.toString());
                }
            } else {
                ret.add(item);
            }
        }
        return ret;
    }

}
