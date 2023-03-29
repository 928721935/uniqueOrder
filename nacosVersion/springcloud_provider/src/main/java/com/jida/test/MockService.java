package com.jida.test;

public class MockService {
    public static void aa(){
        System.out.println("aaaaa");
    }

    public static void bb(){
        aa();
        aa();
        MockService2.cc();
        System.out.println("bbbb");
    }
}
