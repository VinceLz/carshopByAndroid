package com.car.contractcar.myapplication.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.activity.SpecActivity;
import com.car.contractcar.myapplication.entity.BuyCarIndex2;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.EduSohoIconView;
import com.car.contractcar.myapplication.ui.LoadingPage;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.JsonUtils;
import com.car.contractcar.myapplication.utils.UIUtils;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

/**
 * Created by macmini2 on 16/11/5.
 */

public class BuycarFragment2 extends Fragment {


    @BindView(R.id.view_pages_carousel)
    RollPagerView mRollViewPager;
    @BindView(R.id.car_list)
    ListView carList;
    @BindView(R.id.sc_test)
    ScrollView scTest;
    @BindView(R.id.text_search)
    EduSohoIconView textSearch;
    @BindView(R.id.lay_search)
    LinearLayout laySearch;
    @BindView(R.id.select_ok)
    LinearLayout selectOk;
    @BindView(R.id.htext)
    HTextView hTextView;
    private BuyCarIndex2 buyCArIndex2;
    private List<BuyCarIndex2.HomeImageBean> homeImage;
    private List<BuyCarIndex2.CarstoreBean> homeCarstore;
    private List<BuyCarIndex2.HomeActiveBean> homeActive;
    private LoadingPage loadingPage;
    private int statusBarHeight2;
    private static int count = 0;
    private boolean isContinue;


    private Thread myThread;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    hTextView.animateText(homeActive.get(count % homeActive.size()).getTitle());
                    count++;
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private Location location;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        location = activity.getLocation();
        loadingPage.show();

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_index2;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BuycarFragment2.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    buyCArIndex2 = (BuyCarIndex2) JsonUtils.json2Bean(resultState.getContent(), BuyCarIndex2.class);
                    initView();
                    refreshView();
                }
            }

            @Override
            protected RequestParams params() {

                return null;
            }

            @Override
            protected String url() {
                if (location != null) {
                    return Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + location.getLongitude() + "&latitude=" + location.getLatitude();
                } else {
                    return Constant.HTTP_BASE + Constant.HTTP_HOME;
                }

            }
        };


        return loadingPage;
    }

    public String TAG = "TAG";

    /**
     * 初始化ui
     */
    private void initView() {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2500);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.parseColor("#aacccccc")));

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtils.Toast(homeCarstore.get(position).getBname() + "", false);
            }
        });

        statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("WangJ", "状态栏-方法2:" + statusBarHeight2);


        scTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] position = new int[2];
                mRollViewPager.getLocationOnScreen(position);
                position[1] = position[1] - statusBarHeight2;
                Log.e(TAG, "getLocationOnScreen: " + position[1]);
                int[] position1 = new int[2];
//                mRollViewPager.getLocationInWindow(position1);
//                Log.e(TAG, "getLocationInWindow: "+position1[0]+":"+position1[1]);
                int a = 0 - position[1] / 2 > 255 ? 255 : 0 - position[1] / 2;
                Log.e(TAG, "getLocationOnScreen: " + a);
                laySearch.setBackgroundColor(Color.argb(0 - position[1] / 2 > 255 ? 255 : 0 - position[1] / 2, 255, 0, 0));
                return false;
            }
        });

        hTextView.setAnimateType(HTextViewType.ANVIL);

    }

    /**
     * 刷新UI
     */
    private void refreshView() {
        if (buyCArIndex2 != null) {
            //设置适配器
            homeImage = buyCArIndex2.getHomeImage();
            homeCarstore = buyCArIndex2.getCarstore();
            homeActive = buyCArIndex2.getHomeActive();

            mRollViewPager.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(container.getContext());
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(homeImage.get(position).getImage())).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return homeImage.size();
                }
            });

            carList.setAdapter(new MyListAdapter());

            setListViewHeight(carList);
            isContinue = true;
            // 每隔一秒 发一个空消息 滚动一次
            myThread = new Thread() {
                public void run() {
                    while (isContinue) {
                        SystemClock.sleep(2500);
                        handler.sendEmptyMessage(0);// 每隔一秒 发一个空消息 滚动一次
                    }
                }
            };
            myThread.start();
        }
    }


    /**
     * 重新计算listview的高度
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @OnClick(R.id.select_ok)
    public void selectProgramme(View view) {
        //UIUtils.Toast("测试", false);
        startActivity(new Intent(getActivity(), SpecActivity.class));
        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }


    /**
     * listView的适配器
     */
    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return homeCarstore.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holderView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.car_item, null);
                holderView = new ViewHolder(convertView);
                convertView.setTag(holderView);
            } else {
                holderView = (ViewHolder) convertView.getTag();
            }
            holderView.storeAddress.setText(homeCarstore.get(position).getBaddress());
            holderView.storeName.setText(homeCarstore.get(position).getBname());
            holderView.carOwner.setText("主营车型 : " + homeCarstore.get(position).getMajorbusiness());
            float a = (float) (Integer.parseInt(homeCarstore.get(position).getDistance())/1000000);
            DecimalFormat fnum  =   new  DecimalFormat("##.0");
            String   dd=fnum.format(a);
            holderView.distance.setText(dd + "Km");
            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(homeCarstore.get(position).getBimage())).into(holderView.carImg);
            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.car_img)
        ImageView carImg;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_address)
        TextView storeAddress;
        @BindView(R.id.car_owner)
        TextView carOwner;
        @BindView(R.id.distance)
        TextView distance;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 在销毁的时候干掉线程
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isContinue = false;
        if (myThread != null && myThread.isAlive()) {
            myThread.interrupt();
        }
        count = 0;
    }
}
