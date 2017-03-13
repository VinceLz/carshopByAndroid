package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.CarDetail;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.ui.LoadingDialog;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarDetailActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.car_detail_img)
    RollPagerView carDetailImg;
    @BindView(R.id.car_detail_name)
    TextView carDetailName;
    @BindView(R.id.car_detail_price)
    TextView carDetailPrice;
    @BindView(R.id.car_detail_title)
    TextView carDetailTitle;
    @BindView(R.id.car_detail_phone)
    ImageView carDetailPhone;
    @BindView(R.id.car_detail_back)
    ImageView carDetailBack;
    @BindView(R.id.car_detail_hscrollview)
    LinearLayout carDetailHscrollview;
    @BindView(R.id.car_detail_online)
    ImageView carDetailOnline;
    @BindView(R.id.activity_car_detail)
    RelativeLayout activityCarDetail;
    @BindView(R.id.car_detail_fprice)
    ImageView carDetailFprice;
    @BindView(R.id.conf_btn)
    Button confBtn;
    private int mid;
    private CarDetail carDetail;
    private CarDetail.CarBean car;
    private String mName;
    private int bid;
    private View xmlView;
    private LoadingDialog dialog = null;
    private LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xmlView = UIUtils.getXmlView(R.layout.activity_car_detail);
        setContentView(xmlView);
        ButterKnife.bind(this);
        initView();
        initData();
        Log.e(TAG, "onCreate:xxxx ");

    }


    private void initView() {
        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 1);
        bid = intent.getIntExtra("bid", 1);
        //设置播放时间间隔
        dialog = new LoadingDialog(this, "玩命加载中...");
        dialog.show();
        carDetailImg.setPlayDelay(2500);
        //设置透明度
        carDetailImg.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        carDetailImg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));
    }

    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_DETAIL + mid, new HttpUtil.callBlack() {//// TODO: 16/12/24    数据哭数据太少 所以写死了mid为1
            @Override
            public void succcess(String code) {
                carDetail = null;
                carDetail = (CarDetail) JsonUtils.json2Bean(code, CarDetail.class);
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


    private void refreshView() {
        dialog.dismiss();
        if (carDetail != null) {
            car = carDetail.getCar();

            carDetailImg.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(container.getContext());
                    Picasso.with(context).load(HttpUtil.getImage_path(car.getMimage().get(position))).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return car.getMimage() != null ? car.getMimage().size() : 0;
                }
            });


            carDetailName.setText(car.getMname());
            carDetailPrice.setText("价格 : " + car.getGuidegprice() + "万");
            if (!TextUtils.isEmpty(car.getMtitle())) {
                carDetailTitle.setText(car.getMtitle());
            } else {
                carDetailTitle.setVisibility(View.INVISIBLE);
            }
            Log.e(TAG, "refreshView: ");
            List<CarDetail.RecommendBean> recommend = carDetail.getRecommend();

            carDetailHscrollview.removeAllViews();
            if (recommend != null && recommend.size() > 0) {
                //适配横向滑动的scrollview的数据
                for (final CarDetail.RecommendBean recommendBean : recommend) {
                    LinearLayout recommendItemView = (LinearLayout) UIUtils.getXmlView(R.layout.car_detail_recommend_item);
                    ImageView recommendItemImg = (ImageView) recommendItemView.findViewById(R.id.car_detail_recommend_item_img);
                    TextView recommendItemTitle = (TextView) recommendItemView.findViewById(R.id.car_detail_recommend_item_title);
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(recommendBean.getMshowImage())).into(recommendItemImg);
                    recommendItemTitle.setText(recommendBean.getMname() + "\n" + "指导价 : " + recommendBean.getGuidegprice() + "万");
                    carDetailHscrollview.addView(recommendItemView);

                    recommendItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mid = recommendBean.getMid();
                            dialog.show();
                            initData();
                        }
                    });
                }

            }


        }
    }

    @OnClick(R.id.car_detail_back)
    public void onBack(View view) {
        this.onBackPressed();

    }

    @OnClick(R.id.conf_btn)
    public void onConf(View view) {
        loadingDialog = new LoadingDialog(this, "配置加载中...");
        loadingDialog.show();
        HttpUtil.get("http://59.110.5.105/carshop/car/models/getconf.action?mid=" + mid, new HttpUtil.callBlack() {
            @Override
            public void succcess(final String code) {

                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.close();
                        Intent intent = new Intent(CarDetailActivity.this, CarConfActivity.class);
                        intent.putExtra("code", code);
                        startActivity(intent);
                        CarDetailActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
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

    @OnClick(R.id.car_detail_online)
    public void onLien(View view) {
        Intent intent = new Intent(this, CarOnlineActivity.class);
        intent.putExtra("mid", car.getMid());
        intent.putExtra("mname", car.getMname());
        intent.putExtra("image", car.getMimage().get(0));
        startActivity(intent);
        //    右往左推出效果
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.car_detail_phone)
    public void onPhone() {

    }

    @OnClick(R.id.car_detail_fprice)
    public void onFprice() {
        Intent intent = new Intent(this, FloorPriceActivity.class);
        intent.putExtra("mid", mid);
        intent.putExtra("mname", car.getMname());
        intent.putExtra("bid", car.getBid());
        startActivity(intent);
        //    右往左推出效果
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onBackPressed() {
        loadingDialog = null;
        dialog = null;

        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
