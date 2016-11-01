package com.wxcd.java7;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by wenjusun on 9/20/16.
 */
public class JavaCoreTest {

    int a=1;
    static String s= "";

    static class NestedInnerClass{
        void abc(){
//            a=1;
            s = "11";
        }
    }

    public void testAnonymousClass(){
        Collections.sort(null, new Comparator<String>()  {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
    }

//    @Test
    public void testMath(){
        int a[];
        a = new int[4];
        a[1]=5;
        a[3]=9;

        for (int i :a){
            System.out.println(i);
        }

        System.out.println(Math.sqrt(-4D));
    }

//    @Test
    public void testReference(){
        StringBuffer s1 = new StringBuffer("abcd");

        StringBuffer s2 = getTestString(s1);

        System.out.println(s2);

        s1.delete(0,1);

        System.out.println(s2);

    }

    StringBuffer getTestString(StringBuffer s1){
        return s1;
    }

    @Test
    public void testBitOperation(){
        int a1 = 128>>1;
        int a2 = 128 >>>1;
        int a4 = -128>>1;
        int a5 = -128 >>>1;

        //负数：正数的反码加1
        System.out.printf("a1=%d,a2=%d,a1b=%s \n", a1, a2,Integer.toBinaryString(-128));

        System.out.printf("a4=%d,a5=%d", a4, a5);

        System.out.println();
        int a3 = 0b1111;

        System.out.printf("a3=%d",a3);
    }

    @Test
    public void testAssert(){
        for(int i = 1;i<3;i++){
            for(int j = 3;j>i;j++){
//                assert i!=j {System.out.println(i);}
                assert i!=j:String.valueOf(i);
            }
        }
    }
}

