package com.faverolles;

public class Main {

    public static void main(String[] args) {
        try {
            PyCon.test();
        }catch (Exception ex){
            System.out.println("EXCEPTION Class[Main] Method[main]");
        }

    }
}
