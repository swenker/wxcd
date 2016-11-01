package com.wxcd.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wenjusun on 9/22/16.
 */
public class LambdaUsageTest2 {
    private String iamPrivate;
    final public String iamPublic="public";
    static String iamStatic;

    interface Animal {
        public String run(String a, String b);
    }

    @Test
    public void testSimpleUsage() {

        Animal adog = new Animal() {
            @Override
            public String run(String a, String b) {
                iamPrivate="";
//                iamPublic = "";
                iamStatic="";
                return null;
            }
        };

        class Abc {
            public void hunter(Animal animal){
                System.out.println(animal.run(LambdaUsageTest2.iamStatic, iamPublic));
            }
        }

        Abc abc = new Abc();
        abc.hunter((name,color)->String.join("---",name,color));

        abc.hunter((name,color)-> name + color +"==");


        List<String> items = Arrays.asList("","","");

        Collections.sort(items,(s1,s2) -> s1.compareTo(s2) );

    }
}
