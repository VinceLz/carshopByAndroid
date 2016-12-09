package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/20.
 */

public class SelectData {


    /**
     * status : 1
     * params : {"pageNo":1,"pageSize":10}
     * result : [{"gid":1,"gname":"宝马","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":123,"title":"直降500"},{"gid":2,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":12,"title":"直降500"},{"gid":3,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":44444,"minprice":44444,"title":"直降500"},{"gid":4,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":1,"minprice":1,"title":"直降500"}]
     * image : [{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"},{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"},{"image":"/img/menu_viewpager_5.png","url":"http://www.qq.com"}]
     */

    private int status;
    /**
     * pageNo : 1
     * pageSize : 10
     */

    private ParamsBean params;
    /**
     * gid : 1
     * gname : 宝马
     * gshowImage : /img/menu_viewpager_1.png
     * maxprice : 123
     * minprice : 123
     * title : 直降500
     */

    private List<ResultBean> result;
    /**
     * image : /img/menu_viewpager_5.png
     * url : http://www.qq.com
     */

    private List<ImageBean> image;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public List<ImageBean> getImage() {
        return image;
    }

    public void setImage(List<ImageBean> image) {
        this.image = image;
    }

    public static class ParamsBean {
        private int pageNo;
        private int pageSize;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class ResultBean {
        private int gid;
        private String gname;
        private String gshowImage;
        private int maxprice;
        private int minprice;
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

        public String getGshowImage() {
            return gshowImage;
        }

        public void setGshowImage(String gshowImage) {
            this.gshowImage = gshowImage;
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

    public static class ImageBean {
        private String image;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
