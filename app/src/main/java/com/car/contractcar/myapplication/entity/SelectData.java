package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/20.
 */

public class SelectData {


    /**
     * status : 1
     * list : [{"gid":1,"glastimage":"/img/car.png","gname":"宝马","maxprice":444444,"minprice":123,"title":"直降500"},{"gid":2,"glastimage":"/img/car.png","gname":"宝马1","maxprice":11,"minprice":0,"title":"直降500"}]
     */

    private int status;
    /**
     * gid : 1
     * glastimage : /img/car.png
     * gname : 宝马
     * maxprice : 444444
     * minprice : 123
     * title : 直降500
     */

    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int gid;
        private String glastimage;
        private String gname;
        private int maxprice;
        private int minprice;
        private String title;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGlastimage() {
            return glastimage;
        }

        public void setGlastimage(String glastimage) {
            this.glastimage = glastimage;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(int maxprice) {
            this.maxprice = maxprice;
        }

        public int getMinprice() {
            return minprice;
        }

        public void setMinprice(int minprice) {
            this.minprice = minprice;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
