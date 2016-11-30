package com.example.administrator.easyshop.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.easyshop.R;
import com.example.administrator.easyshop.commons.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

public class StartPageActivity extends AppCompatActivity {

    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        utils = new ActivityUtils(this);

        // TODO: 2016/11/17 0017 登录账号冲突
        // TODO: 2016/11/17 0017 判断用户是否登录 

        Timer timer = new Timer();
        //1.5秒后跳转到主页面并且finish
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                utils.startActivity(MainActivity.class);
                finish();
            }
        },1500);
    }
}
