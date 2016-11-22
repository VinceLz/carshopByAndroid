package com.car.contractcar.myapplication.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.utils.UIUtils;

/**
 * Created by macmini2 on 16/11/5.
 */

public class MeFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_me);
        return view;
    }
}
