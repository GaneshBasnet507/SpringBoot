package com.example.demo.javacore;

import java.util.Scanner;

public class Fact {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("enter integer for factorial");
        int number = scan.nextInt();
        System.out.println("factorial of given integer is "+ factorial(number));
        scan.close();
    }
    public static int factorial(int n){
        int result = 1;
        for(int i=1;i<=n;i++){
            result *=i;
        }
        return result;
    }
}
