package com.myvelux.myvelux;

import java.io.Serializable;

/**
 * Created by geoffrey on 03/05/16.
 */
public class Commande implements Serializable {

    private String room;
    private String action;
    private String actionPrice;
    private String Range;
    private String type;
    private String version;
    private String size;
    private String fitting;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionPrice() {
        return actionPrice;
    }

    public void setActionPrice(String actionPrice) {
        this.actionPrice = actionPrice;
    }

    public String getRange() {
        return Range;
    }

    public void setRange(String range) {
        Range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFitting() {
        return fitting;
    }

    public void setFitting(String fitting) {
        this.fitting = fitting;
    }
}
