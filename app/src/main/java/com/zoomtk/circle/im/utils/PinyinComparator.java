package com.zoomtk.circle.im.utils;

import com.zoomtk.circle.bean.Content;

import java.util.Comparator;

/**
 * Created by Administrator on 2015/9/5.
 */
public class PinyinComparator implements Comparator<Content> {

    public int compare(Content content1, Content content2) {
        if (content1.getLetter().equals("@") || content2.getLetter().equals("#")) {
            return -1;
        } else if (content1.getLetter().equals("#") || content2.getLetter().equals("@")) {
            return 1;
        } else {
            return content1.getLetter().compareTo(content2.getLetter());
        }
    }

}
