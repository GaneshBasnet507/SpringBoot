package com.example.demo.javacore;


import java.util.List;

public class PrivateString {
    public static void main(String[] args) {
        Prvate list = new Prvate();
        List<String > name = list.getList();
        System.out.println(name);
        for(int i = 0; i<name.size(); i++){
        if(name.get(i).startsWith("R")){
        System.out.println(name.get(i));
    }
        }
        System.out.println(name.size());
    }


}
