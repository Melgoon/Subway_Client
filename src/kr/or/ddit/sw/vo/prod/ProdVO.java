package kr.or.ddit.sw.vo.prod;


import java.io.Serializable;

public class ProdVO implements Serializable {
    private int prod_no;
    private int kate_no;
    private String prod_name;
    private int prod_price;
    private int prod_kal;
    private String prod_source;
    private String prod_detail;
    private String prod_pic_adr;

    public String getProd_detail() {
        return prod_detail;
    }

    public void setProd_detail(String prod_detail) {
        this.prod_detail = prod_detail;
    }

    public String getProd_source() {
        return prod_source;
    }

    public void setProd_source(String prod_source) {
        this.prod_source = prod_source;
    }

    public int getProd_kal() {
        return prod_kal;
    }

    public void setProd_kal(int prod_kal) {
        this.prod_kal = prod_kal;
    }

    public String getProd_pic_adr() {
        return prod_pic_adr;
    }

    public void setProd_pic_adr(String prod_pic_adr) {
        this.prod_pic_adr = prod_pic_adr;
    }

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }

    public int getKate_no() {
        return kate_no;
    }

    public void setKate_no(int kate_no) {
        this.kate_no = kate_no;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public int getProd_price() {
        return prod_price;
    }

    public void setProd_price(int prod_price) {
        this.prod_price = prod_price;
    }
}
