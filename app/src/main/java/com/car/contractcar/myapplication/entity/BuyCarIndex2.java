package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/22.
 */

public class BuyCarIndex2 {


    /**
     * status : 1
     * homeImage : [{"image":"/img/menu_viewpager_5.png","url":"http://www.baidu.com"},{"image":"/img/menu_viewpager_4.png","url":"http://www.taobao.com"},{"image":"/img/menu_viewpager_1.png","url":"http://www.qq.com"}]
     * homeActive : [{"title":"优惠1","url":"http://www.baidu.com"},{"title":"优惠2","url":"http://www.qq.com"},{"title":"优惠3","url":"http://www.qq.com"},{"title":"优惠4","url":"http://www.qq.com"},{"title":"优惠5","url":"http://www.qq.com"},{"title":"优惠6","url":"http://www.qq.com"}]
     * carstore : [{"baddress":"陕西省西安市","bid":1,"bimage":"/img/menu_viewpager_1.png","bname":"某某4S经销店","bphone":"18292882168","distance":"0","majorbusiness":"玩玩","title1":"进店就有大礼包 ","title2":"购车享1000元直补"},{"baddress":"陕西省西安市","bid":2,"bimage":"/img/menu_viewpager_1.png","bname":"某某4S经销店","bphone":"18292882168","distance":"0","majorbusiness":"跑车","title1":"进店就有小礼包","title2":"购车享1000元直补"}]
     */

    private int status;
    /**
     * image : /img/menu_viewpager_5.png
     * url : http://www.baidu.com
     */

    private List<HomeImageBean> homeImage;
    /**
     * title : 优惠1
     * url : http://www.baidu.com
     */

    private List<HomeActiveBean> homeActive;
    /**
     * baddress : 陕西省西安市
     * bid : 1
     * bimage : /img/menu_viewpager_1.png
     * bname : 某某4S经销店
     * bphone : 18292882168
     * distance : 0
     * majorbusiness : 玩玩
     * title1 : 进店就有大礼包
     * title2 : 购车享1000元直补
     */

    private List<CarstoreBean> carstore;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HomeImageBean> getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(List<HomeImageBean> homeImage) {
        this.homeImage = homeImage;
    }

    public List<HomeActiveBean> getHomeActive() {
        return homeActive;
    }

    public void setHomeActive(List<HomeActiveBean> homeActive) {
        this.homeActive = homeActive;
    }

    public List<CarstoreBean> getCarstore() {
        return carstore;
    }

    public void setCarstore(List<CarstoreBean> carstore) {
        this.carstore = carstore;
    }

    public static class HomeImageBean {
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

    public static class HomeActiveBean {
        private String title;
        private String url;

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
        private String bphone;
        private String distance;
        private String majorbusiness;
        private String title1;
        private String title2;

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

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getMajorbusiness() {
            return majorbusiness;
        }

        public void setMajorbusiness(String majorbusiness) {
            this.majorbusiness = majorbusiness;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }
    }
}
