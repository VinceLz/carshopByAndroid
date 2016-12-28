package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.EduSohoIconView;
import com.car.contractcar.myapplication.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.car.contractcar.myapplication.MyApplication.context;

public class CarOnlineActivity extends AppCompatActivity {

    @BindView(R.id.car_online_back)
    EduSohoIconView carOnlineBack;
    @BindView(R.id.car_online_car_name)
    TextView carOnlineCarName;
    @BindView(R.id.car_online_car_img)
    ImageView carOnlineCarImg;
    private int mid;
    private String gname;
    private String mname;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_online);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 1);
        mname = intent.getStringExtra("mname");
        image = intent.getStringExtra("image");
        carOnlineCarName.setText(mname);
        if (!TextUtils.isEmpty(image)) {
            HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(image)).into(carOnlineCarImg);
        }
    }

    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_CAR_COLOR + mid, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {

            }

            @Override
            public void fail(String code) {

            }

            @Override
            public void err() {

            }
        }, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @OnClick(R.id.car_online_back)
    public void back(View view) {
        this.onBackPressed();
    }

}
