package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.CarInfo;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.JsonUtils;
import com.car.contractcar.myapplication.utils.UIUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarInfoActivity extends AppCompatActivity {

    @BindView(R.id.view_pages_shopimg)
    RollPagerView viewPagesShopimg;
    @BindView(R.id.car_info_name)
    TextView carInfoName;
    @BindView(R.id.car_info_price)
    TextView carInfoPrice;
    @BindView(R.id.car_info_title)
    TextView carInfoTitle;
    @BindView(R.id.shop_car_list)
    ListView shopCarList;
    @BindView(R.id.car_info_back)
    ImageView carInfoBack;
    private CarInfo carInfo;
    private int gid;
    private String gname;
    private String title;
    private int minprice;
    private int maxprice;
    private Intent intent;
    private List<CarInfo.CarBean.ChildBean> datas;
    private Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        gid = intent.getIntExtra("gid", 1);
        gname = intent.getStringExtra("gname");
        title = intent.getStringExtra("title");
        minprice = intent.getIntExtra("minprice", 0);
        maxprice = intent.getIntExtra("maxprice", 0);
        initView();
        initData();
        intent = new Intent(CarInfoActivity.this, CarDetailActivity.class);
        final Intent finalIntent = intent;
        shopCarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                finalIntent.putExtra("mid", datas.get(position).getMid());
                startActivity(finalIntent);
                //    右往左推出效果
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }


    private void initView() {
        intent1 = new Intent(this, CarOnlineActivity.class);
        carInfoName.setText(gname);
        carInfoPrice.setText("指导价 : " + minprice + "~" + maxprice);
        carInfoTitle.setText(title);
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

    private void initData() {

        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_INFO + gid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                String url = Constant.HTTP_BASE + Constant.HTTP_CAR_INFO + gid;
                carInfo = (CarInfo) JsonUtils.json2Bean(code, CarInfo.class);
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


        refreshView();
    }

    private void refreshView() {

        if (carInfo != null) {
            final CarInfo.CarBean car = carInfo.getCar();
            if (car != null) {
                viewPagesShopimg.setAdapter(new StaticPagerAdapter() {

                    @Override
                    public View getView(ViewGroup container, int position) {
                        ImageView imageView = new ImageView(container.getContext());
                        HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(car.getGimage().get(position))).into(imageView);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return car.getGimage() == null ? 0 : car.getGimage().size();
                    }
                });


                datas = car.getChild();
                if (datas.size() > 0) {
                    //适配数据
                    shopCarList.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return datas.size();
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
                            ViewHolder viewHolder = null;
                            if (convertView == null) {
                                convertView = UIUtils.getXmlView(R.layout.car_info_list_item);
                                viewHolder = new ViewHolder(convertView);
                                convertView.setTag(viewHolder);
                            } else {
                                viewHolder = (ViewHolder) convertView.getTag();
                            }
                            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(datas.get(position).getMshowImage())).into(viewHolder.carInfoListItemImg);
                            viewHolder.carInfoListItemName.setText(datas.get(position).getMname() + " " + datas.get(position).getMname());
                            viewHolder.carInfoListItemPrice.setText("指导价 : " + datas.get(position).getGprice());
                            viewHolder.carInfoListItemGuidegprice.setText("参考价 : " + datas.get(position).getGuidegprice());
                            String title = datas.get(position).getMtitle();
                            if (!TextUtils.isEmpty(title)) {
                                viewHolder.carInfoListItemTitle.setText(title);
                            } else {
                                viewHolder.carInfoListItemTitle.setVisibility(View.INVISIBLE);
                            }

//                            LinearLayout onlineBuy = (LinearLayout) convertView.findViewById(R.id.car_info_online_buy);
//                            onlineBuy.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    intent1.putExtra("mid", datas.get(position).getMid());
//                                    CarInfoActivity.this.startActivity(intent1);
//                                    CarInfoActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//                                }
//                            });

                            return convertView;
                        }
                    });
                }

                setListViewHeight(shopCarList);
            }
        }
    }


    @OnClick(R.id.car_info_back)
    public void back(View view) {
        this.onBackPressed();
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
        @BindView(R.id.car_info_list_item_img)
        ImageView carInfoListItemImg;
        @BindView(R.id.car_info_list_item_name)
        TextView carInfoListItemName;
        @BindView(R.id.car_info_list_item_models)
        TextView carInfoListItemModels;
        @BindView(R.id.car_info_list_item_price)
        TextView carInfoListItemPrice;
        @BindView(R.id.car_info_list_item_guidegprice)
        TextView carInfoListItemGuidegprice;
        @BindView(R.id.car_info_list_item_title)
        TextView carInfoListItemTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
