package kr.or.ddit.sw.vo.advertise;

import java.io.Serializable;

public class AdvertiseVO implements Serializable {
    private int ad_no;
    private String ad_pic_adr;

    public int getAd_no() {
        return ad_no;
    }

    public void setAd_no(int ad_no) {
        this.ad_no = ad_no;
    }

    public String getAd_pic_adr() {
        return ad_pic_adr;
    }

    public void setAd_pic_adr(String ad_pic_adr) {
        this.ad_pic_adr = ad_pic_adr;
    }
}
