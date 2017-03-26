package com.car.contractcar.myapplication.fragment;

import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.activity.MainActivity;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.ImageLoad;
import com.car.contractcar.myapplication.common.utils.JsonUtils;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.keepcar.bean.KeepHomeBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macmini2 on 16/11/5.
 */

public class KeepcarFragment extends Fragment {

    @BindView(R.id.keepcar_view_pages_carousel)
    RollPagerView keepcarViewPagesCarousel;
    @BindView(R.id.car_models)
    LinearLayout carModels;
    @BindView(R.id.keepcar_list)
    ListView keepcarList;
    @BindView(R.id.text_search)
    EduSohoIconView textSearch;
    @BindView(R.id.lay_search)
    LinearLayout laySearch;
    private LoadingPage loadingPage;
    private DecimalFormat fnum;
    private Location location;
    private KeepHomeBean keepHomeBean = null;
    private int f;
    private int[] rids = new int[]{R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5};
    private String dd;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        location = activity.getLocation();
        loadingPage.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loadingPage = new LoadingPage(getActivity()) {


            @Override
            public int LayoutId() {
                return R.layout.fragment_keepcar;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(KeepcarFragment.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    String content = resultState.getContent();
                    keepHomeBean = (KeepHomeBean) JsonUtils.json2Bean(resultState.getContent(), KeepHomeBean.class);
                }

                initView();
                refreshView();


            }


            @Override
            protected RequestParams params() {
                return null;
            }

            @Override
            protected String url() {
                if (location != null) {
                    return Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR + "?longitude=" + location.getLongitude() + "&latitude=" + location.getLatitude();
                } else {
                    return Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR;
                }

            }
        };


        return loadingPage;
    }


    private void initView() {
        //设置播放时间间隔
        keepcarViewPagesCarousel.setPlayDelay(2500);
        //设置透明度
        keepcarViewPagesCarousel.setAnimationDurtion(500);


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        keepcarViewPagesCarousel.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.parseColor("#aacccccc")));
    }


    private void refreshView() {
        if (keepHomeBean != null) {
            final List<KeepHomeBean.HomeImageBean> homeImage = keepHomeBean.getHomeImage();
            if (homeImage != null && homeImage.size() > 0) {
                keepcarViewPagesCarousel.setAdapter(new StaticPagerAdapter() {

                    @Override
                    public View getView(ViewGroup container, int position) {
                        SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                        ImageLoad.loadImg(imageView, homeImage.get(position).getImage());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        return imageView;
                    }

                    @Override
                    public int getCount() {
                        return homeImage.size();
                    }
                });
            }


            final List<KeepHomeBean.YcstoreBean> ycstore = keepHomeBean.getYcstore();
            if (ycstore != null && ycstore.size() > 0) {
                keepcarList.setAdapter(new BaseAdapter() {


                    private int score;

                    @Override
                    public int getCount() {
                        return ycstore.size();
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
                            convertView = UIUtils.getXmlView(R.layout.keekcar_shop_list_item);
                            holderView = new ViewHolder(convertView);
                            convertView.setTag(holderView);
                        } else {
                            holderView = (ViewHolder)
                                    convertView.getTag();
                        }
                        final KeepHomeBean.YcstoreBean ycstoreBean = ycstore.get(position);

                        f = Integer.parseInt(ycstore.get(position).getDistance() != null ?
                                ycstore.get(position).getDistance() : "0");
                        if (f < 1000) {
                            holderView.keepcarShopDistance.setText(f + "m");
                        } else {
                            float a = (float) f / 1000;
                            fnum = new DecimalFormat("##.0");
                            dd = fnum.format(a);
                            holderView.keepcarShopDistance.setText(dd + "Km");
                        }

                        holderView.keepcarShopName.setText(ycstoreBean.getMbname());
                        holderView.keepcarShopAdderss.setText(ycstoreBean.getBaddress());
                        holderView.keepcarShopText.setText(ycstoreBean.getPurchase() + "人购买 " + ycstoreBean.getCommentcount() + "条评论");
                        score = ycstoreBean.getScore();
                        if (score < 0 || score > 5) {
                            score = 0;
                        }

                        for (int i = score; i < 5; i++) {
                            convertView.findViewById(rids[i]).setVisibility(View.INVISIBLE);
                        }
                        if (score == 0) {
                            holderView.textView3.setText(score + " 分");
                        }else {
                            holderView.textView3.setText(score + ".0 分");
                        }

                        if (!TextUtils.isEmpty(ycstoreBean.getTitle1())) {
                            holderView.keepcarShopTitle1Text.setText(ycstoreBean.getTitle1());
                        } else {
                            holderView.keepcarShopTitle1Text.setVisibility(View.INVISIBLE);
                        }
                        if (!TextUtils.isEmpty(ycstoreBean.getTitle2())) {
                            holderView.keepcarShopTitle2Text.setText(ycstoreBean.getTitle2());
                        } else {
                            holderView.keepcarShopTitle2Text.setVisibility(View.INVISIBLE);
                        }

                        ImageLoad.loadImg(holderView.keepcarShopImg, ycstoreBean.getBshowimage());


                        //电话

                        holderView.keepcarShopCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Constant.intent.setData(Uri.parse("tel:" + ycstoreBean.getBphone()));
                                startActivity(Constant.intent);
                            }
                        });
                        return convertView;
                    }
                });
            }
        }
    }

    static class ViewHolder {
        @BindView(R.id.keepcar_shop_img)
        SimpleDraweeView keepcarShopImg;
        @BindView(R.id.keepcar_shop_name)
        TextView keepcarShopName;
        @BindView(R.id.textView3)
        TextView textView3;
        @BindView(R.id.keepcar_shop_score)
        LinearLayout keepcarShopScore;
        @BindView(R.id.keepcar_shop_adderss)
        TextView keepcarShopAdderss;
        @BindView(R.id.keepcar_shop_distance)
        TextView keepcarShopDistance;
        @BindView(R.id.keepcar_shop_text)
        TextView keepcarShopText;
        @BindView(R.id.keepcar_shop_location)
        ImageView keepcarShopLocation;
        @BindView(R.id.keepcar_shop_call)
        ImageView keepcarShopCall;
        @BindView(R.id.keepcar_shop_title1_text)
        TextView keepcarShopTitle1Text;
        @BindView(R.id.keepcar_shop_title1_ly)
        LinearLayout keepcarShopTitle1Ly;
        @BindView(R.id.keepcar_shop_title2_text)
        TextView keepcarShopTitle2Text;
        @BindView(R.id.keepcar_shop_title2_ly)
        LinearLayout keepcarShopTitle2Ly;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
