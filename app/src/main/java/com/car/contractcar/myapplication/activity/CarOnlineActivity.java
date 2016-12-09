package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.ui.EduSohoIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarOnlineActivity extends AppCompatActivity {

    @BindView(R.id.car_online_back)
    EduSohoIconView carOnlineBack;
    private int mid;

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
    }

    private void initData() {
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
