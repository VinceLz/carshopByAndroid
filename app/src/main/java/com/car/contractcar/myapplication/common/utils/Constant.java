package com.car.contractcar.myapplication.common.utils;

import android.content.Intent;

/**
 * Created by macmini2 on 16/11/6.
 */

public class Constant {
    public static final String CAR = "CAR";
    public static final int BUY_CAR = 1;
    public static final int KEEP_CAR = 0;
    //外网
//   public static final String HTTP_BASE = "http://59.110.5.105/carshop/";
//    内网
    public static final String HTTP_BASE = "http://172.16.120.65:8080/carshop/";
    public static final String HTTP_HOME = "home.action";
    public static final String HTTP_KEEP_CAR = "/ycstore/home.action";

    public static final String HTTP_SHOP_INFO = "store/getall.action?bid=";
    public static final String HTTP_CAR_INFO = "car/getModels.action?gid=";
    public static final String HTTP_CAR_DETAIL = "car/models/get.action?mid=";
    public static final String HTTP_FLOOR_PRICE = "store/get.action?bid=";
    public static final String HTTP_SELECT = "home/car";
    public static final String HTTP_CAR_COLOR = "car/models/getColor.action?mid=";

    public static final String[] TransmissionCase = {"手动", "自动"};
    public static final String[] displacement = {"1.0及以下", "1.1-1.6L", "1.7-2.0L", "2.1-2.5L", "2.6-3.0L", "3.1-4.0L", "4.0L以上"};
    public static final String[] drive = {"前驱", "后驱", "四驱"};
    public static final String[] Fuel = {"汽油", "柴油", "油电混合", "纯电动", "插电式混动", "增程式"};
    public static final String[] countries = {"中国", "德国", "日本", "美国", "韩国", "法国", "英国", "其他"};
    public static final String[] seat = {"2座", "4座", "5座", "6座", "7座", "7座以上"};
    public static final String[] structure = {"两厢", "三厢", "掀背", "旅行版", "硬顶敞篷车", "软顶敞篷车", "硬顶跑车", "客车", "货车"};
    public static final String[] production = {"自主", "合资", "进口"};
    public static Intent intent = new Intent(Intent.ACTION_CALL);

}
