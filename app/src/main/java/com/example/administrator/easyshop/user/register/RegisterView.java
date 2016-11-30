package com.example.administrator.easyshop.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/22/022.
 */

public interface RegisterView extends MvpView {

    void showPrb();

    void hidePrb();

    //注册失败
    void registerFailed();
    //注册成功
    void registerSuccess();

    //提示信息(成功、失败)
    void showMessage(String msg);
    //提示信息（用户名/密码不符合规则）
    void showUserPasswordError(String msg);
}