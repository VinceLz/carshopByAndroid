package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/12/6.
 */

public class CarInfo {


    /**
     * status : 1
     * car : {"child":[{"gprice":2,"guidegprice":123,"mid":1,"mname":"1","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"},{"gprice":3,"guidegprice":123,"mid":2,"mname":"2","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"}],"gid":1,"gimage":["/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png"],"gname":"宝马","maxprice":123,"minprice":123,"title":"直降500"}
     */

    private int status;
    /**
     * child : [{"gprice":2,"guidegprice":123,"mid":1,"mname":"1","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"},{"gprice":3,"guidegprice":123,"mid":2,"mname":"2","mshowImage":"/img/menu_viewpager_1.png","mtitle":"优惠1000"}]
     * gid : 1
     * gimage : ["/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png"]
     * gname : 宝马
     * maxprice : 123
     * minprice : 123
     * title : 直降500
     */

    private CarBean car;

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

    public static class CarBean {
        private int gid;
        private String gname;
        private int maxprice;
        private int minprice;
        private String title;
        /**
         * gprice : 2
         * guidegprice : 123
         * mid : 1
         * mname : 1
         * mshowImage : /img/menu_viewpager_1.png
         * mtitle : 优惠1000
         */

        private List<ChildBean> child;
        private List<String> gimage;

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

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public List<String> getGimage() {
            return gimage;
        }

        public void setGimage(List<String> gimage) {
            this.gimage = gimage;
        }

        public static class ChildBean {
            private int gprice;
            private int guidegprice;
            private int mid;
            private String mname;
            private String mshowImage;
            private String mtitle;

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

            public String getMname() {
                return mname;
            }

            public void setMname(String mname) {
                this.mname = mname;
            }

            public String getMshowImage() {
                return mshowImage;
            }

            public void setMshowImage(String mshowImage) {
                this.mshowImage = mshowImage;
            }

            public String getMtitle() {
                return mtitle;
            }

            public void setMtitle(String mtitle) {
                this.mtitle = mtitle;
            }
        }
    }
}
