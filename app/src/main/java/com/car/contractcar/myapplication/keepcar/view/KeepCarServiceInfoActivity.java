package com.car.contractcar.myapplication.keepcar.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.bean.KeepCarShopInfo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeepCarServiceInfoActivity extends AppCompatActivity implements IKeepCarServiceInfoView {


    @BindView(R.id.keep_car_server_showimg)
    RollPagerView keepCarServerShowimg;
    @BindView(R.id.car_info_back)
    ImageView carInfoBack;
    @BindView(R.id.keep_car_server_shopname)
    TextView keepCarServerShopname;
    @BindView(R.id.keep_car_server_time)
    TextView keepCarServerTime;
    @BindView(R.id.keep_car_server_address)
    TextView keepCarServerAddress;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.count_text)
    TextView countText;
    @BindView(R.id.keepcar_shop_score)
    LinearLayout keepcarShopScore;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View xmlView;
    private int mbid;
    private int[] rids = new int[]{R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5};
    private KeepCarShopInfo keepCarShopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_car_service_info);
        ButterKnife.bind(this);

        initView();
        initData();

        vpView.setAdapter(new MyPagerAdapter(mViewList));
    }

    private void initView() {
        keepCarServerShowimg.setPlayDelay(2500);
        //设置透明度
        keepCarServerShowimg.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        keepCarServerShowimg.setHintView(new ColorPointHintView(this, Color.WHITE, Color.parseColor("#aacccccc")));

        mbid = getIntent().getIntExtra("mbid", 1);
        //添加页卡视图
        xmlView = UIUtils.getXmlView(R.layout.service_list);
        for (int i = 0; i < 3; i++) {
            mViewList.add(xmlView);
        }

        mTitleList.add("清洗");
        mTitleList.add("保养");
        mTitleList.add("装潢");

    }


    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR_SHOP_INFO + mbid, new HttpUtil.callBlack() {
            @Override
            public void succcess(final String code) {
                UIUtils.runOnUIThread(new Runnable() {


                    @Override
                    public void run() {
                        keepCarShopInfo = (KeepCarShopInfo) JsonUtils.json2Bean(code, KeepCarShopInfo.class);
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

        if (keepCarShopInfo != null && keepCarShopInfo.getYcstore() != null) {
            keepCarServerShowimg.setAdapter(new StaticPagerAdapter() {

                @Override
                public View getView(ViewGroup container, int position) {
                    SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                    // HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(selectData.getImage().get(position).getImage())).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ImageLoad.loadImg(imageView, keepCarShopInfo.getYcstore().getBimage().get(position));
                    return imageView;
                }

                @Override
                public int getCount() {
                    return keepCarShopInfo.getYcstore().getBimage() != null ? keepCarShopInfo.getYcstore().getBimage().size() : 0;
                }
            });

            KeepCarShopInfo.YcstoreBean ycstore = keepCarShopInfo.getYcstore();
            keepCarServerShopname.setText(ycstore.getMbname());
            keepCarServerTime.setText("营业时间 : " + ycstore.getTime());
            keepCarServerAddress.setText(ycstore.getBaddress());
            countText.setText("用户评价(" + ycstore.getCommentcount() + ")");
            int score = ycstore.getScore();
            for (int i = score; i < 5; i++) {
                this.findViewById(rids[i]).setVisibility(View.INVISIBLE);
            }
            if (score == 0) {
                textView3.setText(score + " 分");
            } else {
                textView3.setText(score + ".0 分");
            }

        }

    }


    @Override
    public void loadData() {

    }

    //
    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}
