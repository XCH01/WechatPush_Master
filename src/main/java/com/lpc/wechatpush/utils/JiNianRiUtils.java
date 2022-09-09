package com.lpc.wechatpush.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author byu_rself
 * @date 2022/8/22 19:01
 */
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
    /*
    	{{riqi.DATA}}
    	{{beizhu.DATA}}
    	 城市：{{city.DATA}}
    	 天气：{{tianqi.DATA}}
    	  最低气温：{{low.DATA}}
    	  最高气温：{{high.DATA}}
    	  当前风向：{{wind_dir.DATA}}
    	  当前风力：{{wind_class.DATA}}
    	   {{hersBirthday.DATA}}
    	   {{hisBirthday.DATA}}
    	   今天是我们恋爱的第{{lianai.DATA}}天
    	   今日土味情话：{{caihongpi.DATA}}
    	   {{english.DATA}}
    	   {{chinese.DATA}}
     */

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
