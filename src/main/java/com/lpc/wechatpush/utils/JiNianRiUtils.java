package com.lpc.wechatpush.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JiNianRiUtils {
    public static int getLianAi() {
        return calculationLianAi("2021-11-15");
    }

    public static int getBirthdayLkx() {
        try {
            return calculationBirthday("2001-04-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getBirthdayLpc() {
        try {
            return calculationBirthday("2000-11-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int calculationBirthday(String clidate) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cToday = Calendar.getInstance(); // 存今天
        Calendar cBirth = Calendar.getInstance(); // 存生日
        cBirth.setTime(myFormatter.parse(clidate)); // 设置生日
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR)); // 修改为本年
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            // 生日已经过了，要算明年的了
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {
            // 生日还没过
            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }

    public static void main(String[] args) throws ParseException {
        int i = calculationBirthday("2022-07-24");
        System.out.println(i);
    }

    public static int calculationLianAi(String date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int day = 0;
        try {
            long time = System.currentTimeMillis() - simpleDateFormat.parse(date).getTime();
            day = (int) (time / 86400000L) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
