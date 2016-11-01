package com.wxcd.java8;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by wenjusun on 10/10/16.
 */
public class Base64Test {

    @Test
    public void testEncoder(){
        Base64.Encoder base64Encoder  = Base64.getEncoder();
        Base64.Encoder base64UrlEncoder  = Base64.getUrlEncoder();

        Base64.Decoder base64Decoder = Base64.getDecoder();
        Base64.Decoder base64UrlDecoder = Base64.getUrlDecoder();
        try {
            String encodeResult = base64Encoder.encodeToString("https://www.tutorialspoint.com/java8/java8_base64.htm".getBytes("UTF8"));
            byte[] encodeByteResult = base64UrlEncoder.encode("https://www.tutorialspoint.com/java8/java8_base64.htm".getBytes("UTF8"));

            System.out.println(encodeResult);
            System.out.println(new String(encodeByteResult));

            System.out.println(new String(base64Decoder.decode(encodeResult)));
            System.out.println(new String(base64UrlDecoder.decode(encodeByteResult)));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
