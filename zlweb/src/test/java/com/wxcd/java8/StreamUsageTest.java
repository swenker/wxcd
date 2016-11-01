package com.wxcd.java8;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wenjusun on 9/9/16.
 */
public class StreamUsageTest {

    @Before
    public void setUp(){
        System.out.println("---------------------------------");
    }

    @Test
    public void testStreamFilter(){
        List<String> users = this.getCollection();

        users.stream().filter(s->s.startsWith("B")).forEach(System.out::println);

        //method reference
        users.stream().forEach(System.out::println);

    }

    @Test
    public void testStreamSort(){
        List<String> users = this.getCollection();

        users.stream().sorted().forEach(System.out::println);

        System.out.println("-------");

        users.forEach(System.out::println);
    }

    @Test
    public void testStreamMap(){

        List<String> users = this.getCollection();
        users.stream().map(String::toUpperCase).forEach(System.out::println);

    }

    @Test
    public void testStreamCount(){
        List<String> users = this.getCollection();

        System.out.println(users.stream().count());
    }


    List<String> getCollection(){
        List<String>users = new ArrayList<>();
        users.add("Mike");
        users.add("Bill");
        users.add("John");
        users.add("Jane");
        users.add("Bob");
        return users;
    }


    @Test
    public void testStreamMore(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

//        numbers.stream().map(i->","+i*i).filter(i-> i>3).forEach(System.out::print);
        numbers.stream().map(i-> i*i).filter(i-> i>3).forEach(System.out::print);
//        numbers.stream().map(i->i*i).sorted((a,b)->a<b).distinct().forEach(System.out::print);
    }
}
