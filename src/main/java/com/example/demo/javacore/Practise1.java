package com.example.demo.javacore;

public class Practise1 {

    public static void main(String[] args) {
        Practise1 p = new Practise1();
        int[] storeArr = p.sum();
        for (int num : storeArr) {
            System.out.println(num);
        }
        int sum = p.sumNum();
        System.out.println(sum);
    }

    public int[] sum() {
        int[] arr = {1, 2, 3};
        return arr;
    }
    public int sumNum(){
        int sum = 0;
        int[] arrSum = {1,2,3,4};
        for(int i =0;i<=arrSum.length;i++){
                sum += i;
        }
        return sum;
    }

}
