package com.car.contractcar.myapplication.keepcar.bean;

import java.util.List;

/**
 * Created by macmini2 on 17/3/28.
 */

public class KeepCarShopInfo {

    /**
     * status : 1
     * ycstore : {"baddress":"玉祥门才智大厦","bdate":"2017-03-28 14:32:28.0","bphone":"123456789","clean":[],"commentcount":0,"decoration":[],"isHot":0,"mainclean":[],"mbid":26,"mbname":"DOUDOU","purchase":0,"score":0,"time":"9：00\u2014\u201410:00","uname":"豆豆"}
     */

    private int status;
    /**
     * baddress : 玉祥门才智大厦
     * bdate : 2017-03-28 14:32:28.0
     * bphone : 123456789
     * clean : []
     * commentcount : 0
     * decoration : []
     * isHot : 0
     * mainclean : []
     * mbid : 26
     * mbname : DOUDOU
     * purchase : 0
     * score : 0
     * time : 9：00——10:00
     * uname : 豆豆
     */

    private YcstoreBean ycstore;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public YcstoreBean getYcstore() {
        return ycstore;
    }

    public void setYcstore(YcstoreBean ycstore) {
        this.ycstore = ycstore;
    }

    public static class YcstoreBean {
        private String baddress;
        private String bdate;
        private String bphone;
        private int commentcount;
        private int isHot;
        private int mbid;
        private String mbname;
        private int purchase;
        private int score;
        private String time;
        private String uname;
        private List<?> clean;
        private List<?> decoration;
        private List<?> mainclean;
        private List<String> bimage;

        public List<String> getBimage() {
            return bimage;
        }

        public void setBimage(List<String> bimage) {
            this.bimage = bimage;
        }

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public String getBdate() {
            return bdate;
        }

        public void setBdate(String bdate) {
            this.bdate = bdate;
        }

        public String getBphone() {
            return bphone;
        }

        public void setBphone(String bphone) {
            this.bphone = bphone;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getMbid() {
            return mbid;
        }

        public void setMbid(int mbid) {
            this.mbid = mbid;
        }

        public String getMbname() {
            return mbname;
        }

        public void setMbname(String mbname) {
            this.mbname = mbname;
        }

        public int getPurchase() {
            return purchase;
        }

        public void setPurchase(int purchase) {
            this.purchase = purchase;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public List<?> getClean() {
            return clean;
        }

        public void setClean(List<?> clean) {
            this.clean = clean;
        }

        public List<?> getDecoration() {
            return decoration;
        }

        public void setDecoration(List<?> decoration) {
            this.decoration = decoration;
        }

        public List<?> getMainclean() {
            return mainclean;
        }

        public void setMainclean(List<?> mainclean) {
            this.mainclean = mainclean;
        }
    }
}
