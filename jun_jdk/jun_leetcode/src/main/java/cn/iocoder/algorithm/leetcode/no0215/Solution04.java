package cn.iocoder.algorithm.leetcode.no0215;

/**
 * {@link Solution03} 一样的思路，差别在于手写。
 */
public class Solution04 {

    /**
     * 堆
     */
    private class Heap {

        private int[] nums;
        private int index = 1;

        public Heap(int n) {
            this.nums = new int[n + 1];
        }

        public boolean isFull() {
            return index >= nums.length;
        }

        public void add(int num) {
            // 添加到数组尾
            nums[index] = num;
            index++;

            // 向上堆化
            int tmp = index - 1;
            while (tmp > 1) {
                if (nums[tmp] > nums[tmp >> 1]) {
                    break;
                }
                swap(tmp, tmp >> 1);
                tmp = tmp >> 1;
            }
        }

        public void remove() {
            if (index <= 1) {
                return;
            }
            index--;
            nums[1] = nums[index];

            // 向下堆化
            int tmp = 1;
            while (tmp < index) {
                int newTmp = tmp;
                if (2 * tmp < index && nums[2 * tmp] < nums[newTmp]) {
                    newTmp = 2 * tmp;
                }
                if (2 * tmp + 1 < index && nums[2 * tmp + 1] < nums[newTmp]) {
                    newTmp = 2 * tmp + 1;
                }
                if (newTmp == tmp) {
                    break;
                }
                swap(tmp, newTmp);
                tmp = newTmp;
            }
        }

        public int peek() {
            return nums[1];
        }

        private void swap(int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

    }

    public int findKthLargest(int[] nums, int k) {
        Heap queue = new Heap(k);
        for (int num : nums) {
            if (!queue.isFull()) {
                queue.add(num);
            } else {
                if (queue.peek() < num) {
                    queue.remove();
                    queue.add(num);
                }
            }
        }

        return queue.peek();
    }

    public static void main(String[] args) {
        Solution04 solution = new Solution04();
        System.out.println(solution.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
//        System.out.println(solution.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }

}
