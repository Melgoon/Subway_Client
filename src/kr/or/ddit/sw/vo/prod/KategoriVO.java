package kr.or.ddit.sw.vo.prod;

import java.io.Serializable;

public class KategoriVO implements Serializable {
    private int kate_no;
    private String kate_name;


    public int getKate_no() {
        return kate_no;
    }

    public void setKate_no(int kate_no) {
        this.kate_no = kate_no;
    }

    public String getKate_name() {
        return kate_name;
    }

    public void setKate_name(String kate_name) {
        this.kate_name = kate_name;
    }
}
