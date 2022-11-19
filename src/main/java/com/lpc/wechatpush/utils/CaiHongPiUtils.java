package com.lpc.wechatpush.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lpc.wechatpush.entity.CityBean;
import com.lpc.wechatpush.entity.ConstellationBean;
import com.lpc.wechatpush.service.impl.IPushServiceImpl;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaiHongPiUtils {

    //彩虹屁key 需要去注册
    private static final String txKey = "b92eff0f3179caf824b1da03ea84b9e3";
    private static final String http = "https://apis.tianapi.com";
    //城市码
    private static final String txCity = "440100";
    //天气条数
    private static final String txType = "1";

    /**
     * 获取彩虹屁
     *
     * @author lpc
     */
    public static List<String> getCaiHongPi() {
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + txKey;
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
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + txKey;
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

    /**
     * 天行 天气请求
     *
     * @return
     */
    public static CityBean getCityBean() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("city", txCity); // 北京市行政代码
        map.put("type", txType);
        map.put("key", txKey);
        /**
         * 请求地址
         * https://apis.tianapi.com/tianqi/index
         */
        String res = restTemplate.getForObject(
                http + "/tianqi/index?city={city}&type={type}&key={key}",
                String.class,
                map);
        System.out.println("getCityBean：" + res.toString());
        Gson gson = new Gson();
        CityBean cityBean = null;
        cityBean = gson.fromJson(res, CityBean.class);
        return cityBean;
    }

    /**
     * 天行 星座请求
     *
     * @return
     */

    public static ConstellationBean getConstellationBean(String astro) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        //星座
        map.put("astro", astro);
        map.put("key", txKey);
        /**
         * 请求地址
         * https://apis.tianapi.com/tianqi/index
         */
        String res = restTemplate.getForObject(
                http + "/star/index?astro={astro}&key={key}",
                String.class,
                map);
        System.out.println("getConstellationBean：" + res.toString());
        Gson gson = new Gson();
        ConstellationBean constellationBean = null;
        constellationBean = gson.fromJson(res, ConstellationBean.class);
        return constellationBean;
    }


    public static void main(String[] args) throws ParseException {
        IPushServiceImpl iPushService = new IPushServiceImpl();
        iPushService.push();
    }
}
