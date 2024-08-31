package com.hjq.demo.ui.fragment;

import static com.hjq.demo.ui.activity.LoginActivity.LoginPhone;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.database.DBUtils;
import com.hjq.demo.ui.activity.AccountActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.activity.ImageCropActivity;
import com.hjq.demo.ui.activity.PersonalDataActivity;
import com.hjq.demo.ui.activity.SettingActivity;
import com.hjq.widget.layout.SettingBar;
import com.hjq.demo.ui.activity.ImageSelectActivity;
import com.hjq.demo.ui.activity.ImagePreviewActivity;
import com.hjq.demo.http.glide.GlideApp;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.io.File;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 我的 Fragment
 */
public final class MineFragment extends TitleBarFragment<HomeActivity> {

    private SettingBar mAvatarSettingBar;
    private ImageView mAvatarImageView;
    private SettingBar mIdSettingBar;
    private SettingBar mNameSettingBar;
    private SettingBar mAccountSettingBar;
    private SettingBar mPersonalInfoSettingBar;
    private SettingBar mSettingSettingBar;
    private NestedScrollView mScrollView;
    private String nickname;
    private int id;
    private String diqu;
    private String url;
    private Uri mAvatarUrl;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView() {
        // 绑定控件
        mAvatarSettingBar = findViewById(R.id.fl_person_data_avatar);
        mAvatarImageView = findViewById(R.id.iv_person_data_avatar);
        mIdSettingBar = findViewById(R.id.sb_person_data_id);
        mNameSettingBar = findViewById(R.id.sb_person_data_name);
        mAccountSettingBar = findViewById(R.id.sb_account);
        mPersonalInfoSettingBar = findViewById(R.id.btn_mine_personal);
        mSettingSettingBar = findViewById(R.id.btn_mine_setting);


        // 绑定点击事件
        setOnClickListener(mAvatarSettingBar, mAvatarImageView, mAccountSettingBar, mPersonalInfoSettingBar, mSettingSettingBar);
    }

    @Override
    protected void initData() {

        if (LoginPhone == "111") {
            nickname = "请先登录";
            id = 0;
            // 初始化头像
            GlideApp.with(this)
                    .load(R.drawable.avatar_placeholder_ic)
                    .placeholder(R.drawable.avatar_placeholder_ic)
                    .error(R.drawable.avatar_placeholder_ic)
                    .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                    .into(mAvatarImageView);

        } else {
            try {
                url = new DBUtils().getAvatarByPhone(LoginPhone);
                nickname = new DBUtils().getNicknameByPhone(LoginPhone);
                id = new DBUtils().getIdByPhone(LoginPhone);
                diqu = new DBUtils().getRegionByPhone(LoginPhone);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 初始化头像
            GlideApp.with(this)
                    .load(url)
                    .placeholder(R.drawable.avatar_placeholder_ic)
                    .error(R.drawable.avatar_placeholder_ic)
                    .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                    .into(mAvatarImageView);

        }
        mNameSettingBar.setRightText(nickname);
        mIdSettingBar.setRightText(String.valueOf(id));
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.fl_person_data_avatar || viewId == R.id.iv_person_data_avatar) {
            // 处理头像点击，选择图片

            ;
        } else if (viewId == R.id.sb_account) {
            startActivity(AccountActivity.class);
        } else if (viewId == R.id.btn_mine_personal) {
            startActivity(PersonalDataActivity.class);
        } else if (viewId == R.id.btn_mine_setting) {
            startActivity(SettingActivity.class);
        }
    }

    /**
     * 裁剪图片
     */
    @Override
    public void onResume() {
        super.onResume();
        // 在这里刷新Fragment的数据或视图
        initData();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}
