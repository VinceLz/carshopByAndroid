package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarConfActivity extends AppCompatActivity {

    private static final String TAG = "xuzi";
    @BindView(R.id.car_conf_view_group)
    LinearLayout carConfViewGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_conf);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initDate() {

    }

    private void initView() {
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        JSONObject jsonObject = (JSONObject) JSON.parse(code);
        JSONObject model = jsonObject.getJSONObject("model");
        JSONObject configure = (JSONObject) model.get("configure");
        HashMap<String, HashMap<String, String>> configureMap = (HashMap<String, HashMap<String, String>>) JsonUtils.json2Bean(JSONObject.toJSONString(configure), HashMap.class);
        if (configureMap != null) {
            for (final String key_1 : configureMap.keySet()) {
                final HashMap<String, String> stringStringHashMap = configureMap.get(key_1);
                Set<String> keySet = stringStringHashMap.keySet();
                final List<String> keys_2 = new ArrayList<>();
                final List<String> values = new ArrayList<>();
                for (String s : keySet) {
                    keys_2.add(s);
                    values.add(stringStringHashMap.get(s));
                }
                ListView listView = new ListView(this);
                listView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return stringStringHashMap == null ? 0 : stringStringHashMap.size();
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
                        if (position == 0) {
                            TextView textView = new TextView(CarConfActivity.this);
                            textView.setText(key_1);
                            return textView;
                        }


                        TextView textView = new TextView(CarConfActivity.this);
                        textView.setText(keys_2.get(position) + ":" + values.get(position));
                        return textView;


                    }
                });


                carConfViewGroup.addView(listView);
            }
        }
    }

}
