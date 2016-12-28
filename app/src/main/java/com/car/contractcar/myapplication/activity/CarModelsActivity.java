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
import android.widget.ScrollView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.SelectData;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.EduSohoIconView;
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

public class CarModelsActivity extends AppCompatActivity {

    private static final String TAG = "XUZI";
    @BindView(R.id.car_models_pages_carousel)
    RollPagerView carModelsPagesCarousel;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.car_models_ok)
    LinearLayout carModelsOk;
    @BindView(R.id.car_models_list)
    ListView carModelsList;
    @BindView(R.id.sc_test)
    ScrollView scTest;
    @BindView(R.id.activity_car_models)
    LinearLayout activityCarModels;
    @BindView(R.id.car_models_back)
    EduSohoIconView carModelsBack;
    private SelectData selectData;
    private List<SelectData.ResultBean> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models);
        ButterKnife.bind(this);
        initView();
        initData();

        carModelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CarModelsActivity.this, CarInfoActivity.class);
                intent.putExtra("gid", datas.get(position).getGid());
                CarModelsActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    private void initView() {

//设置播放时间间隔
        carModelsPagesCarousel.setPlayDelay(2500);
        //设置透明度
        carModelsPagesCarousel.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        carModelsPagesCarousel.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));
    }

    private void initData() {
        String data = this.getIntent().getStringExtra("data");
        selectData = (SelectData) JsonUtils.json2Bean(data, SelectData.class);
        refreshView();
    }

    private void refreshView() {
        if (selectData != null) {


            carModelsPagesCarousel.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(container.getContext());
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(selectData.getImage().get(position).getImage())).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return selectData.getImage() != null ? selectData.getImage().size() : 0;
                }
            });

            if (selectData.getResult() != null) {
                datas = selectData.getResult();
                Log.e(TAG, "refreshView: " + datas.size());
                carModelsList.setAdapter(new BaseAdapter() {
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
                    public View getView(int position, View convertView, ViewGroup parent) {
                        ViewHolder holderView = null;
                        if (convertView == null) {
                            convertView = UIUtils.getXmlView(R.layout.car_models_list_item);
                            holderView = new ViewHolder(convertView);
                            convertView.setTag(holderView);
                        } else {
                            holderView = (ViewHolder) convertView.getTag();
                        }
                        HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(datas.get(position).getGshowImage())).into(holderView.carModelsListItemImg);
                        holderView.carModelsListItemName.setText(datas.get(position).getGname());
                        holderView.carModelsListItemPrice.setText("指导价 : " + datas.get(position).getMinprice() + "~" + datas.get(position).getMinprice());
                        if (!TextUtils.isEmpty(datas.get(position).getTitle())) {
                            holderView.carModelsListItemTitle.setText(datas.get(position).getTitle());
                        } else {
                            holderView.carModelsListItemTitle.setVisibility(View.INVISIBLE);
                        }
                        return convertView;
                    }
                });
                setListViewHeight(carModelsList);
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


    @OnClick(R.id.car_models_ok)
    public void selectCarModels(View view) {
        startActivity(new Intent(this, SpecActivity.class));
        this.finish();
        this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.car_models_back)
    public void back(View view) {
        this.onBackPressed();
    }

    static class ViewHolder {
        @BindView(R.id.car_models_list_item_img)
        ImageView carModelsListItemImg;
        @BindView(R.id.car_models_list_item_name)
        TextView carModelsListItemName;
        @BindView(R.id.car_models_list_item_price)
        TextView carModelsListItemPrice;
        @BindView(R.id.car_models_list_item_title)
        TextView carModelsListItemTitle;

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
