package com.myvelux.myvelux;

import java.util.HashMap;

/**
 * Created by geoffrey on 21/04/16.
 */
public class Room {
    public String title;
    public String image;
    HashMap<String,String> map;

    public Room(String title, String image){
        this.title = title;
        this.image = image;
    }

    public HashMap<String,String> getHashMap(){
        map = new HashMap<String, String>();
        map.put("title", this.title);
        map.put("img", this.image);
        return map;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
