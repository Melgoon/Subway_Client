package kr.or.ddit.sw.vo.honeycombi;

import java.io.Serializable;

public class HoneyCombiVO implements Serializable {
    private int HONEY_NO;
    private String HONEY_NAME;
    private String HONEY_CONTENT;
    private int HONEY_GOOD;
    private int HONEY_BAD;
    private String MEM_ID;
    private String HONEY_MENU;

    public int getHONEY_NO() {
        return HONEY_NO;
    }

    public void setHONEY_NO(int HONEY_NO) {
        this.HONEY_NO = HONEY_NO;
    }

    public String getHONEY_NAME() {
        return HONEY_NAME;
    }

    public void setHONEY_NAME(String HONEY_NAME) {
        this.HONEY_NAME = HONEY_NAME;
    }

    public String getHONEY_CONTENT() {
        return HONEY_CONTENT;
    }

    public void setHONEY_CONTENT(String HONEY_CONTENT) {
        this.HONEY_CONTENT = HONEY_CONTENT;
    }

    public int getHONEY_GOOD() {
        return HONEY_GOOD;
    }

    public void setHONEY_GOOD(int HONEY_GOOD) {
        this.HONEY_GOOD = HONEY_GOOD;
    }

    public int getHONEY_BAD() {
        return HONEY_BAD;
    }

    public void setHONEY_BAD(int HONEY_BAD) {
        this.HONEY_BAD = HONEY_BAD;
    }

    public String getMEM_ID() {
        return MEM_ID;
    }

    public void setMEM_ID(String MEM_ID) {
        this.MEM_ID = MEM_ID;
    }

    public String getHONEY_MENU() {
        return HONEY_MENU;
    }

    public void setHONEY_MENU(String HONEY_MENU) {
        this.HONEY_MENU = HONEY_MENU;
    }
}
