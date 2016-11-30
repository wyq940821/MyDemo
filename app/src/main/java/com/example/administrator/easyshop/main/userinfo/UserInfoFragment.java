package com.example.administrator.easyshop.main.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.easyshop.R;
import com.example.administrator.easyshop.commons.ActivityUtils;
import com.example.administrator.easyshop.user.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoFragment extends Fragment {
    @BindView(R.id.login_image)
    ImageView login_image;
    @BindView(R.id.mine_login)
    TextView mine_login;

    private View view;
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_info, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @OnClick({R.id.login_image, R.id.mine_person_info, R.id.mine_login, R.id.mine_person_goods, R.id.mine_upload_goods})
    public void onClick() {
        activityUtils.startActivity(LoginActivity.class);
    }
}
