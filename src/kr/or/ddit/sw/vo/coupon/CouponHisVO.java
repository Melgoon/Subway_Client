package kr.or.ddit.sw.vo.coupon;

import java.io.Serializable;

public class CouponHisVO implements Serializable {
    private String mem_id;
    private int coupon_no;
    private String col4;

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public int getCoupon_no() {
        return coupon_no;
    }

    public void setCoupon_no(int coupon_no) {
        this.coupon_no = coupon_no;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }
}
