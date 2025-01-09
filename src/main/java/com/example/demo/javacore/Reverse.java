package com.example.demo.javacore;

public class Reverse {
    String name = "Ganesh";
    String reverse = "";


    public String rev() {
        int length = name.length();
        for (int i = length - 1; i >= 0; i--) {
            reverse += name.charAt(i);
        }
        if(reverse.equals(name)){
            System.out.println("they are equal");
        }
        else{
            System.out.println("they are not eqaul");
        }
        return reverse;
    }

    public static void main(String[] args) {
        Reverse re = new Reverse();
        System.out.println("Reversed name: " + re.rev());
    }
}
