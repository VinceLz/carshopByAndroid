package com.car.contractcar.myapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.fragment.BuycarFragment2;
import com.car.contractcar.myapplication.fragment.FindFragment;
import com.car.contractcar.myapplication.fragment.KeepcarFragment;
import com.car.contractcar.myapplication.fragment.MeFragment;
import com.car.contractcar.myapplication.utils.UIUtils;

import java.util.List;


public class MainActivity extends FragmentActivity {


    private static final String TAG = "XUZI";
    private FragmentTransaction ft;

    private BuycarFragment2 buycarFragment;
    private KeepcarFragment keepcarFragment;
    private FindFragment findFragment;
    private MeFragment meFragment;
    private FrameLayout content;
    private ImageView iv_buycar;
    private TextView tv_buycar;
    private LinearLayout ll_buycar;
    private ImageView iv_keepcar;
    private TextView tv_keepcar;
    private LinearLayout ll_keepcar;
    private ImageView iv_find;
    private TextView tv_find;
    private LinearLayout ll_find;
    private ImageView iv_me;
    private TextView tv_me;
    private LinearLayout ll_me;

    private LocationManager locationManager;
    private String locationProvider;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setSelect(0);

        initLocation();

    }

    private void initLocation() {
        //获取地理位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            //showLocation(location);
            setLocation(location);
            Log.e(TAG, "onActivityCreated: " + "维度：" + location.getLatitude() + "经度：" + location.getLongitude());
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }


    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_buycar:
                setSelect(0);
                break;
            case R.id.ll_keepcar:
                setSelect(1);
                break;
            case R.id.ll_find:
                setSelect(2);
                break;
            case R.id.ll_me:
                setSelect(3);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        reSetTab();
        hideFragment();
        switch (i) {
            case 0:
                //购车
                if (buycarFragment == null) {
                    buycarFragment = new BuycarFragment2();
                    ft.add(R.id.content, buycarFragment);
                }
                iv_buycar.setImageResource(R.mipmap.bid01);
                tv_buycar.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(buycarFragment);
                break;
            case 1:
                //养车
                if (keepcarFragment == null) {
                    keepcarFragment = new KeepcarFragment();
                    ft.add(R.id.content, keepcarFragment);
                }
                iv_keepcar.setImageResource(R.mipmap.bid03);
                tv_keepcar.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(keepcarFragment);
                break;
            case 2:
                //发现
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    ft.add(R.id.content, findFragment);
                }
                iv_find.setImageResource(R.mipmap.bid07);
                tv_find.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(findFragment);
                break;
            case 3:

                //我
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    ft.add(R.id.content, meFragment);
                }
                iv_me.setImageResource(R.mipmap.bid05);
                tv_me.setTextColor(Color.parseColor("#18B4ED"));
                ft.show(meFragment);
                break;
        }
        ft.commit();
    }

    private void reSetTab() {
        iv_buycar.setImageResource(R.mipmap.bid02);
        iv_keepcar.setImageResource(R.mipmap.bid04);
        iv_find.setImageResource(R.mipmap.bid08);
        iv_me.setImageResource(R.mipmap.bid06);
        tv_buycar.setTextColor(Color.parseColor("#878787"));
        tv_keepcar.setTextColor(Color.parseColor("#878787"));
        tv_find.setTextColor(Color.parseColor("#878787"));
        tv_me.setTextColor(Color.parseColor("#878787"));
    }

    private void hideFragment() {
        if (buycarFragment != null) {
            ft.hide(buycarFragment);
        }
        if (keepcarFragment != null) {
            ft.hide(keepcarFragment);
        }
        if (findFragment != null) {
            ft.hide(findFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        iv_buycar = (ImageView) findViewById(R.id.iv_buycar);
        tv_buycar = (TextView) findViewById(R.id.tv_buycar);
        ll_buycar = (LinearLayout) findViewById(R.id.ll_buycar);
        iv_keepcar = (ImageView) findViewById(R.id.iv_keepcar);
        tv_keepcar = (TextView) findViewById(R.id.tv_keepcar);
        ll_keepcar = (LinearLayout) findViewById(R.id.ll_keepcar);
        iv_find = (ImageView) findViewById(R.id.iv_find);
        tv_find = (TextView) findViewById(R.id.tv_find);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        tv_me = (TextView) findViewById(R.id.tv_me);
        ll_me = (LinearLayout) findViewById(R.id.ll_me);
        ll_buycar.setOnClickListener(MyLayoutCreator);
        ll_find.setOnClickListener(MyLayoutCreator);
        ll_keepcar.setOnClickListener(MyLayoutCreator);
        ll_me.setOnClickListener(MyLayoutCreator);
    }

    View.OnClickListener MyLayoutCreator = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeTab(view);
        }
    };

    private static int count = 0;
    long firClick;
    long secClick;

    @Override
    public void onBackPressed() {
        count++;
        if (count == 1) {
            firClick = System.currentTimeMillis();
            UIUtils.Toast("再次点击退出行吧", false);

        } else if (count == 2) {
            secClick = System.currentTimeMillis();
            if (secClick - firClick < 1500) {
                //双击事件
                super.onBackPressed();
            }
            count = 0;
            firClick = 0;
            secClick = 0;
        }
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            MainActivity.this.setLocation(location);
            Log.e(TAG, "onActivityCreated: " + "维度：" + location.getLatitude() + "经度：" + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * activity销毁时移除监听器
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }
    }
}
