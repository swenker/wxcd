package com.wxcd.java8;

import oracle.jrockit.jfr.events.DynamicValueDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


/**
 * Created by wenjusun on 9/9/16.
 */
public class DateAPITest {

    @Before
    public void setUp(){

//        ZoneId.getAvailableZoneIds().forEach(System.out::println);

    }


//    @Test
    public void testClockUsage(){
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.getZone());

        long PRCZone = clock.millis();

        Clock UTCClock = clock.withZone(ZoneId.of("Etc/UTC"));
//        UTCClock = Clock.systemUTC();
        long UTCZone = UTCClock.millis();

        System.out.println(UTCClock.instant().toString());

                System.out.printf("%d %d \n", PRCZone, UTCZone);
        System.out.println((-PRCZone + UTCZone) / 3600 / 1000);

        Date dt = Date.from(UTCClock.instant());

        Instant  instant = UTCClock.instant();
        Instant yesterday = instant.minus(86400, ChronoUnit.SECONDS);

        System.out.println(yesterday);

        System.out.println(instant.atZone(ZoneId.of("PRC")));

        System.out.println(dt);
    }


//    @Test
    public void testZoneUsage(){
        System.out.println(ZoneId.of("PRC").getRules());

    }

//    @Test
    public void testLocalTimeUsage(){

        LocalTime localTime = LocalTime.now();


        System.out.println(localTime);

        localTime = LocalTime.now(Clock.system(ZoneId.of("Etc/UTC")));
        System.out.println(localTime);

    }

    strictfp void abc(){

    }


    @Test
    public void testClockTime(){
        Clock clock = Clock.systemUTC();
        clock = Clock.system(ZoneId.of("PRC"));
        Instant  instant = clock.instant();
        Instant yesterday = instant.minus(86400, ChronoUnit.SECONDS);

        System.out.println(yesterday);


        Date dt = new Date();
        Instant instant1 = dt.toInstant();

        System.out.println(instant1.toString());

    }
}
