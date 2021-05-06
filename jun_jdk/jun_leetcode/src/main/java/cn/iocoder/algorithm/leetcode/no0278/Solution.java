package cn.iocoder.algorithm.leetcode.no0278;

/**
 * https://leetcode-cn.com/problems/first-bad-version/
 */
public class Solution extends VersionControl {

    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (isBadVersion(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.firstBadVersion(5));
        System.out.println(solution.firstBadVersion(3));
        System.out.println(solution.firstBadVersion(2126753390));
    }

}
