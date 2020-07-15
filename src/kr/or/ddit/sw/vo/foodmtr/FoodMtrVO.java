package kr.or.ddit.sw.vo.foodmtr;

import java.io.Serializable;

public class FoodMtrVO implements Serializable {
    private int MTR_NO;
    private String MTR_NAME;
    private int MTR_CAL;
    private String MTR_DETAIL;
    private int MTR_DIV;
    private int MTR_PIC_NO;
    private String MTR_PIC_ADR;

    public String getMTR_PIC_ADR() {
        return MTR_PIC_ADR;
    }

    public void setMTR_PIC_ADR(String MTR_PIC_ADR) {
        this.MTR_PIC_ADR = MTR_PIC_ADR;
    }

    public int getMTR_NO() {
        return MTR_NO;
    }

    public void setMTR_NO(int MTR_NO) {
        this.MTR_NO = MTR_NO;
    }

    public String getMTR_NAME() {
        return MTR_NAME;
    }

    public void setMTR_NAME(String MTR_NAME) {
        this.MTR_NAME = MTR_NAME;
    }

    public int getMTR_CAL() {
        return MTR_CAL;
    }

    public void setMTR_CAL(int MTR_CAL) {
        this.MTR_CAL = MTR_CAL;
    }

    public String getMTR_DETAIL() {
        return MTR_DETAIL;
    }

    public void setMTR_DETAIL(String MTR_DETAIL) {
        this.MTR_DETAIL = MTR_DETAIL;
    }

    public int getMTR_DIV() {
        return MTR_DIV;
    }

    public void setMTR_DIV(int MTR_DIV) {
        this.MTR_DIV = MTR_DIV;
    }

    public int getMTR_PIC_NO() {
        return MTR_PIC_NO;
    }

    public void setMTR_PIC_NO(int MTR_PIC_NO) {
        this.MTR_PIC_NO = MTR_PIC_NO;
    }


}
