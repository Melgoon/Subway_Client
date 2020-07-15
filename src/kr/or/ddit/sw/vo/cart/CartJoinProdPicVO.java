package kr.or.ddit.sw.vo.cart;

import java.io.Serializable;

public class CartJoinProdPicVO implements Serializable {
    private int cart_no;
    private int cart_price;
    private int prod_no;
    private String cart_cont;
    private String prod_pic_adr;
    private int cart_chk;
    private int cart_chk_buy;

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

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }

    public String getCart_cont() {
        return cart_cont;
    }

    public void setCart_cont(String cart_cont) {
        this.cart_cont = cart_cont;
    }

    public String getProd_pic_adr() {
        return prod_pic_adr;
    }

    public void setProd_pic_adr(String prod_pic_adr) {
        this.prod_pic_adr = prod_pic_adr;
    }

    public int getCart_chk() {
        return cart_chk;
    }

    public void setCart_chk(int cart_chk) {
        this.cart_chk = cart_chk;
    }

    public int getCart_chk_buy() {
        return cart_chk_buy;
    }

    public void setCart_chk_buy(int cart_chk_buy) {
        this.cart_chk_buy = cart_chk_buy;
    }
}
