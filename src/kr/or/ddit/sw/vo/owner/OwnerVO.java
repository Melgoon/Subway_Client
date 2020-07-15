package kr.or.ddit.sw.vo.owner;

import java.io.Serializable;

public class OwnerVO implements Serializable{
    private int owner_code;
    private String owner_pw;
    private String owner_email;
    private String owner_addr;
    private String owner_jijum;
    private String owner_tel;
    private String owner_chk;

    public OwnerVO() {
    }

    public OwnerVO(int owner_code, String owner_pw, String owner_email, String owner_addr, String owner_jijum, String owner_tel, String owner_chk) {
        this.owner_code = owner_code;
        this.owner_pw = owner_pw;
        this.owner_email = owner_email;
        this.owner_addr = owner_addr;
        this.owner_jijum = owner_jijum;
        this.owner_tel = owner_tel;
        this.owner_chk = owner_chk;
    }

    public int getOwner_code() {
        return owner_code;
    }

    public void setOwner_code(int owner_code) {
        this.owner_code = owner_code;
    }

    public String getOwner_pw() {
        return owner_pw;
    }

    public void setOwner_pw(String owner_pw) {
        this.owner_pw = owner_pw;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public String getOwner_addr() {
        return owner_addr;
    }

    public void setOwner_addr(String owner_addr) {
        this.owner_addr = owner_addr;
    }

    public String getOwner_jijum() {
        return owner_jijum;
    }

    public void setOwner_jijum(String owner_jijum) {
        this.owner_jijum = owner_jijum;
    }

    public String getOwner_tel() {
        return owner_tel;
    }

    public void setOwner_tel(String owner_tel) {
        this.owner_tel = owner_tel;
    }

    public String getOwner_chk() {
        return owner_chk;
    }

    public void setOwner_chk(String owner_chk) {
        this.owner_chk = owner_chk;
    }
};
