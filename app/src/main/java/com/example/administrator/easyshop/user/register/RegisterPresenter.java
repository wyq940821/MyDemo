package com.example.administrator.easyshop.user.register;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

/**
 * Created by Administrator on 2016/11/22/022.
 */

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{
    public void login(){
        getView().showPrb();

    }
    class RegisterAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getView().showMessage("注册成功！");
            getView().registerFailed();
            getView().registerSuccess();
            getView().showMessage("注册失败！");
            getView().showUserPasswordError("用户名或密码不正确！");
            getView().hidePrb();
        }
    }
}
