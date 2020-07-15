package kr.or.ddit.sw.vo.cart;

import java.io.Serializable;

public class CartVO implements Serializable {
    private int cart_no;
    private int cart_price;
    private int cart_qty;
    private int cart_chk;
    private int mtr_no;
    private int combi_no;
    private int cart_chk_buy;
    private String cart_cont;
    private String mem_id;
    private int prod_no;

    public int getMtr_no() {
        return mtr_no;
    }

    public void setMtr_no(int mtr_no) {
        this.mtr_no = mtr_no;
    }

    public int getCombi_no() {
        return combi_no;
    }

    public void setCombi_no(int combi_no) {
        this.combi_no = combi_no;
    }

    public int getCart_chk_buy() {
        return cart_chk_buy;
    }

    public void setCart_chk_buy(int cart_chk_buy) {
        this.cart_chk_buy = cart_chk_buy;
    }

    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public int getCart_price() {
        return cart_price;
    }

    public void setCart_price(int cart_price) {
        this.cart_price = cart_price;
    }

    public int getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(int cart_qty) {
        this.cart_qty = cart_qty;
    }

    public int getCart_chk() {
        return cart_chk;
    }

    public void setCart_chk(int cart_chk) {
        this.cart_chk = cart_chk;
    }

    public String getCart_cont() {
        return cart_cont;
    }

    public void setCart_cont(String cart_cont) {
        this.cart_cont = cart_cont;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }
}
