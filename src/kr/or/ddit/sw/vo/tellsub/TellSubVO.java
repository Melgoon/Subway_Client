package kr.or.ddit.sw.vo.tellsub;
import java.io.Serializable;


public class TellSubVO implements Serializable {
    private String Tellsub_no;
    private String Tellsub_name;
    private String Tellsub_content;
    private String Tellsub_rep;
    private String Tellsub_jijum;
    private String Tellsub_visitdate;
    private String Mem_id;
    private String Tellsub_mem_chk;

    public String getTellsub_no() {
        return Tellsub_no;
    }

    public void setTellsub_no(String tellsub_no) {
        Tellsub_no = tellsub_no;
    }

    public String getTellsub_name() {
        return Tellsub_name;
    }

    public void setTellsub_name(String tellsub_name) {
        Tellsub_name = tellsub_name;
    }

    public String getTellsub_content() {
        return Tellsub_content;
    }

    public void setTellsub_content(String tellsub_content) {
        Tellsub_content = tellsub_content;
    }

    public String getTellsub_rep() {
        return Tellsub_rep;
    }

    public void setTellsub_rep(String tellsub_rep) {
        Tellsub_rep = tellsub_rep;
    }

    public String getTellsub_jijum() {
        return Tellsub_jijum;
    }

    public void setTellsub_jijum(String tellsub_jijum) {
        Tellsub_jijum = tellsub_jijum;
    }

    public String getTellsub_visitdate() {
        return Tellsub_visitdate;
    }

    public void setTellsub_visitdate(String tellsub_visitdate) {
        Tellsub_visitdate = tellsub_visitdate;
    }

    public String getMem_id() {
        return Mem_id;
    }

    public void setMem_id(String mem_id) {
        Mem_id = mem_id;
    }

    public String getTellsub_mem_chk() {
        return Tellsub_mem_chk;
    }

    public void setTellsub_mem_chk(String tellsub_mem_chk) {
        Tellsub_mem_chk = tellsub_mem_chk;
    }
}
