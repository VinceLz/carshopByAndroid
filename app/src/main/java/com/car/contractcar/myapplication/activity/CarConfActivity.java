package com.car.contractcar.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.utils.JsonUtils2;
import com.car.contractcar.myapplication.common.utils.UIUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        final String code = intent.getStringExtra("code");


        JSONObject jsonObject = (JSONObject) JSON.parse(code);
        JSONObject model = jsonObject.getJSONObject("model");
        JSONObject configure = (JSONObject) model.get("configure");

        LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = (LinkedHashMap<String, LinkedHashMap<String, String>>) JsonUtils2
                .jsonToPojo(configure.toJSONString(), LinkedHashMap.class);

        if (jsonToPojo != null) {
            for (String key_1 : jsonToPojo.keySet()) {
                final String title = key_1;
                LinkedHashMap<String, String> stringStringHashMap = jsonToPojo.get(key_1);
                final int size = stringStringHashMap == null ? 0 : stringStringHashMap.size();

                Set<String> keySet1 = stringStringHashMap.keySet();
                final List<String> keys_2 = new ArrayList<>();
                final List<String> values = new ArrayList<>();

                for (String s : keySet1) {
                    keys_2.add(s);
                    values.add(stringStringHashMap.get(s));
                }

                ListView listView = new ListView(this);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                listView.setLayoutParams(layoutParams);
                listView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return size;
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
                            textView.setTextSize(UIUtils.px2dp(60));
                            textView.setPadding(80, 5, 0, 5);
                            textView.setTextColor(Color.parseColor("#000000"));
                            textView.setText(title);
                            return textView;
                        }

                        View xmlView = UIUtils.getXmlView(R.layout.conf_list_item);
                        TextView confName = (TextView) xmlView.findViewById(R.id.conf_name);
                        TextView confValue = (TextView) xmlView.findViewById(R.id.conf_value);
                        confName.setText(keys_2.get(position));
                        confValue.setText(values.get(position));
                        return xmlView;
                    }
                });

                setListViewHeight(listView);

                carConfViewGroup.addView(listView);
            }
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

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
    }
}
