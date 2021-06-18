package cn.iocoder.algorithm.leetcode.no0301;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/remove-invalid-parentheses/
 *
 * BFS + 逐步拼凑合理的组合。
 *
 * 未通过，还在找原因。错误案例是 ")()))())))" ，答案是 ["(())","()()"] ，目前输出是 ["()()"]
 *
 * 简化案例为 ())()) 。因为第 108 行代码，固话了答案为 (()) ，无法获得 ()() 答案。
 */
public class Solution {

    public class Node {

        private String s;
        private String text;
        private int lastIndex;

        public Node(String s, String text, int lastIndex) {
            this.s = s;
            this.text = text;
            this.lastIndex = lastIndex;
        }
    }

    private Set<String> result;
    private Queue<Node> queue;

    public List<String> removeInvalidParentheses(String s) {
        this.result = new HashSet<>();
        this.queue = new LinkedList<>();
        // 初始队列
        queue.add(new Node(s,"", 0));

        // bfs 开始
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                this.processNode(node);
            }
            // 如果已经出现结果，结束 bfs
            if (!result.isEmpty()) {
                break;
            }
        }
        return new ArrayList<>(result);
    }

    private void processNode(Node node) {
        // 寻找 ) 出现更多的首个位置
        String s = node.s;
        int leftCount = 0;
        int rightCount = 0;
        int rightMoreIndex = -1; // 首次出现 ) 更多的位置
        for (int i = node.lastIndex; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                leftCount++;
                continue;
            }
            if (ch == ')') {
                rightCount++;
                if (rightCount > leftCount) {
                    rightMoreIndex = i;
                    break;
                }
            }
        }

        // 如果并未出现
        if (rightMoreIndex == -1) {
            // 持平，说明匹配
            if (leftCount == rightCount) {
                result.add(node.text + s.substring(node.lastIndex));
                return;
            }
            // 尝试删除一个 ( 括号
            for (int i = node.lastIndex; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch != '(') {
                    continue;
                }
                // 和前一个一样，删除意味着相同。
                if (i > node.lastIndex && ch == s.charAt(i - 1)) {
                    continue;
                }
                queue.add(new Node(s.substring(0, i) + s.substring(i + 1),
                        node.text,
                        node.lastIndex));
            }
            return;
        }

        // 如果出现，尝试删除一个 ) 括号
        for (int i = node.lastIndex; i <= rightMoreIndex; i++) {
            char ch = s.charAt(i);
            if (ch != ')') {
                continue;
            }
            // 和前一个一样，删除意味着相同。
            if (i > node.lastIndex && ch == s.charAt(i - 1)) {
                continue;
            }
            queue.add(new Node(s,node.text + s.substring(node.lastIndex, i) + s.substring(i + 1, rightMoreIndex + 1),
                    rightMoreIndex + 1));
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
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
//        System.out.println(solution.removeInvalidParentheses("(k(())"));
//        System.out.println(solution.removeInvalidParentheses(")()("));
        System.out.println(solution.removeInvalidParentheses("())())"));
    }

}
