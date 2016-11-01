package com.wxcd.java7;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * Created by wenjusun on 9/1/16.
 */
public class ExceptionHandlerTest {

    @Test
    public void testExceptionCatch(){


        try (FileInputStream fis = new FileInputStream("");FileChannel fic = fis.getChannel() )
        {
            Class.forName("");
            ByteBuffer buffers=ByteBuffer.allocate(4096);

            fic.read(buffers);

        } catch (IOException |ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }


    }
}
