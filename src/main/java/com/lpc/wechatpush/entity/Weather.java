package com.lpc.wechatpush.entity;

import lombok.Data;

@Data
public class Weather {
    /** 白天天气 */
    private String text_day;

    /** 夜晚天气 */
    private String text_night;

    /** 最高温 */
    private String high;

    /** 最低温 */
    private String low;

    /** 白天风力 */
    private String wc_day;

    /** 白天风向 */
    private String wd_day;

    /** 夜晚风力 */
    private String wc_night;

    /** 夜晚风向 */
    private String wd_night;

    /** 日期 */
    private String date;

    /** 周几 */
    private String week;

    /** 当前天气 */
    private String text_now;

    public String getText_day() {
        return text_day;
    }

    public void setText_day(String text_day) {
        this.text_day = text_day;
    }

    public String getText_night() {
        return text_night;
    }

    public void setText_night(String text_night) {
        this.text_night = text_night;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getWc_day() {
        return wc_day;
    }

    public void setWc_day(String wc_day) {
        this.wc_day = wc_day;
    }

    public String getWd_day() {
        return wd_day;
    }

    public void setWd_day(String wd_day) {
        this.wd_day = wd_day;
    }

    public String getWc_night() {
        return wc_night;
    }

    public void setWc_night(String wc_night) {
        this.wc_night = wc_night;
    }

    public String getWd_night() {
        return wd_night;
    }

    public void setWd_night(String wd_night) {
        this.wd_night = wd_night;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getText_now() {
        return text_now;
    }

    public void setText_now(String text_now) {
        this.text_now = text_now;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind_class() {
        return wind_class;
    }

    public void setWind_class(String wind_class) {
        this.wind_class = wind_class;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /** 当前温度 */
    private String temp;

    /** 当前风力大小 */
    private String wind_class;

    /** 当前风向 */
    private String wind_dir;

    /**城市*/
    private String city;
}
