package cn.iocoder.algorithm.leetcode.no0726;

import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/number-of-atoms/
 *
 * 要注意，原子的定义。
 */
public class Solution {

    /**
     * 遍历 formula 的位置
     *
     * 设置成属性，无需每个方法去填写
     */
    private int index;

    public String countOfAtoms(String formula) {
        // 计数，每个原子的数量
        Map<String, Integer> counts = this.recursiveCountOfAtoms(formula);
        // 拼接字符串
        StringBuilder result = new StringBuilder();
        counts.forEach((name, count) -> {
            result.append(name);
            if (count > 1) {
                result.append(count);
            }
        });
        return result.toString();
    }

    private Map<String, Integer> recursiveCountOfAtoms(String formula) { // 可以进一步优化，formula 直接转换成数组
        Map<String, Integer> counts = new TreeMap<>();
        for (; index < formula.length();) {
            char ch = formula.charAt(index);
            if (ch == '(') {
                // 递归，判断 () 内，有都少原子组合，结果到 tmp 中
                index++;
                Map<String, Integer> tmp = this.recursiveCountOfAtoms(formula);
                // 添加回 counts 中
                tmp.forEach((name, count) -> counts.put(name, counts.getOrDefault(name, 0) + count));
                continue;
            }
            if (ch == ')') {
                index++;
                int nowCount = getCount(formula);
                if (nowCount > 1) {
                    counts.replaceAll((name, count) -> count * nowCount);
                }
                return counts;
            }
            String name = getName(formula);
            int count = getCount(formula);
            counts.put(name, counts.getOrDefault(name, 0) + count);
        }
        return counts;
    }

    private int getCount(String formula) {
        int number = 0;
        while (index < formula.length()
            && formula.charAt(index) >= '0' && formula.charAt(index) <= '9') {
            number = number * 10 + formula.charAt(index) - '0';
            index++;
        }
        return number > 0 ? number : 1;
    }

    private String getName(String formula) {
        // 原子首字母
        String name = "" + formula.charAt(index);
        index++;
        // 原子后续的小写字母
        while (index < formula.length()
                && formula.charAt(index) >= 'a' && formula.charAt(index) <= 'z') {
            name += formula.charAt(index);
            index++;
        }
        return name;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.countOfAtoms("H2O"));
//        System.out.println(solution.countOfAtoms("(Mg(OH)2)2"));
        System.out.println(solution.countOfAtoms("K4(ON(SO3)2)2"));
    }

}
