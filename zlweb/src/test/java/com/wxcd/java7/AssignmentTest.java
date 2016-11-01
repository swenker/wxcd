package com.wxcd.java7;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wenjusun on 9/1/16.
 */
public class AssignmentTest {

    @Test
    public void testUnderlineValueAssignment(){
        int price=1_000_000;

        System.out.println(price);
    }

    @Test
    public void testCollectionAssignment(){
//        List<String> names = ["Mike","Carmen"];
        List<String> names = new ArrayList<>();

//        Map<String, Integer> map = {"key" : 1};

    }

    @Test
    public void testBinaryAssignment(){
        int a = 0b1000_0000;

        System.out.println(a);
    }

    @Test
    public void testMapAssignment(){

//        Map<String,String> mymap = {"name":"Mj"};

    }
}
