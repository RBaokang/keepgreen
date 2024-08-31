package com.hjq.demo.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.demo.R;
import com.hjq.demo.aop.Permissions;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.bean.Event;
import com.hjq.demo.database.EventDatabaseHelper;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.adapter.EventAdapter;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 消息 Fragment
 */
public final class MessageFragment extends Fragment {
    private CalendarView calendarView;
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private EventDatabaseHelper dbHelper;
    private String selectedDate;
    private ImageView mImageView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.message_fragment, container, false);

        calendarView = view1.findViewById(R.id.calendarView);
        eventRecyclerView = view1.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelper = new EventDatabaseHelper(getActivity());
        eventAdapter = new EventAdapter(new ArrayList<>(), this::showDeleteDialog);
        eventRecyclerView.setAdapter(eventAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            loadEventsForDate(selectedDate);
        });
        view1.findViewById(R.id.btnAddEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDate == null) {
                    Toast.makeText(getActivity(), "请选择一个日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText input = new EditText(getActivity());
                new AlertDialog.Builder(getActivity())
                        .setTitle("添加事件")
                        .setView(input)
                        .setPositiveButton("添加", (dialog, which) -> {
                            String description = input.getText().toString();
                            if (!description.isEmpty()) {
                                dbHelper.addEvent(selectedDate, description);
                                loadEventsForDate(selectedDate);
                            } else {
                                Toast.makeText(getActivity(), "事件描述不能为空", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        return view1;
    }
    private void loadEventsForDate(String date) {
        List<Event> events = dbHelper.getEventsByDate(date);
        eventAdapter.updateEvents(events);
    }


    private void showDeleteDialog(Event event) {
        new AlertDialog.Builder(getActivity())
                .setTitle("删除事件")
                .setMessage("确定删除该事件？")
                .setPositiveButton("删除", (dialog, which) -> {
                    dbHelper.deleteEvent(event.getId());
                    loadEventsForDate(selectedDate);
                })
                .setNegativeButton("取消", null)
                .show();
    }
   /* public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initView() {
        mImageView = findViewById(R.id.iv_message_image);
        setOnClickListener(R.id.btn_message_image1, R.id.btn_message_image2, R.id.btn_message_image3,
                R.id.btn_message_toast, R.id.btn_message_permission, R.id.btn_message_setting,
                R.id.btn_message_black, R.id.btn_message_white, R.id.btn_message_tab);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_message_image1) {

            mImageView.setVisibility(View.VISIBLE);
            GlideApp.with(this)
                    .load("https://www.baidu.com/img/bd_logo.png")
                    .into(mImageView);

        } else if (viewId == R.id.btn_message_image2) {

            mImageView.setVisibility(View.VISIBLE);
            GlideApp.with(this)
                    .load("https://www.baidu.com/img/bd_logo.png")
                    .circleCrop()
                    .into(mImageView);

        } else if (viewId == R.id.btn_message_image3) {

            mImageView.setVisibility(View.VISIBLE);
            GlideApp.with(this)
                    .load("https://www.baidu.com/img/bd_logo.png")
                    .transform(new RoundedCorners((int) getResources().getDimension(R.dimen.dp_20)))
                    .into(mImageView);

        } else if (viewId == R.id.btn_message_toast) {

            toast("我是吐司");

        } else if (viewId == R.id.btn_message_permission) {

            requestPermission();

        } else if (viewId == R.id.btn_message_setting) {

            XXPermissions.startPermissionActivity(this);

        } else if (viewId == R.id.btn_message_black) {

            getAttachActivity()
                    .getStatusBarConfig()
                    .statusBarDarkFont(true)
                    .init();

        } else if (viewId == R.id.btn_message_white) {

            getAttachActivity()
                    .getStatusBarConfig()
                    .statusBarDarkFont(false)
                    .init();

        } else if (viewId == R.id.btn_message_tab) {

            HomeActivity.start(getActivity(), HomeFragment.class);
        }
    }

    @Permissions(Permission.CAMERA)
    private void requestPermission() {
        toast("获取摄像头权限成功");
    }*/
}