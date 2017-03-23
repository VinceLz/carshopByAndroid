package com.car.contractcar.myapplication.common.utils;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by macmini2 on 17/3/21.
 */

public class ImageLoad {

    private static final String IMAGE_SERVER_PATH = "http://59.110.5.105";

    public static void loadImg(SimpleDraweeView simpleDraweeView, String url) {
        Uri uri = Uri.parse(IMAGE_SERVER_PATH + url);
        simpleDraweeView.setImageURI(uri);
    }

}
