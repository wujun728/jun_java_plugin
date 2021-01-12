package com.java8.newdate;



import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 使用JAVA8新的时间API LocalDate 线程安全
 * 1.LocalDate 日期
 * 2.LocalTime 时间
 * 3.LocalDataTime 日期+时间
 *
 * @author BaoZhou
 * @date 2018/8/3
 */
public class JAVA8LocalDateTest {

    @Test
    void test1() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ExecutorService executors = Executors.newCachedThreadPool();
        Callable<LocalDate> task = () -> LocalDate.parse("2018-08-21", formatter);
        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(executors.submit(task));
        }
        for (Future<LocalDate> result : results) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executors.shutdown();

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime dateTime = LocalDateTime.of(2018, 3, 12, 12, 12, 12);
        System.out.println(dateTime);

        dateTime.plusWeeks(4);
    }


    /**
     * Instant : 时间戳,默认获取UTC时区
     */
    @Test
    public void test2() {
        Instant now = Instant.now();
        System.out.println(now);
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        //原年开始计算
        System.out.println(offsetDateTime.toEpochSecond());

        Instant instant1 = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant instant2 = Instant.now();
        Duration between = Duration.between(instant1, instant2);
        System.out.println(between.getSeconds());

        LocalDate of = LocalDate.of(2015, 2, 2);
        LocalDate now1 = LocalDate.now();
        Period period = Period.between(of, now1);
        System.out.println("--------------------------------------------------------");
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }

    /**
     * 时间校正器
     */
    @Test
    void test3() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime first = now.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(first);
        System.out.println("--------------------------------------------------------");
        LocalDateTime time = now.with(temporal -> {
            LocalDateTime localDate = (LocalDateTime) temporal;
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDate.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDate.plusDays(2);
            } else {
                return localDate.plusDays(1);
            }
        });
        System.out.println(time);
    }

    /**
     * 时区时间
     */
    @Test
    void test4() {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("Brazil/West"));
        System.out.println(zonedDateTime);
    }

}
