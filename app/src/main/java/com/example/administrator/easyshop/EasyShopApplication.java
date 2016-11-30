package com.example.administrator.easyshop;

import android.app.Application;

import com.example.administrator.easyshop.model.CachePreferences;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class EasyShopApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化本地配置
        CachePreferences.init(this);
    }
}
