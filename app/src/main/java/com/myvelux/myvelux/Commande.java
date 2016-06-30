package com.myvelux.myvelux;

import java.io.Serializable;

/**
 * Created by geoffrey on 03/05/16.
 * Modified by julien on 20/05/16.
 */
public class Commande implements Serializable {

    private String id;
    private String room;
    private String action;
    private String actionPrice;
    private String Range;
    private String type;
    private String version;
    private String size;
    private String fitting;
    private String idClient;
    private String refArticle;
    private String prixHTVelux;
    private String prixTTCVelux;
    private String refFitting;
    private String prixHTFitting;
    private String prixTTCFitting;
    private String update;

    public Commande() {
        this.action = " ";
        this.actionPrice = " ";
        this.update = "false";
    }

    public Commande(String id, String room, String action, String actionPrice, String range, String type, String version, String size, String fitting, String idClient) {
        this.id = id;
        this.room = room;
        this.action = action;
        this.actionPrice = actionPrice;
        Range = range;
        this.type = type;
        this.version = version;
        this.size = size;
        this.fitting = fitting;
        this.idClient = idClient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getRefArticle() {
        return refArticle;
    }

    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }

    public String getRefFitting() {
        return refFitting;
    }

    public void setRefFitting(String refFitting) {
        this.refFitting = refFitting;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getPrixHTVelux() {
        return prixHTVelux;
    }

    public void setPrixHTVelux(String prixHTVelux) {
        this.prixHTVelux = prixHTVelux;
    }

    public String getPrixTTCVelux() {
        return prixTTCVelux;
    }

    public void setPrixTTCVelux(String prixTTCVelux) {
        this.prixTTCVelux = prixTTCVelux;
    }

    public String getPrixTTCFitting() {
        return prixTTCFitting;
    }

    public void setPrixTTCFitting(String prixTTCFitting) {
        this.prixTTCFitting = prixTTCFitting;
    }

    public String getPrixHTFitting() {
        return prixHTFitting;
    }

    public void setPrixHTFitting(String prixHTFitting) {
        this.prixHTFitting = prixHTFitting;
    }

}
