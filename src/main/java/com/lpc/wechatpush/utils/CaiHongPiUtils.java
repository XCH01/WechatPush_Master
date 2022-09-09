package com.lpc.wechatpush.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lpc.wechatpush.service.impl.IPushServiceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CaiHongPiUtils {

    //彩虹屁key 需要去注册
    private static final String key = "b92eff0f3179caf824b1da03ea84b9e3";

    /**
     * 获取彩虹屁
     *
     * @author lpc
     */
    public static List<String> getCaiHongPi() {
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + key;
        BufferedReader reader = null;
        String result = null;
         StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String content = newslist.getJSONObject(0).getString("content");
        String note = newslist.getJSONObject(0).getString("note");
        ArrayList<String> strings = new ArrayList<>();
        strings.add(content);
        strings.add(note);
        return strings;
    }


    public static List<String> getCaiHongPiTianGou() {
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + key;
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String content = newslist.getJSONObject(0).getString("content");
        ArrayList<String> strings = new ArrayList<>();
        strings.add(content);

        return strings;
    }




    public static void main(String[] args) throws ParseException {
        IPushServiceImpl iPushService = new IPushServiceImpl();
        iPushService.push();
    }
}
