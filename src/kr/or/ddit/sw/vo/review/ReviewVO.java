package kr.or.ddit.sw.vo.review;

import java.io.Serializable;

public class ReviewVO implements Serializable {

    private int order_his_no;
    private String review_content;
    private String review_name;
    private int review_no;
    private int review_start;


    public int getOrder_his_no() {
        return order_his_no;
    }

    public void setOrder_his_no(int order_his_no) {
        this.order_his_no = order_his_no;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public String getReview_name() {
        return review_name;
    }

    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    public int getReview_no() { return review_no; }

    public void setReview_no(int review_no) {
        this.review_no = review_no;
    }

    public int getReview_start() {
        return review_start;
    }

    public void setReview_start(int review_start) {
        this.review_start = review_start;
    }


}
