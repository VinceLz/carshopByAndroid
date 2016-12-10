package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.EduSohoIconView;
import com.car.contractcar.myapplication.ui.LineEditText;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FloorPriceActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.fprice_back)
    EduSohoIconView fpriceBack;
    @BindView(R.id.fprice_car_name)
    TextView fpriceCarName;
    @BindView(R.id.fprice_phone)
    LineEditText fpricePhone;
    @BindView(R.id.fprice_name)
    LineEditText fpriceName;
    @BindView(R.id.fprice_ok)
    Button fpriceOk;
    private Intent intent;
    private int bid;
    private int gid;
    private String gname;
    private String model;
    private String bname;
    private String uname;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_price);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        intent = getIntent();
        bid = intent.getIntExtra("bid", -1);
        gid = intent.getIntExtra("gid", -1);
        gname = intent.getStringExtra("gname");
        model = intent.getStringExtra("model");
        bname = intent.getStringExtra("bname");
        fpriceCarName.setText(gname);

    }

    private void getInData() {
        phone = fpricePhone.getText().toString();
        uname = fpriceName.getText().toString();

    }

    @OnClick(R.id.fprice_ok)
    public void fSubmit(View view) {
        getInData();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(uname)) {
            UIUtils.Toast("用户名或密码不能为空", false);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("gid", gid);
            params.put("bid", bid);
            params.put("gname", gname);
            params.put("model", model);
            params.put("bname", bname);
            params.put("phone", phone);
            params.put("uname", uname);
            HttpUtil.post(Constant.HTTP_BASE + "/consult/add.action ", params, new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    Log.e(TAG, "succcess: " + code);
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            FloorPriceActivity.this.finish();
                        }
                    });
                }

                @Override
                public void fail(String code) {

                }

                @Override
                public void err() {

                }
            });
        }
    }

    @OnClick(R.id.fprice_back)
    public void fBack(View view) {
        this.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
