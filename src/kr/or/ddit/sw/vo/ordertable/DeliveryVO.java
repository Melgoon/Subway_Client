package kr.or.ddit.sw.vo.ordertable;

import java.io.Serializable;

public class DeliveryVO implements Serializable{
    private String Deli_no;
    private String Deli_name;
    private String Deli_price;
    private String Deli_adr;
    private String Order_no;
    private String Cart_no;
    private String Deli_time;
    private String Deli_status;
    private String Deli_timerq;
    private String Deli_jijum;
    private int chart_no;

    public int getChartNo() {
        return chart_no;
    }

    public void setChartNo(int chart_no) {
        this.chart_no = chart_no;
    }

    public String getDeli_no() {
        return Deli_no;
    }

    public void setDeli_no(String deli_no) {
        Deli_no = deli_no;
    }

    public String getDeli_name() {
        return Deli_name;
    }

    public void setDeli_name(String deli_name) {
        Deli_name = deli_name;
    }

    public String getDeli_price() {
        return Deli_price;
    }

    public void setDeli_price(String deli_price) {
        Deli_price = deli_price;
    }

    public String getDeli_adr() {
        return Deli_adr;
    }

    public void setDeli_adr(String deli_adr) {
        Deli_adr = deli_adr;
    }

    public String getOrder_no() {
        return Order_no;
    }

    public void setOrder_no(String order_no) {
        Order_no = order_no;
    }

    public String getCart_no() {
        return Cart_no;
    }

    public void setCart_no(String cart_no) {
        Cart_no = cart_no;
    }

    public String getDeli_time() {
        return Deli_time;
    }

    public void setDeli_time(String deli_time) {
        Deli_time = deli_time;
    }

    public String getDeli_status() {
        return Deli_status;
    }

    public void setDeli_status(String deli_status) {
        Deli_status = deli_status;
    }

    public String getDeli_timerq() {
        return Deli_timerq;
    }

    public void setDeli_timerq(String deli_timerq) {
        Deli_timerq = deli_timerq;
    }

    public String getDeli_jijum() {
        return Deli_jijum;
    }

    public void setDeli_jijum(String deli_jijum) {
        this.Deli_jijum = deli_jijum;
    }
}
