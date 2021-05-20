package cn.iocoder.algorithm.leetcode.no0215;

/**
 * {@link Solution020} 的基础上，改进。
 *
 * 因为，我们是寻找第 k 大的数字，无需排序整个数组
 */
public class Solution021 {

    public int findKthLargest(int[] nums, int k) {
        return this.quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    // 返回结果，是具体数值
    private int quickSelect(int[] nums, int p, int q, int topK) {
        // 递归结束
        if (p == q) {
            return nums[p];
        }

        // 分区
        int partition = this.partition(nums, p, q);

        // 递归排序
        if (topK == partition) {
            return nums[topK];
        }
        if (topK < partition) {
            return this.quickSelect(nums, p, partition - 1, topK);
        } else {
            return this.quickSelect(nums, partition + 1, q, topK);
        }
    }

    private int partition(int[] nums, int p, int q) {
        int pivot = nums[q];
        int i = p;

        // 不断向后遍历，将比 pivot 小的值，换到 i 的位置。
        // 通过这样的方式，比 i 的位置，就是数组的 [p, i) 部分，就是比 pivot 小的数值。
        for (int j = p; j < q; j++) {
            if (nums[j] < pivot) {
                this.swap(nums, i, j);
                i++;
            }
        }

        // 将 pivot 设置到 i 处。
        // 至此，[p, i) 部分，就是比 pivot 小的数值；[i, i] ，就是 pivot 本身；(i, q] 部分，就是比 pivot 大的数值。
        swap(nums, i, q);

        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        Solution021 solution = new Solution021();
        System.out.println(solution.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
    }

}
