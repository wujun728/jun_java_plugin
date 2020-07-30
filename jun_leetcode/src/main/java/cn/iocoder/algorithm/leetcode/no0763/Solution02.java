package cn.iocoder.algorithm.leetcode.no0763;

import java.util.ArrayList;
import java.util.List;

/**
 * 贪心算法
 *
 * 参考 https://leetcode-cn.com/problems/partition-labels/solution/shi-yong-tan-xin-suan-fa-qiu-jie-tong-guo-by-185-2/ 进行优化
 */
public class Solution02 {

    public List<Integer> partitionLabels(String S) {
        char[] array = S.toCharArray();
        List<Integer> result = new ArrayList<>();
        int startIndex = 0; // 当前开始位置
        int endIndex = 0; // 当前结束位置
        for (int i = 0; i < array.length; i++) {
            int nextIndex = S.indexOf(array[i], i + 1);
            if (nextIndex == -1) { // 当前字符未找到，并且 i 已经到达本轮的 endIndex ，则添加到 result 结果
                if (i >= endIndex) { // 注意，一定要有 > 号，例如说 "eaaaabaaec" ，在 c 的时候，其实不会选择出新的【结束位置】。
                    // 添加到结果
                    result.add(i - startIndex + 1);
                    // 设置新的开始位置
                    startIndex = i + 1;
                }
            } else if (nextIndex > endIndex) {
                // 设置本轮的新的结束位置。
                endIndex = nextIndex;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.partitionLabels("ababcbacadefegdehijhklij"));
        System.out.println(solution.partitionLabels("eaaaabaaec"));
    }

}
