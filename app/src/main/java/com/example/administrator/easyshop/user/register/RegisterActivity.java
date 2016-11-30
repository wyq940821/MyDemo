package com.example.administrator.easyshop.user.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.easyshop.R;
import com.example.administrator.easyshop.commons.ActivityUtils;
import com.example.administrator.easyshop.commons.RegexUtils;
import com.example.administrator.easyshop.components.AlertDialogFragment;
import com.example.administrator.easyshop.components.ProgressDialogFragment;
import com.example.administrator.easyshop.model.CachePreferences;
import com.example.administrator.easyshop.model.User;
import com.example.administrator.easyshop.model.UserResult;
import com.example.administrator.easyshop.network.EasyShopClient;
import com.example.administrator.easyshop.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_username)
    EditText register_username;
    @BindView(R.id.register_password)
    EditText register_password;
    @BindView(R.id.register_confirm_password)
    EditText register_confirm_password;
    @BindView(R.id.register_btn)
    Button register_btn;
    private String username;
    private String password;
    private String pwd_again;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;

    @BindView(R.id.register_prb)
    ProgressBar register_prb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        init();
    }
//    @Override
//    public RegisterPresenter createPresenter(){
//        return  new RegisterPresenter();
//    }
//    @Override
//    public void showPrb(){
//        register_prb.setVisibility(View.VISIBLE);
//    }
//    @Override
//    public void hidePrb(){
//        register_prb.setVisibility(View.GONE);
//    }
//    @Override
//    public void registerFailed(){
//        register_prb.setVisibility(View.VISIBLE);
//    }
//    @Override
//    public void registerSuccess(){
//        register_prb.setVisibility(View.VISIBLE);
//    }
//    @Override
//    public void showMessage(String msg){
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    public void showUserPasswordError(String msg){
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//    }
//    @OnClick(R.id.register_prb)
//    public void onClick(){
//        getPresenter().login();
//    }

    private void init() {
        register_username.addTextChangedListener(textWatcher);
        register_password.addTextChangedListener(textWatcher);
        register_confirm_password.addTextChangedListener(textWatcher);
        setSupportActionBar(toolbar);
        //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            username = register_username.getText().toString();
            password = register_password.getText().toString();
            pwd_again = register_confirm_password.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                    || TextUtils.isEmpty(pwd_again));
            register_btn.setEnabled(canLogin);
        }
    };
    @OnClick(R.id.register_btn)
    public void onClick() {
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.username_rules);
            showUserPasswordError(msg);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.password_rules);
            showUserPasswordError(msg);
            return;
        } else if (!TextUtils.equals(password, pwd_again)) {
            String msg = getString(R.string.username_equal_pwd);
            showUserPasswordError(msg);
            return;
        }

        Call call = EasyShopClient.getInstance().register(username,password);

        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                activityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                //拿到返回的结果
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                //根据结果码处理不同情况
                if (userResult.getCode() == 1){
                    activityUtils.showToast("注册成功！");
                    //拿到用户的实体类
                    User user = userResult.getData();
                    //将用户信息保存到本地配置里
                    CachePreferences.setUser(user);

                    // TODO: 2016/11/21 0021 页面跳转实现，使用eventbus
                    // TODO: 2016/11/21 0021 还需要登录环信，待实现
                }else if (userResult.getCode() == 2){
                    activityUtils.showToast(userResult.getMessage());
                }else{
                    activityUtils.showToast("未知错误！");
                }
            }
        });
    }

    //显示错误提示
    public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
    }
}


















