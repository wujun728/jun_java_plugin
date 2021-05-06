package cn.iocoder.algorithm.leetcode.no0229;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/majority-element-ii/
 *
 * Boyer-Moore 投票算法的升级版，相当于 {@link cn.iocoder.algorithm.leetcode.no0169.Solution02} 的
 *
 * 超过 n/3 个元素的，个数可以是 0 个、1 个、2 个。
 *
 * 大体逻辑是
 * 1. 先选出 2 个候选的众数，当 count 为零的情况下。
 * 2. 然后，将新来的数字，如果和这两个众数不同，就 count 都减一。
 * 3. 如果 count 又出现为零，则新的数字，设置为新的众数。
 * 4. 最终，将候选的两个数字，在去重新统计数量，判断是否超过 1/3 。
 *
 * 其实，比较的理解是，分成 3 堆，一堆是众数 A ，一堆是众数 B ，一堆是剩余的数字。
 * 之后，如果有数字等于 A 或者 B ，则计数++ ，然后都不满足，则减少 A 和 B 的计数。
 * 因为，一开始选出的 A 和 B 可能不对，所以得出的计数，只能知道它们两是最多的，但是无法判断是不是超过 n/3 个元素，所以需要最后重新计数。
 */
public class Solution02 {

    public List<Integer> majorityElement(int[] nums) {
        Integer aNum = null, bNum = null;
        int aCount = 0, bCount = 0;
        for (int num : nums) {
            // 设置候选
            if (aCount == 0
                    && (bNum == null || bNum != num)) { // 避免 b 已经选择
                aNum = num;
            } else if (bCount == 0
                    && aNum != num) { // 避免 a 已经选择
                bNum = num;
            }

            // 计数
            if (aNum == num) {
                aCount++;
                continue;
            }
            if (bNum == num) {
                bCount++;
                continue;
            }

            bCount--;
            aCount--;
        }

        // 再将 aNum、bNum 候选，重新投票
        aCount = bCount = 0;
        for (int num : nums) {
            if (aNum == num) {
                aCount++;
            } else if (bNum != null && bNum == num) {
                bCount++;
            }
        }
        List<Integer> result = new ArrayList<>();
        if (aCount > nums.length / 3) {
            result.add(aNum);
        }
        if (bCount > nums.length / 3) {
            result.add(bNum);
        }
        return result;
    }

}
