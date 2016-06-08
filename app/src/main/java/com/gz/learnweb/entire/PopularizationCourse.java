package com.gz.learnweb.entire;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gz on 15/9/12.
 */
public class PopularizationCourse implements Serializable {
    private String pageImage;
    private List<String> images;
    private String title;
    private String detail;

    public String getPageImage() {
        return pageImage;
    }

    public void setPageImage(String pageImage) {
        this.pageImage = pageImage;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
