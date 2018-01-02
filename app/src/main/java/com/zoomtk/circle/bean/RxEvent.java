package com.zoomtk.circle.bean;

/**
 * Created by wbq501 on 2017/12/7.
 */

public class RxEvent {
    private int code;
    private Object object;
    public RxEvent(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxEvent(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
