package com.zoomtk.circle.base;

import java.io.Serializable;

/**数据接口实体类
 * Created by win 10 on 2017/10/11.
 */

public class BaseJson<T> implements Serializable{
    private String resultCode;
    private String resultInfo;
    private T result;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "resultCode='" + resultCode + '\'' +
                ", resultInfo='" + resultInfo + '\'' +
                ", result=" + result +
                '}';
    }
}
