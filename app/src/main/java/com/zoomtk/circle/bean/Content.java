package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/5.
 */
public class Content implements Serializable{

        private String touxiang;
        private String letter;
        private String name;
        private String id;
        private String is_checked;
     //   private int id;

    public String getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(String is_checked) {
        this.is_checked = is_checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public Content() {
    }

    public Content(String letter, String name) {
        this.letter = letter;
        this.name = name;

    }
    public Content(String letter, String name,String id,String touxiang) {
        this.letter = letter;
        this.name = name;
        this.touxiang=touxiang;
        this.id=id;
    }



    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Content{" +
                "letter='" + letter + '\'' +
                ", name='" + name + '\'' +
               '}';
    }
}
