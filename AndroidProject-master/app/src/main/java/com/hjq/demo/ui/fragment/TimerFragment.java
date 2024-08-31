package com.hjq.demo.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.ui.activity.AboutActivity;
import com.hjq.demo.ui.activity.BrowserActivity;
import com.hjq.demo.ui.activity.DialogActivity;
import com.hjq.demo.ui.activity.GuideActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.activity.ImagePreviewActivity;
import com.hjq.demo.ui.activity.ImageSelectActivity;
import com.hjq.demo.ui.activity.LoginActivity;
import com.hjq.demo.ui.activity.PasswordForgetActivity;
import com.hjq.demo.ui.activity.PasswordResetActivity;
import com.hjq.demo.ui.activity.PersonalDataActivity;
import com.hjq.demo.ui.activity.PhoneResetActivity;
import com.hjq.demo.ui.activity.RegisterActivity;
import com.hjq.demo.ui.activity.SettingActivity;
import com.hjq.demo.ui.activity.StatusActivity;
import com.hjq.demo.ui.activity.TimerActivity;
import com.hjq.demo.ui.activity.VideoPlayActivity;
import com.hjq.demo.ui.activity.VideoSelectActivity;
import com.hjq.demo.ui.dialog.InputDialog;
import com.hjq.demo.ui.dialog.MessageDialog;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 我的 Fragment
 */
public final class TimerFragment extends TitleBarFragment<HomeActivity> {

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.timer_fragment;
    }

    @Override
    protected void initView() {

        setOnClickListener(R.id.btn_time);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_time) {

            startActivity(TimerActivity.class);

        }
//        else if (viewId == R.id.btn_mine_crash) {
//
//            // 上报错误到 Bugly 上
//            CrashReport.postCatchedException(new IllegalStateException("are you ok?"));
//            // 关闭 Bugly 异常捕捉
//            CrashReport.closeBugly();
//            throw new IllegalStateException("are you ok?");
//
//        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}