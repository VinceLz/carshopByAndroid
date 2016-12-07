package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

import com.car.contractcar.myapplication.R;

public class CarOnlineActivity extends AppCompatActivity {

    private int mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_online);
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        mid = intent.getIntExtra("mid", 1);
    }

    private void initData() {
    }
}
