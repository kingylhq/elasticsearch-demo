package com.lq.test;

/**
 * 鸟欲高飞先振翅
 * Created with IntelliJ IDEA.
 * Description: 简单冒泡排序
 *
 * @author: liqian
 * @email 857264134@qq.com
 * @Date: 2019-11-19
 * @Time: 17:50:20
 * 人求上进先读书
 */
public class BubbleSort {

    public static void main(String[] args) {

        Integer[] array = {20, 39, 10, -50, 5000, 2, 308, -609};
        sort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);

        }
    }

    /**
     * @param array
     */
    public static void sort(Integer[] array) {

        int length = array.length;
        if (length == 0) {
            return;
        }
        // 循环的趟数
        for (int i = 0; i < length - 1; i++) {
            // 每次的趟数需要交换的次数
            for (int j = 0; j < length - 1 - i; j++) {
                // 满足此条件，则降序排列
                if (array[j] < array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
