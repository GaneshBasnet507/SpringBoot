package com.example.demo.javacore;

public class ArrayOddNumberSum {
    public int sum(int[] arr) {
        int su = 0;
        for (int i = 0; i <= arr.length-1; i++) {
            int number = arr[i];
            if (number % 2 != 0) {
                su = su + number;
            }
        }
        return su;
    }

    public static void main(String[] args) {
        ArrayOddNumberSum p = new ArrayOddNumberSum();
        int[] arr = {2, 3, 5, 7};
        System.out.println(p.sum(arr));
    }
}
