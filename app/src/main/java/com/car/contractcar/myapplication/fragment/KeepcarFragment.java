package com.car.contractcar.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.ui.EduSohoIconView;
import com.car.contractcar.myapplication.common.ui.LoadingPage;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.jude.rollviewpager.RollPagerView;
import com.loopj.android.http.RequestParams;

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
    @BindView(R.id.sc_test)
    ScrollView scTest;
    @BindView(R.id.text_search)
    EduSohoIconView textSearch;
    @BindView(R.id.lay_search)
    LinearLayout laySearch;
    private LoadingPage loadingPage;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingPage.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View view = View.inflate(getActivity(), R.layout.fragment_keepcar, null);
////
////        ButterKnife.bind(this, view);
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_keepcar;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(KeepcarFragment.this, successView);


            }

            @Override
            protected RequestParams params() {
                return null;
            }

            @Override
            protected String url() {
                return Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR;
            }
        };
//
//        initView();
//
//        initData();


        return loadingPage;
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


}
