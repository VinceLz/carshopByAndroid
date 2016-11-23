package com.car.contractcar.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.entity.KeepCar;
import com.car.contractcar.myapplication.http.HttpUtil;
import com.car.contractcar.myapplication.ui.LoadingPage;
import com.car.contractcar.myapplication.ui.MyGridView;
import com.car.contractcar.myapplication.utils.Constant;
import com.car.contractcar.myapplication.utils.JsonUtils;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.car.contractcar.myapplication.MyApplication.context;

/**
 * Created by macmini2 on 16/11/5.
 */

public class KeepcarFragment extends Fragment {
    @BindView(R.id.keep_car_active)
    MyGridView keepCarActive;
    @BindView(R.id.keep_car_list)
    ListView keepCarList;
    private KeepCar keepCar;
    private List<KeepCar.ActiveBean> active;
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
//
//        ButterKnife.bind(this, view);
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int LayoutId() {
                return R.layout.fragment_keepcar;
            }

            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(KeepcarFragment.this, successView);
                if (!TextUtils.isEmpty(resultState.getContent())) {
                    keepCar = (KeepCar) JsonUtils.json2Bean(resultState.getContent(), KeepCar.class);
                    refreshView();
                }

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


    private void initView() {

    }

    private void initData() {
        HttpUtil.get(Constant.HTTP_BASE + Constant.HTTP_KEEP_CAR, new HttpUtil.callBlack() {
            @Override
            public void succcess(String code) {
                keepCar = (KeepCar) JsonUtils.json2Bean(code, KeepCar.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
        if (keepCar != null) {
            active = keepCar.getActive();
//            carstore = keepCar.getCarstore();
            keepCarActive.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return active.size();
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
                    View view = View.inflate(getActivity(), R.layout.keep_car_item, null);
                    ImageView itemImg = (ImageView) view.findViewById(R.id.iv_item);
                    TextView itemText = (TextView) view.findViewById(R.id.tv_item);
                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(active.get(position).getImage())).into(itemImg);
                    itemText.setText(active.get(position).getTitle());
                    return view;
                }
            });

//            keepCarList.setAdapter(new BaseAdapter() {
//                @Override
//                public int getCount() {
//                    return carstore.size();
//                }
//
//                @Override
//                public Object getItem(int position) {
//                    return null;
//                }
//
//                @Override
//                public long getItemId(int position) {
//                    return position;
//                }
//
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    ViewHolder viewHolder = null;
//                    if (convertView == null) {
//                        convertView = View.inflate(getActivity(), R.layout.list_item_keepcar, null);
//                        viewHolder = new ViewHolder(convertView);
//                        convertView.setTag(viewHolder);
//                    } else {
//                        viewHolder = (ViewHolder) convertView.getTag();
//                    }
//                    viewHolder.textAddress.setText(carstore.get(position).getBaddress());
//                    viewHolder.shopName.setText(carstore.get(position).getBname());
//                    HttpUtil.picasso.with(context).load(HttpUtil.getImage_path(carstore.get(position).getBimage())).into(viewHolder.keepcarItemImg);
//
//                    return convertView;
//                }
//            });

            setListViewHeight(keepCarList);
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

    static class ViewHolder {
        @BindView(R.id.keepcar_item_img)
        ImageView keepcarItemImg;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.text_address)
        TextView textAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
