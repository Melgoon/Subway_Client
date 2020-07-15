package kr.or.ddit.sw.vo.tellsub;

import java.io.Serializable;

public class TellSubMemVO implements Serializable {
    private int TellsubMemNo;
    private String TellsubMemChk;
    private String MemId;

    public int getTellsubMemNo() {
        return TellsubMemNo;
    }

    public void setTellsubMemNo(int tellsubMemNo) {
        TellsubMemNo = tellsubMemNo;
    }

    public String getTellsubMemChk() {
        return TellsubMemChk;
    }

    public void setTellsubMemChk(String tellsubMemChk) {
        TellsubMemChk = tellsubMemChk;
    }

    public String getMemId() {
        return MemId;
    }

    public void setMemId(String memId) {
        MemId = memId;
    }
}
