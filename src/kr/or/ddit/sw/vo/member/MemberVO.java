package kr.or.ddit.sw.vo.member;

import java.io.Serializable;


public class MemberVO implements Serializable {
    private String MEM_ID;
    private String MEM_PW;
    private String MEM_NAME;
    private String MEM_BIRTH;
    private String MEM_AGE;
    private String MEM_GENDER;
    private String MEM_EMAIL;
    private String MEM_HP;
    private String MEM_ADDR;

    public MemberVO() {
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "MEM_ID='" + MEM_ID + '\'' +
                ", MEM_PW='" + MEM_PW + '\'' +
                ", MEM_NAME='" + MEM_NAME + '\'' +
                ", MEM_BIRTH='" + MEM_BIRTH + '\'' +
                ", MEM_AGE='" + MEM_AGE + '\'' +
                ", MEM_GENDER='" + MEM_GENDER + '\'' +
                ", MEM_EMAIL='" + MEM_EMAIL + '\'' +
                ", MEM_HP='" + MEM_HP + '\'' +
                ", MEM_ADDR='" + MEM_ADDR + '\'' +
                '}';
    }

    public MemberVO(String MEM_ID,
                    String MEM_PW, String MEM_NAME, String MEM_BIRTH, String MEM_AGE,
                    String MEM_GENDER, String MEM_EMAIL, String MEM_HP, String MEM_ADDR) {
        this.MEM_ID = MEM_ID;
        this.MEM_PW = MEM_PW;
        this.MEM_NAME = MEM_NAME;
        this.MEM_BIRTH = MEM_BIRTH;
        this.MEM_AGE = MEM_AGE;
        this.MEM_GENDER = MEM_GENDER;
        this.MEM_EMAIL = MEM_EMAIL;
        this.MEM_HP = MEM_HP;
        this.MEM_ADDR = MEM_ADDR;
    }

    public String getMEM_NAME() {
        return MEM_NAME;
    }

    public void setMEM_NAME(String MEM_NAME) {
        this.MEM_NAME = MEM_NAME;
    }

    public String getMEM_AGE() {
        return MEM_AGE;
    }

    public void setMEM_AGE(String MEM_AGE) {
        this.MEM_AGE = MEM_AGE;
    }


    public String getMEM_ID() {
        return MEM_ID;
    }

    public void setMEM_ID(String MEM_ID) {
        this.MEM_ID = MEM_ID;
    }

    public String getMEM_PW() {
        return MEM_PW;
    }

    public void setMEM_PW(String MEM_PW) {
        this.MEM_PW = MEM_PW;
    }

    public String getMEM_BIRTH() {
        return MEM_BIRTH;
    }

    public void setMEM_BIRTH(String MEM_BIRTH) {
        this.MEM_BIRTH = MEM_BIRTH;
    }

    public String getMEM_GENDER() {
        return MEM_GENDER;
    }

    public void setMEM_GENDER(String MEM_GENDER) {
        this.MEM_GENDER = MEM_GENDER;
    }

    public String getMEM_EMAIL() {
        return MEM_EMAIL;
    }

    public void setMEM_EMAIL(String MEM_EMAIL) {
        this.MEM_EMAIL = MEM_EMAIL;
    }

    public String getMEM_HP() {
        return MEM_HP;
    }

    public void setMEM_HP(String MEM_HP) {
        this.MEM_HP = MEM_HP;
    }

    public String getMEM_ADDR() {
        return MEM_ADDR;
    }

    public void setMEM_ADDR(String MEM_ADDR) {
        this.MEM_ADDR = MEM_ADDR;
    }
}
