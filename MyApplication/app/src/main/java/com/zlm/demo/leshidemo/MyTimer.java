package com.zlm.demo.leshidemo;

import java.util.Timer;

/**
 * Created by zhaoliming on 2016/8/9.
 */
public class MyTimer extends Timer {
    private long delaymillts = 1000;


    public void start() {
        start();
    }

    public void stop() {
       cancel();
    }
}
