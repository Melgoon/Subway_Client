package kr.or.ddit.sw.vo.tellsub;

import java.io.Serializable;

public class TellSubOwnerVO implements Serializable {
    private int TellsubOwnerNo;
    private String TellsubOwnerChk;
    private int OwnerCode;

    public int getTellsubOwnerNo() {
        return TellsubOwnerNo;
    }

    public void setTellsubOwnerNo(int tellsubOwnerNo) {
        TellsubOwnerNo = tellsubOwnerNo;
    }

    public String getTellsubOwnerChk() {
        return TellsubOwnerChk;
    }

    public void setTellsubOwnerChk(String tellsubOwnerChk) {
        TellsubOwnerChk = tellsubOwnerChk;
    }

    public int getOwnerCode() {
        return OwnerCode;
    }

    public void setOwnerCode(int ownerCode) {
        OwnerCode = ownerCode;
    }
}
