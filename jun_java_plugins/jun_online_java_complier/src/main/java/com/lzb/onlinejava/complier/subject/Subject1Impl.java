package com.lzb.onlinejava.complier.subject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lzb.onlinejava.complier.dto.SubjectResult;
import com.lzb.onlinejava.complier.service.JavaComileService;
import com.lzb.onlinejava.complier.service.impl.JavaComplieServiceImpl;
import com.lzb.onlinejava.complier.util.EqualUtil;
import com.lzb.onlinejava.complier.util.ToStringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Subject1Impl implements Subject {

    public String title = "两数之和";

    public String initcode = "public class Solution {\n" +
            "    public int[] twoSum(int[] nums, int target) {\n" +
            "        int[] a = new int[2];\n" +
            "            a[0] = 0;\n" +
            "            a[1] = 1;\n" +
            "        return a;\n" +
            "     \n" +
            "    }\n" +
            "}\n";

    public String describe =  "<p>给定一个整数数组 <code>nums</code>&nbsp;和一个目标值 <code>target</code>，请你在该数组中找出和为目标值的那&nbsp;<strong>两个</strong>&nbsp;整数，并返回他们的数组下标。</p>\n" +
            "                                <p>你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。</p>";


    public String example = "给定 nums = [2, 7, 11, 15], target = 9<br>" +
            "因为 nums[<strong>0</strong>] + nums[<strong>1</strong>] = 2 + 7 = 9<br>" +
            "所以返回 [<strong>0, 1</strong>]";


    private String arg1 = "[[2,7,11,15], [1,4,6,2]]";
    private String arg2 = "[9,8]";
    private String result = "[[0,1], [2,3]]";

    public String methodName = "twoSum";

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getIntitCode() {
        return this.initcode;
    }

    @Override
    public String getDescribe() {
        return this.describe;
    }

    @Override
    public String getExample() {
        return this.example;
    }

    /**
     * 执行代码
     * @param javaCode
     * @return
     */
    @Override
    public SubjectResult execueMethod(String javaCode) {
        JSONArray array1 = JSON.parseArray(arg1);
        JSONArray array2 = JSON.parseArray(arg2);
        JSONArray resultArray = JSON.parseArray(result);
        JavaComileService javaComileService = new JavaComplieServiceImpl();
        Class runClass = null;
        try {
            runClass = javaComileService.complie(javaCode, "Solution");
        } catch (Exception e) {
            e.printStackTrace();
            return new SubjectResult(0, "编译错误");
        }

        Method method = null;
        try {
            method = runClass.getMethod(methodName, int[].class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        JSONArray intJSONArray = array1.getJSONArray(0);

        int[] arg1 = JSONArrayToIntArray(intJSONArray);
        System.out.println(arg1.length);
        int arg2 = array2.getInteger(0);
        int[] result = JSONArrayToIntArray(resultArray.getJSONArray(0));
        try {
            int[] resultIntArray = (int[]) method.invoke(runClass.newInstance(), arg1, arg2);
            if (!EqualUtil.intArray(resultIntArray, result)) {
                SubjectResult subjectResult = new SubjectResult();
                subjectResult.setStatus(0);
                subjectResult.setInfo("第:" + 0 + "个测试用例没通过");
                subjectResult.setInput("arg1:" + ToStringUtil.IntArrayToString(arg1) + "arg2:" + arg2);
                subjectResult.setOutput(ToStringUtil.IntArrayToString(resultIntArray));
                subjectResult.setExpect(ToStringUtil.IntArrayToString(result));
                return subjectResult;

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return new SubjectResult(1, "测试完成");
    }


    /**
     * 提交代码
     * @param javaCode
     * @return
     */
    @Override
    public SubjectResult submit(String javaCode) {
        JSONArray array1 = JSON.parseArray(arg1);
        JSONArray array2 = JSON.parseArray(arg2);
        JSONArray resultArray = JSON.parseArray(result);
        JavaComileService javaComileService = new JavaComplieServiceImpl();
        Class runClass = null;
        try {
            runClass = javaComileService.complie(javaCode, "Solution");
        } catch (Exception e) {
            e.printStackTrace();
            return new SubjectResult(0, "编译错误");
        }

        Method method = null;
        try {
            method = runClass.getMethod(methodName, int[].class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return new SubjectResult(0, "运行错误");
        }

        for (int i = 0; i < array1.size(); i++) {
            JSONArray intJSONArray = array1.getJSONArray(i);

            int[] arg1 = JSONArrayToIntArray(intJSONArray);
            System.out.println(arg1.length);
            int arg2 = array2.getInteger(i);
            int[] result = JSONArrayToIntArray(resultArray.getJSONArray(i));
            try {
                int[] resultIntArray = (int[]) method.invoke(runClass.newInstance(), arg1, arg2);
                if (!EqualUtil.intArray(resultIntArray, result)) {
                    SubjectResult subjectResult = new SubjectResult();
                    subjectResult.setStatus(0);
                    subjectResult.setInfo("第:" + i + "个测试用例没通过");
                    subjectResult.setInput("arg1:" + ToStringUtil.IntArrayToString(arg1) + "arg2:" + arg2);
                    subjectResult.setOutput(ToStringUtil.IntArrayToString(resultIntArray));
                    subjectResult.setExpect(ToStringUtil.IntArrayToString(result));
                    return subjectResult;

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
        return new SubjectResult(1, "测试通过");

    }


    private int[] JSONArrayToIntArray(JSONArray intJSONArray) {
        List<Integer> intList = intJSONArray.toJavaList(Integer.class);
        int[] intArray = new int[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            intArray[i] = intList.get(i);
        }
        return intArray;

    }

    public static void main(String[] args) {
        new Subject1Impl().execueMethod("f");
    }




}
