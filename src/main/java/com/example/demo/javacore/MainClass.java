package com.example.demo.javacore;

public class MainClass {

    {
        System.out.println("I am instance block");
    }

    static {
        System.out.println("I am static block");
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        mainClass.walk();
    }

    public void walk() {
        System.out.println("i am working method");
    }
}
