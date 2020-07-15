package kr.or.ddit.sw.vo.prod;

import java.io.Serializable;

public class CombiVO implements Serializable {
    private int combi_no;
    private int prod_no;
    private int mtr_no;
    private int combi_count;

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

    public int getMtr_no() {
        return mtr_no;
    }

    public void setMtr_no(int mtr_no) {
        this.mtr_no = mtr_no;
    }

    public int getCombi_count() {
        return combi_count;
    }

    public void setCombi_count(int combi_count) {
        this.combi_count = combi_count;
    }
}
