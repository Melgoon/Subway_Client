package kr.or.ddit.sw.vo.coupon;

import java.io.Serializable;

public class CouponVO implements Serializable {
    private int coupon_no;
    private String coupon_name;
    private int coupon_price;
    private int coupon_pic_no;

    public int getCoupon_no() {
        return coupon_no;
    }

    public void setCoupon_no(int coupon_no) {
        this.coupon_no = coupon_no;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public int getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(int coupon_price) {
        this.coupon_price = coupon_price;
    }

    public int getCoupon_pic_no() {
        return coupon_pic_no;
    }

    public void setCoupon_pic_no(int coupon_pic_no) {
        this.coupon_pic_no = coupon_pic_no;
    }
}
