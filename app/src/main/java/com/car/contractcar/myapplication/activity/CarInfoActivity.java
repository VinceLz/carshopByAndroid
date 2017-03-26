package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.car.contractcar.myapplication.common.ui.ListViewUtlis;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.entity.CarInfo;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private Intent intent;
    private List<CarInfo.CarBean.ChildsBean> datas;
    private LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        gid = intent.getIntExtra("gid", 1);
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

        dialog = new LoadingDialog(this, "玩命加载中...");
        dialog.show();
        viewPagesShopimg.setPlayDelay(2500);
        viewPagesShopimg.setAnimationDurtion(500);
        viewPagesShopimg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));
        shopCarList.setFocusable(false);
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
        dialog.close();
        if (carInfo != null) {


            final CarInfo.CarBean car = carInfo.getCar();
            if (car != null) {
                carInfoName.setText(car.getGname());
                carInfoPrice.setText("指导价 : " + car.getMinprice() + "~" + car.getMaxprice());
                if (!TextUtils.isEmpty(car.getTitle())) {
                    carInfoTitle.setText(car.getTitle());
                } else {
                    carInfoTitle.setVisibility(View.INVISIBLE);
                }

                viewPagesShopimg.setAdapter(new StaticPagerAdapter() {

                    @Override
                    public View getView(ViewGroup container, int position) {
                        SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        ImageLoad.loadImg(imageView, car.getGimage().get(position));
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return car.getGimage() == null ? 0 : car.getGimage().size();
                    }
                });


                datas = car.getChilds();
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
                            ImageLoad.loadImg(viewHolder.carInfoListItemImg, datas.get(position).getMshowImage());
                            viewHolder.carInfoListItemName.setText(car.getGname() + " " + datas.get(position).getMname());
                            viewHolder.carInfoListItemPrice.setText("指导价 : " + datas.get(position).getGprice() + "万");
                            viewHolder.carInfoListItemGuidegprice.setText("参考价 : " + datas.get(position).getGuidegprice() + "万");
                            String title = datas.get(position).getMtitle();
                            if (!TextUtils.isEmpty(title)) {
                                viewHolder.carInfoListItemTitle.setText(title);
                            } else {
                                viewHolder.carInfoListItemTitle.setVisibility(View.INVISIBLE);
                            }

                            LinearLayout onlineBuy = (LinearLayout) convertView.findViewById(R.id.ly_fprice);
                            onlineBuy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(CarInfoActivity.this, FloorPriceActivity.class);
                                    intent1.putExtra("mid", datas.get(position).getMid());
                                    intent1.putExtra("bid", car.getBid());
                                    intent1.putExtra("mname", datas.get(position).getMname());
                                    CarInfoActivity.this.startActivity(intent1);
                                    CarInfoActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                }
                            });

                            return convertView;
                        }
                    });
                }

                ListViewUtlis.setListViewHeight(shopCarList);
            }
        }
    }


    @OnClick(R.id.car_info_back)
    public void back(View view) {
        this.onBackPressed();
    }



    static class ViewHolder {
        @BindView(R.id.car_info_list_item_img)
        SimpleDraweeView carInfoListItemImg;
        @BindView(R.id.car_info_list_item_name)
        TextView carInfoListItemName;
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
