package cn.iocoder.algorithm.leetcode.no0215;

/**
 * 和 {@link Solution} 一样，使用快速排序，不过是手写。
 */
public class Solution020 {

    public int findKthLargest(int[] nums, int k) {
        this.quickSort(nums, 0, nums.length - 1);

        return nums[nums.length - k];
    }

    private void quickSort(int[] nums, int p, int q) {
        // 递归结束
        if (p >= q) {
            return;
        }

        // 分区
        int partition = this.partition(nums, p, q);

        // 递归排序
        this.quickSort(nums, p, partition - 1);
        this.quickSort(nums, partition + 1, q);
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
        Solution020 solution = new Solution020();
        System.out.println(solution.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
    }

}
