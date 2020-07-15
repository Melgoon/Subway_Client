package kr.or.ddit.sw.vo.owner;

import java.io.Serializable;

public class TempOwnerVO implements Serializable {

    private int jijumCode;
    private String ownerPW;

    public int getJijumCode() {
        return jijumCode;
    }

    public void setJijumCode(int jijumCode) {
        this.jijumCode = jijumCode;
    }

    public String getOwnerPW() {
        return ownerPW;
    }

    public void setOwnerPW(String ownerPW) {
        this.ownerPW = ownerPW;
    }
}
