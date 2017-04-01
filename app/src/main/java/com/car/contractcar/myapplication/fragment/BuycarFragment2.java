package com.car.contractcar.myapplication.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.baidu.location.BDLocation;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.CarModelsActivity;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.activity.ShopInfoActivity;
import com.car.contractcar.myapplication.activity.SpecActivity;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.entity.BuyCarIndex2;
import com.facebook.drawee.view.SimpleDraweeView;
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
    @BindView(R.id.car_models)
    LinearLayout carModels;
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
    };

    private String dd;
    private DecimalFormat fnum;
    private int[] position1;
    private int f;
    Location location;
    private BDLocation bdLocation;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        bdLocation = MainActivity.bdLocation;
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
                if (bdLocation != null) {
                    Log.e(TAG, Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude());
                    return Constant.HTTP_BASE + Constant.HTTP_HOME + "?longitude=" + bdLocation.getLongitude() + "&latitude=" + bdLocation.getLatitude();
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final LoadingDialog loadingDialog = new LoadingDialog(getContext(),"加载中...");
                loadingDialog.show();
                HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SHOP_INFO + homeCarstore.get(position).getBid(), new HttpUtil.callBlack() {
                    @Override
                    public void succcess(final String code) {

                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(), ShopInfoActivity.class);
                                // intent.putExtra("bid", homeCarstore.get(position).getBid());
                                intent.putExtra("distance", Integer.parseInt(homeCarstore.get(position).getDistance() != null ?
                                        homeCarstore.get(position).getDistance() : "0"));
                                intent.putExtra("code", code);
                                loadingDialog.dismiss();
                                loadingDialog.close();
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            }
                        });

                    }

                    @Override
                    public void fail(String code) {

                    }

                    @Override
                    public void err() {

                    }
                }, false);


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


        scTest.setOnTouchListener(new View.OnTouchListener() {

            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;

                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            return;

                        } else {

                            mRollViewPager.getLocationOnScreen(position1);
                            position1[1] -= statusBarHeight2;
                            int a = 0 - position1[1] / 2 > 255 ? 255 : 0 - position1[1] / 2;
                            laySearch.setBackgroundColor(Color.argb(0 - position1[1] / 2 > 255 ? 255 : 0 - position1[1] / 2, 255, 255, 255));
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //isScoll = false;
                int eventAction = event.getAction();
                int y = (int) event.getRawY();
                switch (eventAction) {
                    case MotionEvent.ACTION_UP:
                        handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
                        break;
                    default:
                        position1 = new int[2];
                        mRollViewPager.getLocationOnScreen(position1);
                        position1[1] -= statusBarHeight2;
                        int a = 0 - position1[1] / 2 > 255 ? 255 : 0 - position1[1] / 2;
                        laySearch.setBackgroundColor(Color.argb(0 - position1[1] / 2 > 255 ? 255 : 0 - position1[1] / 2, 255, 255, 255));
                        break;
                }
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
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
//                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(homeImage.get(position).getImage())).into(imageView);

                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ImageLoad.loadImg(imageView, homeImage.get(position).getImage());
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
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    @OnClick(R.id.car_models)
    public void carModels(View view) {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SELECT + "/.action", new HttpUtil.callBlack() {
            @Override
            public void succcess(final String code) {
                Log.e(TAG, "succcess: " + code);
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), CarModelsActivity.class);
                        intent.putExtra("data", code);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                });
            }

            @Override
            public void fail(String code) {
                Log.e(TAG, "fail: " + code);
            }

            @Override
            public void err() {
                Log.e(TAG, "err: ");
            }
        }, false);

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
        public View getView(final int position, View convertView, ViewGroup parent) {

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
            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle1())) {
                holderView.homeTitle1Text.setText(homeCarstore.get(position).getTitle1());
            } else {
                holderView.homeTitle1Ly.setVisibility(View.INVISIBLE);
            }
            if (!TextUtils.isEmpty(homeCarstore.get(position).getTitle2())) {
                holderView.homeTitle2Text.setText(homeCarstore.get(position).getTitle2());
            } else {
                holderView.homeTitle2Ly.setVisibility(View.INVISIBLE);
            }


            f = Integer.parseInt(homeCarstore.get(position).getDistance() != null ?
                    homeCarstore.get(position).getDistance() : "0");
            if (f < 1000) {
                holderView.distance.setText(f + "m");
            } else {
                float a = (float) f / 1000;
                fnum = new DecimalFormat("##.0");
                dd = fnum.format(a);
                holderView.distance.setText(dd + "Km");
            }
            ImageLoad.loadImg(holderView.carImg, homeCarstore.get(position).getBshowImage());

            //电话
            ImageView car_call = (ImageView) convertView.findViewById(R.id.car_call);
            car_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.intent.setData(Uri.parse("tel:" + homeCarstore.get(position).getBphone()));
                    startActivity(Constant.intent);
                }
            });

            //位置导航
            ImageView car_location = (ImageView) convertView.findViewById(R.id.car_location);
            car_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.Toast("还未开发", false);// TODO: 16/11/29
                    locationShop(position);
                }
            });


            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.car_img)
        SimpleDraweeView carImg;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_address)
        TextView storeAddress;
        @BindView(R.id.car_owner)
        TextView carOwner;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.car_location)
        ImageView carLocation;
        @BindView(R.id.car_call)
        ImageView carCall;
        @BindView(R.id.home_title1_text)
        TextView homeTitle1Text;
        @BindView(R.id.home_title1_ly)
        LinearLayout homeTitle1Ly;
        @BindView(R.id.home_title2_text)
        TextView homeTitle2Text;
        @BindView(R.id.home_title2_ly)
        LinearLayout homeTitle2Ly;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 百度地图导航
     *
     * @param position
     */
    private void locationShop(int position) {
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
