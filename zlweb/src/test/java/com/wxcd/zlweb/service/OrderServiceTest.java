package com.wxcd.zlweb.service;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wenjusun on 6/28/16.
 */
public class OrderServiceTest {
    static OrderService orderService = new OrderService();
    static SecureRandom random = null;

    @BeforeClass
    public static void initSecureRandom() {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");

            System.out.println("init");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void releaseResource() {
        orderService.closeDataSource();
        System.out.println("release...");
    }

    //    @Test
    public void testOnePlaceOrder() {
        int userId = random.nextInt(10000);
        int productId = 10;
        int num = random.nextInt(5);
        orderService.placeOrder(userId, productId, num);
    }


//    @Test
    public void testInitStockEntries() {
        for (int i = 0; i < 100; i++) {
            int productId = i + 1;
            int inStock = random.nextInt(100000);

            orderService.addStock(productId, inStock);
        }
    }

    @Test
    public void testUpdateStock(){
        ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 30, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

        class UpdateStockTask implements Runnable {
            @Override
            public void run() {
                int productId =random.nextInt(10);
//                int productId = 100;
                int num = random.nextInt(5);
                orderService.updateStock_withlock(productId, num);
                System.out.println("num:" + num);

            }
        }

        for (int i = 0; i < 20; i++) {
            UpdateStockTask orderTask = new UpdateStockTask();
            try {
                pool.execute(orderTask);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (RejectedExecutionException ree) {
                int retry = 10;
                do {
                    System.out.println("retry....." + retry);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        pool.execute(orderTask);
                        retry = 0;
                    } catch (RejectedExecutionException e) {
                        retry--;
                        System.out.println("will retry ....." + retry);
                    }

                } while (retry > 0);
            }
        }


        while (pool.getActiveCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("all completed.");
        pool.shutdown();

    }

    //    @Test
    public void testManyOrders() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 30, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

        final AtomicInteger counter = new AtomicInteger();


        class PlaceOrderTask implements Runnable {
            @Override
            public void run() {
                int userId = random.nextInt(10000);
                int productId = 10 * random.nextInt(100);
                int num = random.nextInt(5);
                orderService.placeOrder(userId, productId, num);
                System.out.println("placing order..:" + counter.incrementAndGet());

/*

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
*/

            }
        }

        for (int i = 0; i < 200; i++) {
            PlaceOrderTask orderTask = new PlaceOrderTask();
            try {
                pool.execute(orderTask);
//            if (i % 10 == 0) {
                /**
                 * The insert itself will take about 100 ms and the 15ms sleep will avoid Reject from the Queue.
                 *
                 * */
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
            } catch (RejectedExecutionException ree) {
                int retry = 5;
                do {
                    System.out.println("retry....." + retry);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        pool.execute(orderTask);
                        retry = 0;
                    } catch (RejectedExecutionException e) {
                        retry--;
                        System.out.println("will retry ....." + retry);
                    }

                } while (retry > 0);
            }
        }

        while (pool.getActiveCount() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("all completed.");
        pool.shutdown();

    }
}
