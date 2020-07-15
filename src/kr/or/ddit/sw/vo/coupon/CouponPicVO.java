package kr.or.ddit.sw.vo.coupon;

import java.io.Serializable;

public class CouponPicVO implements Serializable {
    private int coupon_pic_no;
    private String coupon_pic_adr;

    public int getCoupon_pic_no() {
        return coupon_pic_no;
    }

    public void setCoupon_pic_no(int coupon_pic_no) {
        this.coupon_pic_no = coupon_pic_no;
    }

    public String getCoupon_pic_adr() {
        return coupon_pic_adr;
    }

    public void setCoupon_pic_adr(String coupon_pic_adr) {
        this.coupon_pic_adr = coupon_pic_adr;
    }
}
