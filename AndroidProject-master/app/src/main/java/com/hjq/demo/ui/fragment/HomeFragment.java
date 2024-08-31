package com.hjq.demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.demo.R;
import com.hjq.demo.app.AppFragment;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.bean.City;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.adapter.CityInfoAdapter;
import com.hjq.demo.ui.adapter.TabAdapter;
import com.hjq.demo.widget.XCollapsingToolbarLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 首页 Fragment
 */
public final class HomeFragment extends Fragment {

    private XCollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    private TextView mAddressView;
    private TextView mHintView;
    private AppCompatImageView mSearchView;

    private RecyclerView mTabView;
    private ViewPager mViewPager;

    private TabAdapter mTabAdapter;
    private Handler handler;
    private TextView textView,textView1,textView2,textView3,textView4;
    private  String weaurl = "http://t.weather.sojson.com/api/weather/city/101120801";
    private List<City.dataBean.forecast> list;
    private City.cityInfoBean cityInfoBean;
    private City.dataBean dataBean;
    private String weaurll;
    private ListView listView;
    private CityInfoAdapter cityInfoAdapter;
    private EditText editText;
    private List<String> his = new ArrayList<>();
    private ImageButton button;
    private ArrayAdapter arrayAdapter;
    private ImageView imageView;
    String id;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initView(view);
        Defult(weaurl);
        return view;
    }


    public void initView(View view) {
        textView = view.findViewById(R.id.tv_city);
        textView1 = view.findViewById(R.id.tv_wendu);
        textView2 = view.findViewById(R.id.tv_pm);
        textView3 = view.findViewById(R.id.tv_zhiling);
        listView = view.findViewById(R.id.listview_cityinfo);
        weaurll = weaurl+id;
    }
    public void Defult(String url) {
        new Thread() {
            @Override
            public void run() {
                Connection(url);
            }
        }.start();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        list = Json(msg.obj.toString()).getDataList().getForecasts();
                        cityInfoBean = Json(msg.obj.toString()).getCityinfos();
                        dataBean = Json(msg.obj.toString()).getDataList();
                        Show();
                        break;
                    default:
                        break;
                }
            }
        };
    }
    public void Show(){
        cityInfoAdapter = new CityInfoAdapter(getActivity(),list);
        listView.setAdapter(cityInfoAdapter);
        textView.setText(cityInfoBean.getParent()+"省"+cityInfoBean.getCity());
        textView1.setText(dataBean.getWendu()+"度");
        textView2.setText("pm25: "+dataBean.getPm25()+"/ pm10: "+dataBean.getPm10());
        textView3.setText("空气质量: "+dataBean.getQuality());
    }
    public void Connection(String urll) {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(urll);
                    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputstream = connection.getInputStream();
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
                    StringBuilder respone = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        respone.append(line);
                    }
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = respone.toString();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public City Json(String data){
        Gson gson = new Gson();//Parse the data
        City city = gson.fromJson(data,City.class);
        return city;
    }



    /* @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
       /* mCollapsingToolbarLayout = findViewById(R.id.ctl_home_bar);
        mToolbar = findViewById(R.id.tb_home_title);

        mAddressView = findViewById(R.id.tv_home_address);
        mHintView = findViewById(R.id.tv_home_hint);
        mSearchView = findViewById(R.id.iv_home_search);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);

        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表演示");
        mPagerAdapter.addFragment(BrowserFragment.newInstance("https://github.com/getActivity"), "网页演示");
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mTabAdapter = new TabAdapter(getAttachActivity());
        mTabView.setAdapter(mTabAdapter);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);*/

   /* @Override
    protected void initData() {
        mTabAdapter.addItem("列表演示");
        mTabAdapter.addItem("网页演示");
        mTabAdapter.setOnTabListener(this);
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * {@link TabAdapter.OnTabListener}
     */

   /* @Override
    public boolean onTabSelected(RecyclerView recyclerView, int position) {
        mViewPager.setCurrentItem(position);
        return true;
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

   /* @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if (mTabAdapter == null) {
            return;
        }
        mTabAdapter.setSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * CollapsingToolbarLayout 渐变回调
     *
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
   /* @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        getStatusBarConfig().statusBarDarkFont(shown).init();
        mAddressView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black : R.color.white));
        mHintView.setBackgroundResource(shown ? R.drawable.home_search_bar_gray_bg : R.drawable.home_search_bar_transparent_bg);
        mHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black60 : R.color.white60));
        mSearchView.setSupportImageTintList(ColorStateList.valueOf(getColor(shown ? R.color.common_icon_color : R.color.white)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }*/
}