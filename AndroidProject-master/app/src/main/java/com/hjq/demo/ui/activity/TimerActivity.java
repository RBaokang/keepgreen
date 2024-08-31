package com.hjq.demo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.hjq.demo.util.CircleTimerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.hjq.demo.R;
//import com.hjq.demo.util.EasyFloatView;
import com.hjq.demo.util.MyNotification;

public class TimerActivity extends AppCompatActivity implements CircleTimerView.CircleTimerListener {

    private static final String TAG = TimerActivity.class.getSimpleName();

    private CircleTimerView mTimer;
    private Button startBtu;
    private boolean startBtuStatus=true;//开始计时按钮状态

    private NumberPicker WorkTime;
    private NumberPicker RestTime;
    private NumberPicker Frequency;
    private SwitchMaterial vibrate;
    private SwitchMaterial ring;

    private int mWorkTime=25;//工作时间
    private int mRestTime=5;//休息时间
    private int mFrequency=4;//组数
    private boolean isWork=true;//工作状态

    private SharedPreferences sharedPreferences;//实例化 SharedPreferences
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //初始化SharedPreferences储存
        sharedPreferences= getApplicationContext().getSharedPreferences("Pomodoro", Context.MODE_PRIVATE);
        //将SharedPreferences储存可编辑化
        editor=sharedPreferences.edit();

        mTimer = findViewById(R.id.circle_timer);
        mTimer.setCircleTimerListener(this);

        editor.putBoolean("ScreenOn",true);
        editor.commit();

        Button ScreenOnBtu=findViewById(R.id.screen_on_button);//常亮按钮
        if(sharedPreferences.getBoolean("ScreenOn",true)){
            //在Window增加flag打开屏幕常亮：
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            ScreenOnBtu.setBackgroundResource(R.drawable.ic_keep_screen_on);
        }else {
            //在Window去除flag关闭屏幕常亮：
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            ScreenOnBtu.setBackgroundResource(R.drawable.ic_keep_screen_off);
        }



        ScreenOnBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getBoolean("ScreenOn",true)){
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    editor.putBoolean("ScreenOn",false);
                    ScreenOnBtu.setBackgroundResource(R.drawable.ic_keep_screen_off);
                    Toast.makeText(getApplicationContext(), getString(R.string.screen_on_off),Toast.LENGTH_SHORT).show();
                }else {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    editor.putBoolean("ScreenOn",true);
                    ScreenOnBtu.setBackgroundResource(R.drawable.ic_keep_screen_on);
                    Toast.makeText(getApplicationContext(), getString(R.string.screen_on_on),Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });

        startBtu = findViewById(R.id.start_button);
        startBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CircleTimerView.getCurrentTime()!=0) {
                    if (startBtuStatus) {
                        mTimer.startTimer();
                        startBtu.setBackgroundResource(R.drawable.circle_pause);
                        startBtu.setText(R.string.pause);
                        if(isWork) {
                            mTimer.setHintText(getString(R.string.working));
                        }else {
                            mTimer.setHintText(getString(R.string.resting));
                        }
                        startBtuStatus = false;
                    } else {
                        mTimer.pauseTimer();
                        startBtu.setBackgroundResource(R.drawable.circle_start);
                        startBtu.setText(R.string.start);
                        mTimer.setHintText(getString(R.string.pause_tips));
                        startBtuStatus = true;
                    }
                }
            }
        });

        Button stopBtu = findViewById(R.id.stop_button);
        stopBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mTimer.getTimerEnd()) {
                    isWork=true;
                    mFrequency=1;
                    mTimer.stopTimer();
                    startBtu.setBackgroundResource(R.drawable.circle_start);
                    startBtu.setText(R.string.start);
                    mTimer.setHintText("");
                    startBtuStatus = true;
                }
            }
        });
        Button timerBtu = findViewById(R.id.timer_button);
        timerBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialog = DialogPlus.newDialog(TimerActivity.this)
                        .setGravity(Gravity.BOTTOM)
                        .setCancelable(true)
                        .setContentHolder(new ViewHolder(R.layout.layout_bottom_dialog))
                        .setOnDismissListener(new OnDismissListener() {//对话框关闭监听
                            @Override
                            public void onDismiss(DialogPlus dialog) {

                            }
                        })
//                        .setOnItemClickListener(new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//                                Toast.makeText(MainActivity.this,"你点击了bottom dialog",Toast.LENGTH_SHORT).show();
//                            }
//                        })
                        //.setExpanded(true)  //这将启用扩展功能,(类似于Android L共享对话框)
                        .create();
                Button ConfirmBtu= (Button) dialog.findViewById(R.id.confirm_button);
                ConfirmBtu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWorkTime=WorkTime.getValue();
                        editor.putInt("workTime",mWorkTime);
                        mRestTime=RestTime.getValue();
                        editor.putInt("restTime",mRestTime);
                        mFrequency=Frequency.getValue();
                        editor.putInt("frequency",mFrequency);
                        editor.putBoolean("vibrate",vibrate.isChecked());
                        editor.putBoolean("ring",ring.isChecked());
                        editor.commit();

                        PomodoroStart(mWorkTime,mRestTime,mFrequency);
                        dialog.dismiss();
                    }
                });

                WorkTime=(NumberPicker) dialog.findViewById(R.id.work_time);
                WorkTime.setMaxValue(60);
                WorkTime.setMinValue(1);
                WorkTime.setValue(sharedPreferences.getInt("workTime", 25));
                WorkTime.setWrapSelectorWheel(false);//设置不循环显示

                RestTime=(NumberPicker) dialog.findViewById(R.id.rest_time);
                RestTime.setMaxValue(20);
                RestTime.setMinValue(1);
                RestTime.setValue(sharedPreferences.getInt("restTime",5));
                RestTime.setWrapSelectorWheel(false);

                Frequency=(NumberPicker) dialog.findViewById(R.id.frequency);
                Frequency.setMaxValue(10);
                Frequency.setMinValue(1);
                Frequency.setValue(sharedPreferences.getInt("frequency",4));
                Frequency.setWrapSelectorWheel(false);

                vibrate=(SwitchMaterial) dialog.findViewById(R.id.vibrate);
                vibrate.setChecked(sharedPreferences.getBoolean("vibrate",true));

                ring=(SwitchMaterial) dialog.findViewById(R.id.ring);
                ring.setChecked(sharedPreferences.getBoolean("ring",false));

                dialog.show();
            }
        });
//        Button settingsBtu = findViewById(R.id.settings_button);
//        settingsBtu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent=new Intent(TimerActivity.this,SettingsActivity.class);
////                startActivity(intent);
//            }
//        });
        LinearLayout main=findViewById(R.id.main_linearlayout);
    }

    public void PomodoroStart(int mWorkTime,int mRestTime,int mFrequency){
        mTimer.setCurrentTime(mWorkTime*60);
        //MyNotification.OngoingNotification(getApplicationContext(),"test","test","test","test");
    }

    /**
     * 番茄钟初始化
     */
    public void PomodoroInit(){
        mWorkTime=sharedPreferences.getInt("workTime", 25);
        mRestTime=sharedPreferences.getInt("restTime",5);
        mFrequency=sharedPreferences.getInt("frequency",4);
        PomodoroStart(mWorkTime,mRestTime,mFrequency);
    }

    @Override
    public void onTimerStop() {
        Log.d(TAG, "onTimerStop");
        startBtu.setBackgroundResource(R.drawable.circle_start);
        startBtu.setText(R.string.start);
        mTimer.setHintText("");
        startBtuStatus=true;

        if(isWork) {
            isWork=false;
            mFrequency--;
            if (mFrequency != 0) {
                MyNotification.SendNotification(getApplicationContext(),
                        getString(R.string.end_str1) + (sharedPreferences.getInt("frequency", 0)-mFrequency) +
                                getString(R.string.end_str2), "", "", "");
                if(sharedPreferences.getBoolean("ring",false)){//铃声提醒
                    MyNotification.playRing(getApplicationContext());
                }
                if(sharedPreferences.getBoolean("vibrate",false)) {//震动提醒
                    MyNotification.playVibrate(getApplicationContext(), new long[]{0, 120, 60, 120});
                }
                mTimer.setCurrentTime(mRestTime*60);
                mTimer.startTimer();
                startBtu.setBackgroundResource(R.drawable.circle_pause);
                startBtu.setText(R.string.pause);
                mTimer.setHintText(R.string.resting);
                startBtuStatus = false;
            }else {
                isWork=true;
                MyNotification.SendNotification(getApplicationContext(),
                        getString(R.string.all_work_end),
                        "", "", "");
                if(sharedPreferences.getBoolean("ring",false)){
                    MyNotification.playRing(getApplicationContext());
                }
                if(sharedPreferences.getBoolean("vibrate",false)) {
                    MyNotification.playVibrate(getApplicationContext(), new long[]{0, 360, 80, 180});
                }
//                EasyFloatView.updateTimingFloat(isWork);
            }
        }else {
            isWork=true;
            MyNotification.SendNotification(getApplicationContext(),
                    getString(R.string.start_str1) + (sharedPreferences.getInt("frequency", 0)-mFrequency) +
                            getString(R.string.start_str2), "", "", "");
            if(sharedPreferences.getBoolean("ring",false)){
                MyNotification.playRing(getApplicationContext());
            }
            if(sharedPreferences.getBoolean("vibrate",false)) {
                MyNotification.playVibrate(getApplicationContext(), new long[]{0, 120, 60, 120});
            }
            mTimer.setCurrentTime(mWorkTime*60);
            mTimer.startTimer();
            startBtu.setBackgroundResource(R.drawable.circle_pause);
            startBtu.setText(R.string.pause);
            mTimer.setHintText(R.string.working);
            startBtuStatus = false;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG,"onStart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG,"onResume");
    }
    @Override
    public void onRestart(){
        super.onRestart();
        Log.i(TAG,"onRestart()");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG,"onPause()");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG,"onStop()");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }
    @Override
    public void onTimerStart(int currentTime) {
        Log.d(TAG, "onTimerStart");
    }

    @Override
    public void onTimerPause(int currentTime) {
        Log.d(TAG, "onTimerPause");
    }

    /**
     * 计时时间变化时相应的函数
     * @param time
     */
    @Override
    public void onTimerTimingValueChanged(int time) {
        Log.d(TAG, "onTimerTimingValueChanged");
//        EasyFloatView.updateTimingFloat(isWork);
    }

    @Override
    public void onTimerSetValueChanged(int time) {
        Log.d(TAG, "onTimerSetValueChanged");
    }

    @Override
    public void onTimerSetValueChange(int time) {
        Log.d(TAG, "onTimerSetValueChange");
    }
}