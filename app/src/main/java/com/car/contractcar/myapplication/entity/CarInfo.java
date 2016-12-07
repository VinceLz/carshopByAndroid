package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/12/6.
 */

public class CarInfo {


    /**
     * status : 1
     * list : [{"gname":"123","gprice":2,"guidegprice":123,"mid":1,"mimage":"/img/menu_viewpager_1.png","mname":"1","title":"1"},{"gname":"123","gprice":3,"guidegprice":123,"mid":2,"mimage":"/img/menu_viewpager_1.png","mname":"2","title":"1"}]
     */

    private int status;
    /**
     * gname : 123
     * gprice : 2
     * guidegprice : 123
     * mid : 1
     * mimage : /img/menu_viewpager_1.png
     * mname : 1
     * title : 1
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
        private String gname;
        private int gprice;
        private int guidegprice;
        private int mid;
        private String mimage;
        private String mname;
        private String title;

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getGprice() {
            return gprice;
        }

        public void setGprice(int gprice) {
            this.gprice = gprice;
        }

        public int getGuidegprice() {
            return guidegprice;
        }

        public void setGuidegprice(int guidegprice) {
            this.guidegprice = guidegprice;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getMimage() {
            return mimage;
        }

        public void setMimage(String mimage) {
            this.mimage = mimage;
        }

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
