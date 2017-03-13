package com.car.contractcar.myapplication.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.SpecActivity;
import com.car.contractcar.myapplication.entity.BuyCarIndex;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.ui.MyGridView;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

/**
 * Created by macmini2 on 16/11/5.
 */

public class BuycarFragment extends Fragment {


    @BindView(R.id.view_pages_carousel)
    RollPagerView mRollViewPager;
    @BindView(R.id.car_list)
    ListView carList;
    @BindView(R.id.sc_test)
    ScrollView scTest;
    @BindView(R.id.lay_search)
    LinearLayout laySearch;
    @BindView(R.id.ly_Programme)
    LinearLayout lyProgramme;
    @BindView(R.id.home_active)
    MyGridView homeActiveVIew;

    private BuyCarIndex buyCArIndex;
    int x = 0;
    int y = 0;
    private List<BuyCarIndex.HomeImageBean> homeImage;
    private List<BuyCarIndex.HomeCarBean> homeCar;
    private List<BuyCarIndex.HomeActiveBean> homeActive;
    private LoadingPage loadingPage;
    private int statusBarHeight2;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingPage.show();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        //                View view =  View.inflate(getActivity(), R.layout.fragment_index, null);
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_index;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
//                View view =  View.inflate(getActivity(), R.layout.fragment_index, null);
                ButterKnife.bind(BuycarFragment.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {

                    buyCArIndex = (BuyCarIndex) JsonUtils.json2Bean(resultState.getContent(), BuyCarIndex.class);
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
                return Constant.HTTP_BASE + Constant.HTTP_HOME;
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

        carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//// TODO: 16/11/22  
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIUtils.Toast(homeCar.get(position).getGname() + "", false);
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

    }

    /**
     * 刷新UI
     */
    private void refreshView() {
        if (buyCArIndex != null) {
            //设置适配器
            homeImage = buyCArIndex.getHomeImage();
            homeCar = buyCArIndex.getHomeCar();
            homeActive = buyCArIndex.getHomeActive();

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

            homeActiveVIew.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return homeActive.size();
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
                    View view = UIUtils.getXmlView(R.layout.keep_car_item);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_item);
                    TextView textView = (TextView) view.findViewById(R.id.tv_item);
                    textView.setText(homeActive.get(position).getTitle());
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(homeActive.get(position).getImage())).into(imageView);
                    return view;
                }
            });
            setListViewHeight(carList);
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


    @OnClick(R.id.ly_Programme)
    public void selectProgramme(View view) {
        //UIUtils.Toast("测试", false);
        startActivityForResult(new Intent(getActivity(), SpecActivity.class), 0);
    }

    static class ViewHolder {
        @BindView(R.id.car_item_img)
        ImageView carItemImg;
        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.text_price)
        TextView textPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * listView的适配器
     */
    class MyListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return homeCar.size();
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
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_car, null);
                holderView = new ViewHolder(convertView);
                convertView.setTag(holderView);
            } else {
                holderView = (ViewHolder) convertView.getTag();
            }
            holderView.textName.setText(homeCar.get(position).getGname());
            holderView.textPrice.setText(homeCar.get(position).getGprice() + "");
            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(homeCar.get(position).getGfirstimage())).into(holderView.carItemImg);
            return convertView;
        }
    }


}
