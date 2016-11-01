package com.wxcd.java8;

import org.junit.Test;

import java.util.Optional;

/**
 * Created by wenjusun on 10/10/16.
 */
public class OptionalClassTest {

    @Test
    public void testOptionalCreation(){
        Integer aNull = null;
        Integer aValue = 9;

        Optional<Integer> nullOptional = Optional.ofNullable(aNull);
//        Optional<Integer> nullOptional2 = Optional.of(aNull);
        Optional<Integer> valueOptional = Optional.of(aValue);
        Optional<Integer> valueOptional2 = Optional.ofNullable(aValue);

        System.out.println(nullOptional.isPresent());
//        System.out.println(nullOptional2.isPresent());
        System.out.println(valueOptional.isPresent());
        System.out.println(valueOptional2.isPresent());
    }
}
