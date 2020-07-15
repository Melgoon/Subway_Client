package kr.or.ddit.sw.vo.ordertable;

import java.io.Serializable;

public class OrderVO implements Serializable {
    private int order_no;
    private int cart_no;
    private int order_qty;
    private int order_price;
    private String order_date;
    private int order_coupon;
    private String order_st;
    private int prod_no;
    private String order_cont;
    private String cart_no_string;
    private String mem_id;

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public int getOrder_qty() {
        return order_qty;
    }

    public void setOrder_qty(int order_qty) {
        this.order_qty = order_qty;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getOrder_coupon() {
        return order_coupon;
    }

    public void setOrder_coupon(int order_coupon) {
        this.order_coupon = order_coupon;
    }

    public String getOrder_st() {
        return order_st;
    }

    public void setOrder_st(String order_st) {
        this.order_st = order_st;
    }

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }

    public String getOrder_cont() {
        return order_cont;
    }

    public void setOrder_cont(String order_cont) {
        this.order_cont = order_cont;
    }

    public String getCart_no_string() {
        return cart_no_string;
    }

    public void setCart_no_string(String cart_no_string) {
        this.cart_no_string = cart_no_string;
    }
}
