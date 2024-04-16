package com.daksh.vasavievents;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Event {
    private String name;

    public String getGetDesc() {
        return getDesc;
    }

    public void setGetDesc(String getDesc) {
        this.getDesc = getDesc;
    }

    private String getDesc;

    private List<Image> images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
