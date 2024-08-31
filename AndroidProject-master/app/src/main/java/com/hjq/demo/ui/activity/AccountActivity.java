package com.hjq.demo.ui.activity;





import android.view.View;

import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.AppActivity;

import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.SwitchButton;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/03/01
 *    desc   : 设置界面
 */
public final class AccountActivity extends AppActivity
        implements SwitchButton.OnCheckedChangeListener {

    private SettingBar mbtn_mine_login;
    private SettingBar mbtn_mine_register;
    private SettingBar mbtn_mine_change;
    private SettingBar mbtn_mine_forget;
    private SettingBar mbtn_mine_reset;
    private SwitchButton mAutoSwitchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initView() {
        mbtn_mine_login = findViewById(R.id.btn_mine_login);

        mbtn_mine_change = findViewById(R.id.btn_mine_change);
        mbtn_mine_forget = findViewById(R.id.btn_mine_forget);





        setOnClickListener(R.id.btn_mine_login, R.id.btn_mine_change,
                R.id.btn_mine_forget);
    }

    @Override
    protected void initData() {


    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_mine_login) {
            startActivity(LoginActivity.class);

        }  else if (viewId == R.id.btn_mine_change) {

            startActivity(PhoneResetActivity.class);

        } else if (viewId == R.id.btn_mine_forget) {

            startActivity(PasswordForgetActivity.class);

        }
    }
    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {
        toast(checked);
    }
}