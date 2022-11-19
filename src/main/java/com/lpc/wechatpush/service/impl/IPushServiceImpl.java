package com.lpc.wechatpush.service.impl;

import com.lpc.wechatpush.entity.CityBean;
import com.lpc.wechatpush.entity.ConstellationBean;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IPushServiceImpl implements IPushService {

    private String wxAppid = "wx92338b5ce4035e3e";
    private String wxSecret = "1c2dc8312e6ee2e08b7894ded5e6cd21";
    private String constellation = "摩羯座";

    //测试
    private String MYMODENUMBER_A = null;
    private String MYMODEID_A = null;

    //正式
    private String MYMODENUMBER_B = "optkE5wion9Lkv1_HJkr8whNXxyA";
    private String MYMODENUMBER_C = "";

    //早晚模型
    private String MYMODEID_MORNING = "";
    private String MYMODEID_NIGHT = "FTg_M8TCxCVG7uTcU3Yq8QL0T_sdDN3nfAz-CPD3svo";

    /**
     * 生日
     */
    private String me = "";
    private String she = "";


    /**
     * mySwitch
     * 测试：1
     * 正式早：2
     * 正式晚：3
     */
    private String mySwitch = "2";

    /**
     * 模板配置
     */
    WxMpTemplateMessage templateMessage;
    WxMpService wxMpService;

    /**
     * 天气请求 类
     */
    CityBean cityBean;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Override
    public void push() {
        // 1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(wxAppid);
        wxStorage.setSecret(wxSecret);
        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        switch (mySwitch) {
            case "1":
                MYMODENUMBER_A = "optkE56wjpM9mdfVCxbnqkYQP9n4";
                //推送宝贝
//                MYMODENUMBER_A = "optkE5wion9Lkv1_HJkr8whNXxyA";
                MYMODEID_A = "5ZbYLM_0l7l8Vjq3k-2Joks9wAItCUNgzhnmVVxlGlY";
                break;
            case "2":
                MYMODENUMBER_A = "optkE56wjpM9mdfVCxbnqkYQP9n4";
                MYMODEID_A = "G90x63rPWBTxs-3PVtZhAbNeuMJBqxSBFG9M0xaaYVo";
                break;
        }


        // 2,推送消息
        templateMessage = WxMpTemplateMessage.builder()
                //接收方微信openId
                //测试号1
                .toUser(MYMODENUMBER_A)
                .templateId(MYMODEID_A)
                .build();
        //TODO 模板谨慎修改
        /*
{{riqi.DATA}}{{beizhu.DATA}}
城市：{{city.DATA}}
天气：{{tianqi.DATA}}
空气：{{kongqi.DATA}}
最低气温：{{low.DATA}}°
最高气温：{{high.DATA}}°
当前风向：{{wind_dir.DATA}}
当前风力：{{wind_class.DATA}}
生日倒计时-{{shengri.DATA}}
{{hersBirthday.DATA}}
{{hisBirthday.DATA}}
今天是我们恋爱的第{{lianai.DATA}}天

今日土味情话：{{caihongpi.DATA}}

{{english.DATA}}
{{chinese.DATA}}


         */
        // 3,如果是正式版发送模版消息，这里需要配置你的信息
        switch (mySwitch) {
            case "1":
                getModeIDA();
                break;
            case "2":
                getModeIDB();
                break;
            case "3":

                break;
        }
        getBirthDay("2001-12-26", "2001-4-7");

    }

    public void getBirthDay(String me, String she) {
        //计算距离下一个生日还有多少天
        int birthDayW = 0;
        int birthDayM = 0;
        /**
         * 修改生日
         */
        //他/她生日
        birthDayW = ChineseCalendar.Tool.getBirthDay(she);
        //他/她生日
        birthDayM = ChineseCalendar.Tool.getBirthDay(me);
        templateMessage.addData(new WxMpTemplateData("hersBirthday", "[ " + birthDayW + " ]", "#b44c97"));
        templateMessage.addData(new WxMpTemplateData("hisBirthday", "[ " + birthDayM + " ]", "#f5b199"));
        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
        /*
{{riqi.DATA}}{{beizhu.DATA}}
城市：{{city.DATA}}
天气：{{tianqi.DATA}}   空气：{{kongqi.DATA}}
最低气温：{{low.DATA}}   最高气温：{{high.DATA}}
当前风向：{{wind_dir.DATA}}    当前风力：{{wind_class.DATA}}
{{shengria.DATA}}
生日倒计时:{{shengri.DATA}}ヽ(•ω•ゞ)
距离爱妃生日还有{{hersBirthday.DATA}}天 距离朕生日还有{{hisBirthday.DATA}}天
星座：{{xingzuo.DATA}}
综合指数：{{zh.DATA}}爱情指数：{{gz.DATA}}财运指数：{{cy.DATA}}
健康指数：{{jk.DATA}}幸运颜色：{{ys.DATA}}幸运数字：{{sz.DATA}}
匹配星座：{{pp.DATA}}
今日土味情话：{{caihongpi.DATA}}

{{english.DATA}}
         */

    public void getModeIDA() {
        cityBean = CaiHongPiUtils.getCityBean();
        ConstellationBean constellationBean = CaiHongPiUtils.getConstellationBean(constellation);
        List<ConstellationBean.ResultDTO.ListDTO> list = constellationBean.getResult().getList();
        templateMessage.addData(new WxMpTemplateData("beizhu", getTitleMsg() + "\n", "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("city", cityBean.getResult().getArea(), "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("kongqi", cityBean.getResult().getQuality(), "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("tianqi", cityBean.getResult().getWeather(), "#165eaf"));
        templateMessage.addData(new WxMpTemplateData("low", cityBean.getResult().getLowest() + "", "#a4c9dd"));
        templateMessage.addData(new WxMpTemplateData("high", cityBean.getResult().getHighest() + "", "#bf242a"));
        templateMessage.addData(new WxMpTemplateData("wind_dir", cityBean.getResult().getWind() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("wind_class", cityBean.getResult().getWindsc() + "\n", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("shengria", "(๑╹◡╹)ﾉ”“”", "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("english", CaiHongPiUtils.getCaiHongPi().get(0) + "\n", "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("caihongpi", "" + CaiHongPiUtils.getCaiHongPiTianGou().get(0), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("shengri", JiNianRiUtils.getBirthdayLkx() + "", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("xingzuo", "" + constellation, "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("zh", "" + list.get(0).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("gz", "" + list.get(2).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("cy", "" + list.get(3).getContent() + "\t\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("jk", "" + list.get(4).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("ys", "" + list.get(5).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("sz", "" + list.get(6).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("jieshao", "", "#FF1493"));
    }

    public void getModeIDB() {
        cityBean = CaiHongPiUtils.getCityBean();
        ConstellationBean constellationBean = CaiHongPiUtils.getConstellationBean(constellation);
        List<ConstellationBean.ResultDTO.ListDTO> list = constellationBean.getResult().getList();
        templateMessage.addData(new WxMpTemplateData("beizhu", getTitleMsg() + "\n", "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("city", cityBean.getResult().getArea(), "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("kongqi", cityBean.getResult().getQuality(), "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("tianqi", cityBean.getResult().getWeather(), "#165eaf"));
        templateMessage.addData(new WxMpTemplateData("low", cityBean.getResult().getLowest() + "", "#a4c9dd"));
        templateMessage.addData(new WxMpTemplateData("high", cityBean.getResult().getHighest() + "", "#bf242a"));
        templateMessage.addData(new WxMpTemplateData("wind_dir", cityBean.getResult().getWind() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("wind_class", cityBean.getResult().getWindsc() + "\n", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("shengria", "(๑╹◡╹)ﾉ”“”", "#e6b422"));
        templateMessage.addData(new WxMpTemplateData("english", CaiHongPiUtils.getCaiHongPi().get(0) + "\n", "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("caihongpi", "" + CaiHongPiUtils.getCaiHongPiTianGou().get(0), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("shengri", JiNianRiUtils.getBirthdayLkx() + "", "#f09199"));
//        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("xingzuo", "" + constellation, "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("zh", "" + list.get(0).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("gz", "" + list.get(1).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("cy", "" + list.get(3).getContent() + "\t\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("jk", "" + list.get(4).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("ys", "" + list.get(5).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("sz", "" + list.get(6).getContent() + "\t\t", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("pp", "摩羯座", "#f09199"));
        templateMessage.addData(new WxMpTemplateData("jieshao", "", "#FF1493"));
    }

    public String getTitleMsg() {
        //获取当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date);
        //获取当前时间得阴历时间
        ChineseCalendar calendar = ChineseCalendar.as(today);
        String beizhu = "";
        //阴历4月7日  获取阴历得月份和日期进行比较
        if ("1".equals(mySwitch) && calendar.getChinaMonth() == 4 && calendar.getChinaDay() == 7) {
            return beizhu = "生日快乐！！！";
        } else if ("1".equals(mySwitch) && calendar.getChinaMonth() == 12 && calendar.getChinaDay() == 26) {
            return beizhu = "今天是你宝贝的生日，嘿嘿！";
        } else {
            return beizhu = "每日天气按时播报 ٩(๑>◡<๑)۶ 记得关注一下嗷~~";
        }
    }

}
