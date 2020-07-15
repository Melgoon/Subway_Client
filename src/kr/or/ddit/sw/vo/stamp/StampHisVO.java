package kr.or.ddit.sw.vo.stamp;

import java.io.Serializable;

public class StampHisVO implements Serializable {
    private String mem_id;
    private int stamp_count;

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public int getStamp_count() {
        return stamp_count;
    }

    public void setStamp_count(int stamp_count) {
        this.stamp_count = stamp_count;
    }
}
