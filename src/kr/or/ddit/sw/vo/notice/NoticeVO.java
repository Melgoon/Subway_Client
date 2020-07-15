package kr.or.ddit.sw.vo.notice;

import java.io.Serializable;

public class NoticeVO implements Serializable {
    private int NOTICE_NO;
    private String NOTICE_NAME;
    private String NOTICE_CONTENT;
    private String NOTICE_CHK;
    private String NOTICE_DATE;

    public String getNOTICE_DATE() {
        return NOTICE_DATE;
    }

    public void setNOTICE_DATE(String NOTICE_DATE) {
        this.NOTICE_DATE = NOTICE_DATE;
    }

    public NoticeVO() {

    }

    public int getNOTICE_NO() {
        return NOTICE_NO;
    }

    public void setNOTICE_NO(int NOTICE_NO) {
        this.NOTICE_NO = NOTICE_NO;
    }

    public String getNOTICE_NAME() {
        return NOTICE_NAME;
    }

    public void setNOTICE_NAME(String NOTICE_NAME) {
        this.NOTICE_NAME = NOTICE_NAME;
    }

    public String getNOTICE_CONTENT() {
        return NOTICE_CONTENT;
    }

    public void setNOTICE_CONTENT(String NOTICE_CONTENT) {
        this.NOTICE_CONTENT = NOTICE_CONTENT;
    }

    public String getNOTICE_CHK() {
        return NOTICE_CHK;
    }

    public void setNOTICE_CHK(String NOTICE_CHK) {
        this.NOTICE_CHK = NOTICE_CHK;
    }
}
