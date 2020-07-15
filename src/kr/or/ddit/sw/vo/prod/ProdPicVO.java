package kr.or.ddit.sw.vo.prod;

import java.io.Serializable;

public class ProdPicVO implements Serializable {
    private int prod_no;
    private String prod_pic_adr;

    public int getProd_no() {
        return prod_no;
    }

    public void setProd_no(int prod_no) {
        this.prod_no = prod_no;
    }

    public String getProd_pic_adr() {
        return prod_pic_adr;
    }

    public void setProd_pic_adr(String prod_pic_adr) {
        this.prod_pic_adr = prod_pic_adr;
    }
}
