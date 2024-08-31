package com.hjq.demo.ui.activity;

import static com.hjq.demo.ui.activity.LoginActivity.LoginPhone;

import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.AppActivity;
import com.hjq.demo.database.DBUtils;
import com.hjq.demo.http.api.UpdateImageApi;
import com.hjq.demo.http.glide.GlideApp;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.ui.dialog.AddressDialog;
import com.hjq.demo.ui.dialog.InputDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.FileContentResolver;
import com.hjq.widget.layout.SettingBar;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/04/20
 *    desc   : 个人资料
 */
public final class PersonalDataActivity extends AppActivity {

    private ViewGroup mAvatarLayout;
    private ImageView mAvatarView;
    private SettingBar mIdView;
    private SettingBar mNameView;
    private SettingBar mAddressView;

    /** 省 */
    private String mProvince = "广东省";
    /** 市 */
    private String mCity = "广州市";
    /** 区 */
    private String mArea = "天河区";

    /** 头像地址 */
    private Uri mAvatarUrl;
    private String nickname;
    private int id;
    private String diqu;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.personal_data_activity;
    }

    @Override
    protected void initView() {
        mAvatarLayout = findViewById(R.id.fl_person_data_avatar);
        mAvatarView = findViewById(R.id.iv_person_data_avatar);
        mIdView = findViewById(R.id.sb_person_data_id);
        mNameView = findViewById(R.id.sb_person_data_name);
        mAddressView = findViewById(R.id.sb_person_data_address);
        setOnClickListener(mAvatarLayout, mAvatarView, mNameView, mAddressView);
        if (LoginPhone=="111"){
            nickname = "请先登录";
            id = 0;
        }else {
            try {
                url = new DBUtils().getAvatarByPhone(LoginPhone);
                nickname = new DBUtils().getNicknameByPhone(LoginPhone);
                id = new DBUtils().getIdByPhone(LoginPhone);
                diqu = new DBUtils().getRegionByPhone(LoginPhone);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    protected void initData() {
        GlideApp.with(getActivity())
                .load(url)
                .placeholder(R.drawable.avatar_placeholder_ic)
                .error(R.drawable.avatar_placeholder_ic)
                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(mAvatarView);

        mIdView.setRightText(String.valueOf(id));
        mNameView.setRightText(nickname);

        String address = mProvince + mCity + mArea;
        mAddressView.setRightText(diqu);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (LoginPhone=="111"){
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            if (view == mAvatarLayout) {
                ImageSelectActivity.start(this, data -> {
                    // 裁剪头像
                    cropImageFile(new File(data.get(0)));
                });
            } else if (view == mAvatarView) {
                if (mAvatarUrl != null) {
                    // 查看头像
                    ImagePreviewActivity.start(getActivity(), mAvatarUrl.toString());
                } else {
                    // 选择头像
                    onClick(mAvatarLayout);
                }
            } else if (view == mNameView) {
                new InputDialog.Builder(this)
                        // 标题可以不用填写
                        .setTitle(getString(R.string.personal_data_name_hint))
                        .setContent(mNameView.getRightText())
                        //.setHint(getString(R.string.personal_data_name_hint))
                        //.setConfirm("确定")
                        // 设置 null 表示不显示取消按钮
                        //.setCancel("取消")
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener((dialog, content) -> {
                            if (!mNameView.getRightText().equals(content)) {
                                mNameView.setRightText(content);
                                int up = 0;
                                try {
                                    up = new DBUtils().updateNickname(LoginPhone, content);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if (up > 0) {
                                   // Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                   // Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            } else if (view == mAddressView) {
                new AddressDialog.Builder(this)
                        //.setTitle("选择地区")
                        // 设置默认省份
                        .setProvince(mProvince)
                        // 设置默认城市（必须要先设置默认省份）
                        .setCity(mCity)
                        // 不选择县级区域
                        //.setIgnoreArea()
                        .setListener((dialog, province, city, area) -> {
                            String address = province + city + area;
                            if (!mAddressView.getRightText().equals(address)) {
                                mProvince = province;
                                mCity = city;
                                mArea = area;
                                mAddressView.setRightText(address);
                                int di = 0;
                                try {
                                    di = new DBUtils().updateRegionByPhone(LoginPhone, address);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if (di > 0) {
                                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();
            }
        }
    }

    /**
     * 裁剪图片
     */
    private void cropImageFile(File sourceFile) {
        ImageCropActivity.start(this, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {

            @Override
            public void onSucceed(Uri fileUri, String fileName) {
                File outputFile;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    outputFile = new FileContentResolver(getActivity(), fileUri, fileName);
                } else {
                    try {
                        outputFile = new File(new URI(fileUri.toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        outputFile = new File(fileUri.toString());
                    }
                }
                updateCropImage(outputFile, true);
            }

            @Override
            public void onError(String details) {
                // 没有的话就不裁剪，直接上传原图片
                // 但是这种情况极其少见，可以忽略不计
                updateCropImage(sourceFile, false);
            }
        });
    }

    /**
     * 上传裁剪后的图片
     */
    private void updateCropImage(File file, boolean deleteFile) {
        if (true) {
            if (file instanceof FileContentResolver) {
                mAvatarUrl = ((FileContentResolver) file).getContentUri();
            } else {
                mAvatarUrl = Uri.fromFile(file);
            }
            GlideApp.with(getActivity())
                    .load(mAvatarUrl)
                    .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                    .into(mAvatarView);
            try {
                int updateResult = new DBUtils().updateAvatarByPhone(LoginPhone, mAvatarUrl.toString());
                if (updateResult > 0) {
                    Toast.makeText(this, "头像更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "头像更新失败", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "头像更新出现异常", Toast.LENGTH_SHORT).show();
            }

            return;
        }

       /* EasyHttp.post(this)
                .api(new UpdateImageApi()
                        .setImage(file))
                .request(new HttpCallback<HttpData<String>>(this) {

                    @Override
                    public void onSucceed(HttpData<String> data) {
                        mAvatarUrl = Uri.parse(data.getData());
                        GlideApp.with(getActivity())
                                .load(mAvatarUrl)
                                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                                .into(mAvatarView);
                        if (deleteFile) {
                            file.delete();
                        }
                    }
                });*/
    }
}