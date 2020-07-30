package cn.iocoder.algorithm.leetcode.no0433;

/**
 * 回溯
 */
public class Solution03 {

    public int minMutation(String start, String end, String[] bank) {
        int min = this.calcMin(start, end, bank, new boolean[bank.length]);
        if (min > bank.length) { // 超过，意味着没有答案
            return -1;
        }
        return min;
    }

    private int calcMin(String start, String end, String[] bank, boolean[] visited) {
        // 匹配，返回
        if (start.equals(end)) {
            return 0;
        }

        // 继续递归匹配
        int min = bank.length + 1; // 注意，此处不能使用 Integer.MAX ，因为下面求和，会超过 int 的范围
        for (int i = 0; i < bank.length; i++) {
            // 跳过已经访问过
            if (visited[i]) {
                continue;
            }
            // 不匹配，下一个
            String target = bank[i];
            if (!this.canConvert(start, target)) {
                continue;
            }
            // 匹配
            visited[i] = true; // 标记已使用
            min = Math.min(min, this.calcMin(target, end, bank, visited) + 1);
            visited[i] = false; // 标记未使用
        }

        return min;
    }

    private boolean canConvert(String from, String to) {
        int count = 0;
        for (int i = 0; i < from.length(); i++) {
            if (from.charAt(i) != to.charAt(i)) {
                count++;
                if (count >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
        System.out.println(solution.minMutation(
                "AACCGGTT", "AAACGGTA",
                new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"}
        ));
    }

}
