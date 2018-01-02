package com.zoomtk.circle.Interface;

import com.zoomtk.circle.base.BaseJson;

/**
 * Created by wbq501 on 2017/12/6.
 */

public interface ResponseBack<T> {
    void success(T t) throws Exception;
    void error(String errormsg);
}
