package kr.or.ddit.sw.vo.cart;

import java.io.Serializable;

public class CombiMtrJoinVO implements Serializable {
    private int mtr_no;
    private String mtr_name;
    private int mtr_cal;
    private String mtr_detail;
    private int mtr_div;
    private int mtr_pic_no;
    private int combi_no;
    private int prod_no;
    private int combi_count;
    private int prod_price;

    public int getProd_price() {
        return prod_price;
    }

    public void setProd_price(int prod_price) {
        this.prod_price = prod_price;
    }

    public int getMtr_no() {
        return mtr_no;
    }

    public void setMtr_no(int mtr_no) {
        this.mtr_no = mtr_no;
    }

    public String getMtr_name() {
        return mtr_name;
    }

    public void setMtr_name(String mtr_name) {
        this.mtr_name = mtr_name;
    }

    public int getMtr_cal() {
        return mtr_cal;
    }

    public void setMtr_cal(int mtr_cal) {
        this.mtr_cal = mtr_cal;
    }

    public String getMtr_detail() {
        return mtr_detail;
    }

    public void setMtr_detail(String mtr_detail) {
        this.mtr_detail = mtr_detail;
    }

    public int getMtr_div() {
        return mtr_div;
    }

    public void setMtr_div(int mtr_div) {
        this.mtr_div = mtr_div;
    }

    public int getMtr_pic_no() {
        return mtr_pic_no;
    }

    public void setMtr_pic_no(int mtr_pic_no) {
        this.mtr_pic_no = mtr_pic_no;
    }

    public int getCombi_no() {
        return combi_no;
    }

    public void setCombi_no(int combi_no) {
        this.combi_no = combi_no;
    }

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }

    public int getCombi_count() {
        return combi_count;
    }

    public void setCombi_count(int combi_count) {
        this.combi_count = combi_count;
    }
}
