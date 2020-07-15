package kr.or.ddit.sw.vo.foodmtr;

import java.io.Serializable;

public class MtrPicVO implements Serializable {
    private int MTR_PIC_NO;
    private String MTR_PIC_ADDR;

    public int getMTR_PIC_NO() {
        return MTR_PIC_NO;
    }

    public void setMTR_PIC_NO(int MTR_PIC_NO) {
        this.MTR_PIC_NO = MTR_PIC_NO;
    }

    public String getMTR_PIC_ADDR() {
        return MTR_PIC_ADDR;
    }

    public void setMTR_PIC_ADDR(String MTR_PIC_ADDR) {
        this.MTR_PIC_ADDR = MTR_PIC_ADDR;
    }
}