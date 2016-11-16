package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/15.
 */

public class KeepCar {

    /**
     * status : 1
     * active : [{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车1","url":"http://www.qq.com"},{"image":"/img/menu_1_3.png","title":"养车6","url":"http://www.qq.com"}]
     * carstore : [{"baddress":"陕西省西安市","bid":1,"bimage":"/img/menu_viewpager_1.png","bname":"4s店"},{"baddress":"陕西省西安市","bid":2,"bimage":"/img/menu_viewpager_1.png","bname":"4s店"}]
     */

    private int status;
    /**
     * image : /img/menu_1_3.png
     * title : 养车1
     * url : http://www.qq.com
     */

    private List<ActiveBean> active;
    /**
     * baddress : 陕西省西安市
     * bid : 1
     * bimage : /img/menu_viewpager_1.png
     * bname : 4s店
     */

    private List<CarstoreBean> carstore;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ActiveBean> getActive() {
        return active;
    }

    public void setActive(List<ActiveBean> active) {
        this.active = active;
    }

    public List<CarstoreBean> getCarstore() {
        return carstore;
    }

    public void setCarstore(List<CarstoreBean> carstore) {
        this.carstore = carstore;
    }

    public static class ActiveBean {
        private String image;
        private String title;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class CarstoreBean {
        private String baddress;
        private int bid;
        private String bimage;
        private String bname;

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getBimage() {
            return bimage;
        }

        public void setBimage(String bimage) {
            this.bimage = bimage;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }
    }
}
