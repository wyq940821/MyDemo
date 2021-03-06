package com.example.administrator.easyshop.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.example.administrator.easyshop.R;
import com.example.administrator.easyshop.commons.ActivityUtils;
import com.example.administrator.easyshop.main.userinfo.UserInfoFragment;
import com.example.administrator.easyshop.main.shop.ShopFragment;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private Unbinder unbinder;
    //项目的四个页面ID
    @BindViews({R.id.main_shop,R.id.main_message,R.id.main_mail_list,R.id.mian_mine})
    TextView[] textViews;
    //标题栏
    @BindView(R.id.main_toobar)
    Toolbar main_toobar;
    //标题
    @BindView(R.id.main_title)
    TextView main_title;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    //退出程序的判断值
    private boolean isExit = false;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);
        //设置一下actionbar
        setSupportActionBar(main_toobar);
        //设置一下ActionBae标题为空，否则默认显示应用名
        getSupportActionBar().setTitle("");
        //调用页面切换方法
        init();
    }
    //进入页面数据初始化，默认显示为市场页面
    private void init(){
        //刚进来默认选择市场
        textViews[0].setSelected(true);
        //判断用户是否登录，从而选择不同的适配器
        viewPager.setAdapter(unLoginAdapter);
        viewPager.setCurrentItem(0);

        //viewpager添加滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //textView选择处理
                for (TextView textView : textViews){
                    textView.setSelected(false);
                }
                //更改页面标题，改写为选择的效果
                main_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //Fragment的页面切换
    private FragmentStatePagerAdapter unLoginAdapter = new
            FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        //市场
                        case 0:
                            return new ShopFragment();
                        //消息
                        case 1:
                            return new UnLoginFragment();
                        //通讯录
                        case 2:
                            return new UnLoginFragment();
                        //用户信息
                        case 3:
                            return new UserInfoFragment();
                    }
                    return null;
                }
                @Override
                public int getCount() {
                    return 4;
                }
            };
    //textView点击事件
    @OnClick({R.id.main_shop,R.id.main_message,R.id.main_mail_list,R.id.mian_mine})
    public void onClick(TextView view){
        for(int i = 0;i<textViews.length;i++){
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //参数false代表瞬间切换，而不是平滑过渡
        viewPager.setCurrentItem((Integer) view.getTag(),false);
        //设置一下toolbar的title
        main_title .setText(textViews[(Integer) view.getTag()].getText());
    }
    //页面关闭方法：点击2次退出程序
    @Override
    public void onBackPressed() {
        if (!isExit){
            isExit = true;
            activityUtils.showToast("再次点击即可退出程序");
            //两秒内再次点击返回则退出
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
