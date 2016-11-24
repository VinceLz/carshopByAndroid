package com.car.contractcar.myapplication.entity;

import java.util.List;

/**
 * Created by macmini2 on 16/11/24.
 */

public class ShopInfo {


    /**
     * status : 1
     * business : {"baddress":"陕西省西安市","bid":1,"bimage":["/img/menu_viewpager_1.png"],"bname":"某某4S经销店","bphone":"18292882168","childs":[{"gid":1,"glastimage":"/img/menu_1_3.png","gname":"宝马","gprice":1234567,"guidegprice":123,"model":"领先型","title":"直降500"},{"gid":6,"glastimage":"/img/menu_1_3.png","gname":"宝马1","gprice":4,"guidegprice":123,"model":"土豪型"}],"majorbusiness":"玩玩"}
     */

    private int status;
    /**
     * baddress : 陕西省西安市
     * bid : 1
     * bimage : ["/img/menu_viewpager_1.png"]
     * bname : 某某4S经销店
     * bphone : 18292882168
     * childs : [{"gid":1,"glastimage":"/img/menu_1_3.png","gname":"宝马","gprice":1234567,"guidegprice":123,"model":"领先型","title":"直降500"},{"gid":6,"glastimage":"/img/menu_1_3.png","gname":"宝马1","gprice":4,"guidegprice":123,"model":"土豪型"}]
     * majorbusiness : 玩玩
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
        private List<String> bimage;
        /**
         * gid : 1
         * glastimage : /img/menu_1_3.png
         * gname : 宝马
         * gprice : 1234567
         * guidegprice : 123
         * model : 领先型
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
            private String glastimage;
            private String gname;
            private int gprice;
            private int guidegprice;
            private String model;
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

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
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
