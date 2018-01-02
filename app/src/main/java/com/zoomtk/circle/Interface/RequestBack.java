package com.zoomtk.circle.Interface;

import com.zoomtk.circle.base.BaseJson;

/**
 * Created by wbq501 on 2017/10/27.
 */

public interface RequestBack<T> {
    void success(BaseJson<T> msg) throws Exception;
    void error(String errormsg);
}
