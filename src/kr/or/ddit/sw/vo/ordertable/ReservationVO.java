package kr.or.ddit.sw.vo.ordertable;

import java.io.Serializable;

public class ReservationVO implements Serializable {
    private String Reser_no;
    private String Reser_name;
    private String Reser_price;
    private String Reser_jijum;
    private String Reser_time;
    private String Reser_date;
    private String Order_no;
    private String Cart_no;
    private String Reser_status;
    private int chart_no;

    public int getChart_no() {
        return chart_no;
    }

    public void setChart_no(int chart_no) {
        this.chart_no = chart_no;
    }

    public String getReser_no() {
        return Reser_no;
    }

    public void setReser_no(String reser_no) {
        Reser_no = reser_no;
    }

    public String getReser_name() {
        return Reser_name;
    }

    public void setReser_name(String reser_name) {
        Reser_name = reser_name;
    }

    public String getReser_price() {
        return Reser_price;
    }

    public void setReser_price(String reser_price) {
        Reser_price = reser_price;
    }

    public String getReser_jijum() {
        return Reser_jijum;
    }

    public void setReser_jijum(String reser_jijum) {
        Reser_jijum = reser_jijum;
    }

    public String getReser_time() {
        return Reser_time;
    }

    public void setReser_time(String reser_time) {
        Reser_time = reser_time;
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

    public String getReser_date() {
        return Reser_date;
    }

    public void setReser_date(String reser_date) {
        Reser_date = reser_date;
    }

    public String getReser_status() {
        return Reser_status;
    }

    public void setReser_status(String reser_status) {
        Reser_status = reser_status;
    }
}
