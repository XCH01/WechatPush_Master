package com.lpc.wechatpush.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lpc.wechatpush.entity.TodayWeather;
import com.lpc.wechatpush.entity.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherUtils {
//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=9e87975dcc9ceb0967463d5d5417f250&extensions=all
    private static final String district_id = "440100";
    private static final String data_type = "all";
    private static final String ak = "pt81NVAgTjKkbDOqqPS2uyLB7DZVyD8X";


    /**
     * 获取天气
     *
     * @author lpc
     */
    public static Weather getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("district_id", district_id); // 北京市行政代码
        map.put("data_type", data_type);
        map.put("ak", ak);
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        JSONObject location = json.getJSONObject("result").getJSONObject("location");
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setWind_class(now.getString("wind_class"));
        weather.setWind_dir(now.getString("wind_dir"));
        weather.setCity(location.getString("city"));
        return weather;
    }

    /**
     * 高德天气
     */
    //城市码
    private static final String city = "440100";
    //all/base    all：未来几天天气   base：今天天气
    private static final String extensions = "all";
    //需要注册高德地图拿到key
    private static final String Key = "9e87975dcc9ceb0967463d5d5417f250";

    public static TodayWeather getTodayWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("city", city); // 北京市行政代码
        map.put("extensions", extensions);
        map.put("key", Key);
        /**
         * 请求地址
         * https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=9e87975dcc9ceb0967463d5d5417f250&extensions=all
         */
        String res = restTemplate.getForObject(
                "https://restapi.amap.com/v3/weather/weatherInfo?city={city}&extensions={extensions}&key={key}",
                String.class,
                map);
        Gson gson = new Gson();
        TodayWeather todayWeather = null;
        todayWeather = gson.fromJson(res, TodayWeather.class);


        return todayWeather;
    }
}
