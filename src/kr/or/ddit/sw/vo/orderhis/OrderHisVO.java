package kr.or.ddit.sw.vo.orderhis;

import java.io.Serializable;

public class OrderHisVO implements Serializable {
    private String Order_His_No;
    private String Order_His_Content;
    private String Order_His_Price;
    private String Order_His_Br;
    private String Pay_No;
    private String Order_His_Time;
    private String Order_His_Date;


    public String getOrder_His_No() {
        return Order_His_No;
    }

    public void setOrder_His_No(String order_His_No) {
        Order_His_No = order_His_No;
    }

    public String getOrder_His_Content() {
        return Order_His_Content;
    }

    public void setOrder_His_Content(String order_His_Content) {
        Order_His_Content = order_His_Content;
    }

    public String getOrder_His_Price() {
        return Order_His_Price;
    }

    public void setOrder_His_Price(String order_His_Price) {
        Order_His_Price = order_His_Price;
    }

    public String getOrder_His_Br() {
        return Order_His_Br;
    }

    public void setOrder_His_Br(String order_His_Br) {
        Order_His_Br = order_His_Br;
    }

    public String getPay_No() {
        return Pay_No;
    }

    public void setPay_No(String pay_No) {
        Pay_No = pay_No;
    }

    public String getOrder_His_Time() {
        return Order_His_Time;
    }

    public void setOrder_His_Time(String order_His_Time) {
        Order_His_Time = order_His_Time;
    }

    public String getOrder_His_Date() {
        return Order_His_Date;
    }

    public void setOrder_His_Date(String order_His_Date) {
        Order_His_Date = order_His_Date;
    }
}
