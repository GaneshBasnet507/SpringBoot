package com.example.demo.DTO;

import javax.naming.Name;

public class ReverseString {
    public String reverss(){
        String name = "Ram";
        String reverse = "";
        for(int i = name.length()-1; i>=0; i--){
          reverse = reverse+name.charAt(i);
        }
        return reverse;

    }

    public static void main(String[] args) {
        ReverseString r = new ReverseString();
        System.out.println(r.reverss());

    }
}
