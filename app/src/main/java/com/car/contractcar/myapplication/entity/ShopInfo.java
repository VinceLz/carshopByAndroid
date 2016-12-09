package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/24.
 */

public class ShopInfo {


    /**
     * status : 1
     * business : {"baddress":"陕西省西安市","bid":1,"bimage":["/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png"],"bname":"某某4S经销店","bphone":"18292882168","childs":[{"gid":1,"gname":"宝马","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":123,"title":"直降500"},{"gid":2,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":12,"title":"直降500"},{"gid":9,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":44,"minprice":2,"title":"直降500"}],"majorbusiness":"玩玩","title1":"进店就有大礼包 ","title2":"购车享1000元直补"}
     */

    private int status;
    /**
     * baddress : 陕西省西安市
     * bid : 1
     * bimage : ["/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png","/img/menu_viewpager_1.png"]
     * bname : 某某4S经销店
     * bphone : 18292882168
     * childs : [{"gid":1,"gname":"宝马","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":123,"title":"直降500"},{"gid":2,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":123,"minprice":12,"title":"直降500"},{"gid":9,"gname":"宝马1","gshowImage":"/img/menu_viewpager_1.png","maxprice":44,"minprice":2,"title":"直降500"}]
     * majorbusiness : 玩玩
     * title1 : 进店就有大礼包
     * title2 : 购车享1000元直补
     */

    private BusinessBean business;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public static class BusinessBean {
        private String baddress;
        private int bid;
        private String bname;
        private String bphone;
        private String majorbusiness;
        private String title1;
        private String title2;
        private List<String> bimage;
        /**
         * gid : 1
         * gname : 宝马
         * gshowImage : /img/menu_viewpager_1.png
         * maxprice : 123
         * minprice : 123
         * title : 直降500
         */

        private List<ChildsBean> childs;

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

        public List<String> getBimage() {
            return bimage;
        }

        public void setBimage(List<String> bimage) {
            this.bimage = bimage;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBean> childs) {
            this.childs = childs;
        }

        public static class ChildsBean {
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
    }
}
