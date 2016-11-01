package com.wxcd.java8;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by wenjusun on 9/12/16.
 */
public class LambdaUsageTest {

//    @Test
    public void testSimpleUsage(){
        List<String> users = Arrays.asList("mike","peter","john");

        Collections.sort(users,(a,b)->(a.compareTo(b)));


        //Method reference
        users.forEach(System.out::println);

    }

//    @Test
    public void testInterface(){
        Predicate<String>  predicate= (s)->s.length()>0;

        System.out.println(predicate.test("abc"));

    }

//    @Test
    public void testFunctionInterface(){
        Function<String,String> function = String::toLowerCase;

        System.out.println(function.apply("ABC$"));

    }

    static class Book{
        String name;
        Book(){
            this.name = null;
        }

        public String toString(){
            return "Default Book";
        }
    }
//    @Test
    public void testSupplierInterface(){
        Supplier<Book> supplier = Book::new;
        System.out.println( supplier.get());

    }

//    @Test
    public void testConsumerInterface(){
        Consumer<String>consumer = System.out::println;

        consumer.accept("sssssssssssssssssss");
    }

    @Test
    public void testOptionalInterface(){
        Optional<String> optional = Optional.of("abc");

        System.out.println(optional.isPresent());
    }



    interface MathOperation{
        public int operate(int a,int b);
    }

    interface Greeting{
//        public void say(String message,String person);
        public void say(String message);

    }
    @Test
    public void testLambda(){

        MathOperation add = (a,b) -> a+b;
        MathOperation multiply = (a,b) -> a*b;

        System.out.println(add.operate(1,2));
        System.out.println(multiply.operate(1, 2));

        Greeting hello = System.out::println;

        hello.say("Baby");

        Predicate<String> isUser=a->{
            return a.length()==10;
        };

        System.out.println(isUser.or(isUser));
        System.out.println(isUser.test("123456789"));

    }
}
