package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.CarDetail;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.JsonUtils;
import com.car.contractcar.myapplication.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarDetailActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.car_detail_img)
    ImageView carDetailImg;
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
    private int mid;
    private CarDetail carDetail;
    private CarDetail.CarBean car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 1);
        Log.e(TAG, "initView: " + Constant.HTTP_BASE + Constant.HTTP_CAR_DETAIL + 1);
    }

    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_DETAIL + 1, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
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
        if (carDetail != null) {
            car = carDetail.getCar();
            carDetailName.setText(car.getGname() + " " + car.getMname());
            carDetailPrice.setText("价格 : " + car.getGuidegprice());
            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(car.getShowImage())).into(carDetailImg);
            if (!TextUtils.isEmpty(car.getTitle())) {
                carDetailTitle.setText(car.getTitle());
            } else {
                carDetailTitle.setVisibility(View.INVISIBLE);
            }
            Log.e(TAG, "refreshView: ");
            List<CarDetail.RecommendBean> recommend = carDetail.getRecommend();

            if (recommend != null && recommend.size() > 0) {
                //适配横向滑动的scrollview的数据
                for (CarDetail.RecommendBean recommendBean : recommend) {
                    LinearLayout recommendItemView = (LinearLayout) UIUtils.getXmlView(R.layout.car_detail_recommend_item);
                    ImageView recommendItemImg = (ImageView) recommendItemView.findViewById(R.id.car_detail_recommend_item_img);
                    TextView recommendItemTitle = (TextView) recommendItemView.findViewById(R.id.car_detail_recommend_item_title);
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(recommendBean.getMimage().split(",")[0])).into(recommendItemImg);
                    recommendItemTitle.setText(recommendBean.getGname() + " " + recommendBean.getMname() + "\n" + "指导价 : " + recommendBean.getGuidegprice() + "\n" + recommendBean.getTitle());
                    carDetailHscrollview.addView(recommendItemView);
                }

            }
        }
    }

    @OnClick(R.id.car_detail_back)
    public void onBack(View view) {
        this.onBackPressed();

    }

    @OnClick(R.id.car_detail_online)
    public void onLien(View view) {
        Intent intent = new Intent(this, CarOnlineActivity.class);
        intent.putExtra("mid", car.getMid());
        startActivity(intent);
        //    右往左推出效果
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @OnClick(R.id.car_detail_phone)
    public void onPhone() {

    }

    @OnClick(R.id.car_detail_phone)
    public void onFprice() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
