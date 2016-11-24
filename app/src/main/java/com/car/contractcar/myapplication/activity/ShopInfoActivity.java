package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.ShopInfo;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.LoadingPage;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.JsonUtils;
import com.car.contractcar.myapplication.utils.UIUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.MyApplication.context;

public class ShopInfoActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.view_pages_shopimg)
    RollPagerView viewPagesShopimg;
    @BindView(R.id.shop_car_list)
    ListView shopCarList;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.shop_owner)
    TextView shopOwner;
    @BindView(R.id.shop_distance)
    TextView shopDistance;
    private int bid;
    private ShopInfo shopInfo;
    private List<ShopInfo.BusinessBean.ChildsBean> cars;
    private int distance;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        //设置播放时间间隔
        viewPagesShopimg.setPlayDelay(2500);
        //设置透明度
        viewPagesShopimg.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        viewPagesShopimg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));

    }

    /**
     * 从网络中获取数据
     */
    private void initData() {
        Intent intent = getIntent();
        bid = intent.getIntExtra("bid", 1);
        distance = intent.getIntExtra("distance", 100);
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_SHOP_INFO + bid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                shopInfo = (ShopInfo) JsonUtils.json2Bean(code, ShopInfo.class);
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshView();
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

    /**
     * 刷新UI
     */
    private void refreshView() {
        if (shopInfo.getBusiness() != null) {
            cars = shopInfo.getBusiness().getChilds();
            viewPagesShopimg.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    Log.e(TAG, "getView: " + shopInfo.getBusiness().getBimage().get(position));
                    ImageView imageView = new ImageView(container.getContext());
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(shopInfo.getBusiness().getBimage().get(position))).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return shopInfo.getBusiness().getBimage().size();
                }
            });
            ShopInfo.BusinessBean business = shopInfo.getBusiness();
            shopName.setText(business.getBname());
            shopAddress.setText(business.getBaddress());
            shopOwner.setText("主营车型 : " + business.getMajorbusiness());

            if (distance < 1000) {
                shopDistance.setText(distance + "m");
            } else {
                float a = (float) distance / 1000;
                DecimalFormat fnum = new DecimalFormat("##.0");
                shopDistance.setText(fnum.format(a) + "Km");
            }
            if (cars != null && cars.size() > 0) {
                shopCarList.setAdapter(new MyAdapter());
                setListViewHeight(shopCarList);
            }

        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cars.size();
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = UIUtils.getXmlView(R.layout.shop_cars_list);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(cars.get(position).getGlastimage())).into(viewHolder.shopCarImg);
            viewHolder.shopCarMode.setText(cars.get(position).getModel());
            viewHolder.shopCarName.setText(cars.get(position).getGname());
            viewHolder.shopCarPrice.setText("指导价: " + cars.get(position).getGprice() + "");
            viewHolder.shopCarUidegprice.setText("参考价: " + cars.get(position).getGuidegprice() + "");
            String title = cars.get(position).getTitle();
            if (!TextUtils.isEmpty(title)) {
                viewHolder.shopCarTitle.setText(title);
            } else {
                viewHolder.shopCarTitle.setVisibility(View.INVISIBLE);
            }
            return convertView;
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


    static class ViewHolder {
        @BindView(R.id.shop_car_img)
        ImageView shopCarImg;
        @BindView(R.id.shop_car_name)
        TextView shopCarName;
        @BindView(R.id.shop_car_mode)
        TextView shopCarMode;
        @BindView(R.id.shop_car_price)
        TextView shopCarPrice;
        @BindView(R.id.shop_car_uidegprice)
        TextView shopCarUidegprice;
        @BindView(R.id.shop_car_title)
        TextView shopCarTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}