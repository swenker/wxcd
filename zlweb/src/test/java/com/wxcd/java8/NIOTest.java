package com.wxcd.java8;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by wenjusun on 9/5/16.
 */
public class NIOTest {

//    @Test
    public void testFileChannel_by_RandomAccessFile(){
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("/home/wenjusun/wxcd/zlweb/deploy.sh","r")){

            FileChannel fileChannel = randomAccessFile.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(32);

            int bread = fileChannel.read(byteBuffer);
            while(bread!=-1){
                System.out.printf("\n===========================reads:%d \n",bread);
                byteBuffer.flip();

//                while (byteBuffer.hasRemaining()){
//                    System.out.print((char) byteBuffer.get());
//                }
                byteBuffer.clear();
                bread = fileChannel.read(byteBuffer);
            }

            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void testGetFileChannel(){
        try(FileInputStream fis = new FileInputStream("/home/wenjusun/wxcd/zlweb/pom.xml");
            FileChannel fileChannel = fis.getChannel())
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(64);

            int readCounter = fileChannel.read(byteBuffer);
            while (readCounter != -1){
                byteBuffer.flip();

                while(byteBuffer.hasRemaining()){
                        System.out.print((char)byteBuffer.get());
                }
                byteBuffer.clear();

                readCounter = fileChannel.read(byteBuffer);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void testCopyFileWithChannel(){
        String originalPath =
                "/home/wenjusun/wxcd/zlweb/../";

        Path path = Paths.get(originalPath);

        System.out.println(path.toString());
        System.out.println(path.toAbsolutePath());
        System.out.println(path.normalize());

        try {

            long currentTime = System.currentTimeMillis();
            Files.copy(Paths.get("/home/wenjusun/29.zip"),Paths.get("/home/wenjusun/30.zip"));

            System.out.println(System.currentTimeMillis()-currentTime);

//            FileChannel fileChannel = Files.newByteChannel(Paths.get("/home/wenjusun/30.zip"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Test
    public void testAsyncFileChannel(){
        Path photoFile = Paths.get("/home/wenjusun/30.zip");
        try (AsynchronousFileChannel asynchronousFileChannel =
                     AsynchronousFileChannel.open(photoFile, StandardOpenOption.READ);){

            if (!Files.exists(photoFile)){
                System.out.println("File not exist....");
                return;
            }
            else{
                System.out.println("Will handle the file");
            }

//            final ByteBuffer buffer = ByteBuffer.allocate((int)asynchronousFileChannel.size());
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            asynchronousFileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                    buffer.flip();
                    while(buffer.hasRemaining()){
                        buffer.get();
                    }

                    System.out.printf("%d -----completed \n",result);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("-----Failed");
                    exc.printStackTrace();
                }
            });

            System.out.println("Method completed......");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void testFilesReadAllLines(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("pom.xml"));
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
