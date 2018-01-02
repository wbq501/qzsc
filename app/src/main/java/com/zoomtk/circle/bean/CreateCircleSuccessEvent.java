package com.zoomtk.circle.bean;

/**
 * Created by wbq501 on 2017/12/19.
 */

public class CreateCircleSuccessEvent {
    private String mString;

    public CreateCircleSuccessEvent() {
    }

    public CreateCircleSuccessEvent(String mString) {
        this.mString = mString;
    }

    public String getString() {
        return mString;
    }

    public CreateCircleSuccessEvent setString(String mString) {
        this.mString = mString;
        return this;
    }
}
