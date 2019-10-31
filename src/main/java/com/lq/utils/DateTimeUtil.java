package com.lq.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-10-15
 * @Time: 10:23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        LocalDateTime localDateTime = localDate.atStartOfDay();

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        LocalDateTime ldt = LocalDateTime.now();

        int hour = ldt.getHour();


        System.out.println(hour);
    }
}
