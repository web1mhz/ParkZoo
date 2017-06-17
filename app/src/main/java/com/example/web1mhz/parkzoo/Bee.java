package com.example.web1mhz.parkzoo;

import java.io.Serializable;

/**
 * Created by HP on 2017-05-26.
 */

public class Bee implements Serializable{
    String beeID;
    String kor_name;
    String eng_name;
    String status;
    String img_url;
    String date;
    String source;

    public Bee(String kor_name, String eng_name, String status, String img_url, String date, String source) {
        this.kor_name = kor_name;
        this.eng_name = eng_name;
        this.status = status;
        this.img_url = img_url;
        this.date = date;
        this.source = source;
    }

    public String getSource() {

        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bee(String beeID, String kor_name, String eng_name, String status, String img_url) {
        this.beeID = beeID;
        this.kor_name = kor_name;
        this.eng_name = eng_name;
        this.status = status;
        this.img_url = img_url;
    }

    public Bee(String kor_name, String eng_name, String status, String img_url) {
        this.kor_name = kor_name;
        this.eng_name = eng_name;
        this.status = status;
        this.img_url = img_url;
    }

    public Bee(String kor_name, String eng_name, String img_url) {
        this.kor_name = kor_name;
        this.eng_name = eng_name;
        this.img_url = img_url;
    }

    public String getBeeID() {
        return beeID;
    }

    public void setBeeID(String beeID) {
        this.beeID = beeID;
    }

    public String getKor_name() {
        return kor_name;
    }

    public void setKor_name(String kor_name) {
        this.kor_name = kor_name;
    }

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
