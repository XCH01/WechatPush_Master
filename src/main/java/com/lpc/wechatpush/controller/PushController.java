package com.lpc.wechatpush.controller;

import com.lpc.wechatpush.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class PushController {

    @Autowired
    private IPushService pushService;

    @Scheduled(cron = "0 30 7 * * ?")
//    @Scheduled(cron = "0 15 11 ? * *")
    public void push() {
        try {
            pushService.push();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
