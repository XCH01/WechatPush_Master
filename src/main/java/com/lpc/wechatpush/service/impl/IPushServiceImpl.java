package com.lpc.wechatpush.service.impl;

import com.lpc.wechatpush.entity.TodayWeather;
import com.lpc.wechatpush.entity.Weather;
import com.lpc.wechatpush.service.IPushService;
import com.lpc.wechatpush.utils.CaiHongPiUtils;
import com.lpc.wechatpush.utils.ChineseCalendar;
import com.lpc.wechatpush.utils.JiNianRiUtils;
import com.lpc.wechatpush.utils.WeatherUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class IPushServiceImpl implements IPushService {
    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Override
    public void push()  {
        // 1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wx92338b5ce4035e3e");
        wxStorage.setSecret("1c2dc8312e6ee2e08b7894ded5e6cd21");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                //接收方微信openId
                //测试号1
//                .toUser("optkE56wjpM9mdfVCxbnqkYQP9n4")
                //测试号2
//                .toUser("optkE516M8L9qSANtqi163sz75DE")
                //推送号
                .toUser("optkE5wion9Lkv1_HJkr8whNXxyA")
                //模板Id
//                .templateId("UpmNzkg8lznD8sP1mGdesK46E4ugEmGWgwD0HtQv2eI")
                //晚上
                .templateId("kxUR5OoRl9dZAUFrKSEClS7DWqFG6oirk3Dlzp9BTuM")
                .build();
        //TODO 模板谨慎修改
        /*
        {{riqi.DATA}}{{beizhu.DATA}}
        城市：{{city.DATA}}
        天气：{{tianqi.DATA}}
        最低气温：{{low.DATA}}°
        最高气温：{{high.DATA}}°
        当前风向：{{wind_dir.DATA}}
        当前风力：{{wind_class.DATA}}
        生日倒计时-(๑╹◡╹)ﾉ"""
        {{hersBirthday.DATA}}
        {{hisBirthday.DATA}}
        今天是我们恋爱的第{{lianai.DATA}}天

        今日土味情话：{{caihongpi.DATA}}

        {{english.DATA}}
        {{chinese.DATA}}
         */
        // 3,如果是正式版发送模版消息，这里需要配置你的信息
        TodayWeather todayWeather = WeatherUtils.getTodayWeather();
//        templateMessage.addData(new WxMpTemplateData("riqi", todayWeather.getForecasts().get(0).getCity() + "  " + todayWeather.getForecasts().get(0).getCity(), "#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("city", todayWeather.getForecasts().get(0).getCity(), "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("tianqi", todayWeather.getForecasts().get(0).getCasts().get(0).getDayweather(), "#165eaf"));
        templateMessage.addData(new WxMpTemplateData("low", todayWeather.getForecasts().get(0).getCasts().get(0).getNighttemp() + "", "#a4c9dd"));
        templateMessage.addData(new WxMpTemplateData("high", todayWeather.getForecasts().get(0).getCasts().get(0).getDaytemp() + "", "#bf242a"));
        templateMessage.addData(new WxMpTemplateData("wind_dir", todayWeather.getForecasts().get(0).getCasts().get(0).getDaywind() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("wind_class", todayWeather.getForecasts().get(0).getCasts().get(0).getDaypower() + "\n", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("english", CaiHongPiUtils.getCaiHongPi().get(0), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("chinese", CaiHongPiUtils.getCaiHongPi().get(1), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("caihongpi",""+ CaiHongPiUtils.getCaiHongPiTianGou().get(0), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri", JiNianRiUtils.getBirthdayLkx() + "", "#f09199"));

        String beizhu = "每日天气按时播报 ٩(๑>◡<๑)۶ 记得关注一下嗷~~";
        //获取当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date);
        //获取当前时间得阴历时间
        ChineseCalendar calendar = ChineseCalendar.as(today);

        //阴历4月7日  获取阴历得月份和日期进行比较
        if (calendar.getChinaMonth() == 4 && calendar.getChinaDay() == 7) {
            beizhu = "生日快乐！！！";
        }
        if (calendar.getChinaMonth() == 12 && calendar.getChinaDay() == 26) {
            beizhu = "今天是你宝贝的生日，嘿嘿！";
        }
        //计算距离下一个生日还有多少天
        int birthDayW = 0;
        int birthDayM = 0;
        /**
         * 修改生日
         */
        //他/她生日
        birthDayW = ChineseCalendar.Tool.getBirthDay("2001-4-7");
        //他/她生日
        birthDayM = ChineseCalendar.Tool.getBirthDay("2001-12-26");
        templateMessage.addData(new WxMpTemplateData("beizhu", beizhu+"\n", "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("hersBirthday","距离爱妃生日还有"+birthDayW+"天", "#b44c97"));
        templateMessage.addData(new WxMpTemplateData("hisBirthday","距离朕生日还有"+birthDayM+"天", "#f5b199"));
        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }


}
