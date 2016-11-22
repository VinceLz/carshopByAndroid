package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/20.
 */

public class SelectData {

    /**
     * status : 1
     * list : [{"gfirstimage":"/img/car.png","gid":1,"gname":"宝马","gprice":1234567},{"gfirstimage":"/img/car.png","gid":2,"gname":"宝马1","gprice":123},{"gfirstimage":"/img/car.png","gid":3,"gname":"宝马1","gprice":1},{"gfirstimage":"/img/car.png","gid":4,"gname":"宝马1","gprice":2},{"gfirstimage":"/img/car.png","gid":5,"gname":"宝马1","gprice":3},{"gfirstimage":"/img/car.png","gid":6,"gname":"宝马1","gprice":4}]
     */

    private int status;
    /**
     * gfirstimage : /img/car.png
     * gid : 1
     * gname : 宝马
     * gprice : 1234567
     */

    private List<BuyCarIndex.HomeCarBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BuyCarIndex.HomeCarBean> getList() {
        return list;
    }

    public void setList(List<BuyCarIndex.HomeCarBean> list) {
        this.list = list;
    }
}
