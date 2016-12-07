package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/12/7.
 */

public class CarDetail {

    /**
     * status : 1
     * car : {"gid":1,"gname":"123","guidegprice":123,"mid":1,"mimage":"/img/menu_viewpager_1.png,/img/menu_viewpager_1.png","mname":"1","showImage":"/img/menu_viewpager_1.png","title":"1"}
     * recommend : [{"gid":1,"gname":"123","guidegprice":123,"mid":2,"mimage":"/img/menu_viewpager_1.png,/img/menu_viewpager_1.png","mname":"2","title":"1"},{"gid":2,"gname":"123","guidegprice":123,"mid":3,"mimage":"/img/menu_viewpager_1.png,/img/menu_viewpager_1.png","mname":"3","title":"1"}]
     */

    private int status;
    /**
     * gid : 1
     * gname : 123
     * guidegprice : 123
     * mid : 1
     * mimage : /img/menu_viewpager_1.png,/img/menu_viewpager_1.png
     * mname : 1
     * showImage : /img/menu_viewpager_1.png
     * title : 1
     */

    private CarBean car;
    /**
     * gid : 1
     * gname : 123
     * guidegprice : 123
     * mid : 2
     * mimage : /img/menu_viewpager_1.png,/img/menu_viewpager_1.png
     * mname : 2
     * title : 1
     */

    private List<RecommendBean> recommend;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CarBean getCar() {
        return car;
    }

    public void setCar(CarBean car) {
        this.car = car;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class CarBean {
        private int gid;
        private String gname;
        private int guidegprice;
        private int mid;
        private String mimage;
        private String mname;
        private String showImage;
        private String title;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
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

        public String getShowImage() {
            return showImage;
        }

        public void setShowImage(String showImage) {
            this.showImage = showImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class RecommendBean {
        private int gid;
        private String gname;
        private int guidegprice;
        private int mid;
        private String mimage;
        private String mname;
        private String title;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
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
