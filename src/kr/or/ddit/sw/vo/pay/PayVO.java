package kr.or.ddit.sw.vo.pay;

import java.io.Serializable;

public class PayVO implements Serializable {
    private int pay_no;
    private String pay_way;
    private int reser_no;
    private int deli_no;

    public int getPay_no() {
        return pay_no;
    }

    public void setPay_no(int pay_no) {
        this.pay_no = pay_no;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public int getReser_no() {
        return reser_no;
    }

    public void setReser_no(int reser_no) {
        this.reser_no = reser_no;
    }

    public int getDeli_no() {
        return deli_no;
    }

    public void setDeli_no(int deli_no) {
        this.deli_no = deli_no;
    }
}
