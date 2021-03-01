package cn.iocoder.algorithm.leetcode.no0301;

import java.util.*;

/**
 * BFS + 回溯
 *
 * 思路是，每一轮，删除多余的括号。
 *
 * 相同思路，也可以使用 DFS + 回溯。相比来说，就是会重复遍历。
 */
public class Solution02 {

    private Set<String> result;
    private Queue<String> queue;
    private Set<String> visits;

    public List<String> removeInvalidParentheses(String s) {
        // 初始化
        this.result = new HashSet<>();
        this.queue = new LinkedList<>();
        this.visits = new HashSet<>();
        queue.add(s);
        visits.add(s);

        // bfs 开始
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String node = queue.poll();
                this.processNode(node);
            }
            // 如果已经出现结果，结束 bfs
            if (!result.isEmpty()) {
                break;
            }
        }
        return new ArrayList<>(result);
    }

    private void processNode(String s) {
        // 寻找 ) 出现更多的首个位置
        int leftCount = 0;
        int rightCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                leftCount++;
                continue;
            }
            if (ch == ')') {
                rightCount++;
                if (rightCount > leftCount) {
                    break;
                }
            }
        }

        // 持平，说明匹配
        if (leftCount == rightCount) {
            result.add(s);
            return;
        }

        // 计算需要删除的括号
        char deleteCh = leftCount > rightCount ? '(' : ')';
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch != deleteCh) {
                continue;
            }
            // 和前一个一样，删除意味着相同。
            if (i > 0 && ch == s.charAt(i - 1)) {
                continue;
            }
            String text = s.substring(0, i) + s.substring(i + 1);
            if (visits.contains(text)) {
                continue;
            }
            visits.add(text);
            queue.add(text);
        }
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.removeInvalidParentheses("()())()"));
//        System.out.println(solution.removeInvalidParentheses("(a)())()"));
//        System.out.println(solution.removeInvalidParentheses(")("));
//        System.out.println(solution.removeInvalidParentheses("x("));
//        System.out.println(solution.removeInvalidParentheses("x)"));
//        System.out.println(solution.removeInvalidParentheses("(()"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
//        System.out.println(solution.removeInvalidParentheses(")()(x"));
//        System.out.println(solution.removeInvalidParentheses(")()(("));
//        System.out.println(solution.removeInvalidParentheses(")()(()"));
//        System.out.println(solution.removeInvalidParentheses("(((k()(("));
//        System.out.println(solution.removeInvalidParentheses("(k))"));
//        System.out.println(solution.removeInvalidParentheses("(k()"));
        System.out.println(solution.removeInvalidParentheses("(k(())"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
//        System.out.println(solution.removeInvalidParentheses("())())"));
    }

}
