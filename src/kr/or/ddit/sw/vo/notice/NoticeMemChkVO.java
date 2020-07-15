package kr.or.ddit.sw.vo.notice;

import java.io.Serializable;

public class NoticeMemChkVO implements Serializable {

    private int NOTICE_NO;
    private int MEM_CHECK;
    private String MEM_ID;

    public NoticeMemChkVO(int NOTICE_NO, int MEM_CHECK, String MEM_ID) {
        this.NOTICE_NO = NOTICE_NO;
        this.MEM_CHECK = MEM_CHECK;
        this.MEM_ID = MEM_ID;
    }

    public NoticeMemChkVO() {

    }

    public int getNOTICE_NO() {
        return NOTICE_NO;
    }

    public void setNOTICE_NO(int NOTICE_NO) {
        this.NOTICE_NO = NOTICE_NO;
    }

    public int getMEM_CHECK() {
        return MEM_CHECK;
    }

    public void setMEM_CHECK(int MEM_CHECK) {
        this.MEM_CHECK = MEM_CHECK;
    }

    public String getMEM_ID() {
        return MEM_ID;
    }

    public void setMEM_ID(String MEM_ID) {
        this.MEM_ID = MEM_ID;
    }
}
