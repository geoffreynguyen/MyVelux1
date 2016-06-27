package com.myvelux.myvelux;

/**
 * Created by Julien on 31/05/2016.
 */
public class Produit {
    private String COL_ID_PRODUCT;
    private String COL_FAMILLY_PRODUCT;
    private String COL_RANGE_PRODUCT;
    private String COL_TYPE_PRODUCT;
    private String COL_VERSION_PRODUCT;
    private String COL_LIBEL_ARTICLE;
    private String COL_REF_DIMENSION;
    private String COL_DIMENSION;
    private String COL_REF_ARTCILE;
    private String COL_PRICE_HT;
    private String COL_PRICE_TTC;

    public Produit() {
    }

    public Produit(String COL_ID_PRODUCT, String COL_FAMILLY_PRODUCT, String COL_RANGE_PRODUCT, String COL_TYPE_PRODUCT, String COL_VERSION_PRODUCT, String COL_LIBEL_ARTICLE, String COL_REF_DIMENSION, String COL_DIMENSION, String COL_REF_ARTCILE, String COL_PRICE_HT, String COL_PRICE_TTC) {
        this.COL_ID_PRODUCT = COL_ID_PRODUCT;
        this.COL_FAMILLY_PRODUCT = COL_FAMILLY_PRODUCT;
        this.COL_RANGE_PRODUCT = COL_RANGE_PRODUCT;
        this.COL_TYPE_PRODUCT = COL_TYPE_PRODUCT;
        this.COL_VERSION_PRODUCT = COL_VERSION_PRODUCT;
        this.COL_LIBEL_ARTICLE = COL_LIBEL_ARTICLE;
        this.COL_REF_DIMENSION = COL_REF_DIMENSION;
        this.COL_DIMENSION = COL_DIMENSION;
        this.COL_REF_ARTCILE= COL_REF_ARTCILE;
        this.COL_PRICE_HT = COL_PRICE_HT;
        this.COL_PRICE_TTC = COL_PRICE_TTC;
    }

    public String getCOL_ID_PRODUCT() {
        return COL_ID_PRODUCT;
    }

    public void setCOL_ID_PRODUCT(String COL_ID_PRODUCT) {
        this.COL_ID_PRODUCT = COL_ID_PRODUCT;
    }

    public String getCOL_FAMILLY_PRODUCT() {
        return COL_FAMILLY_PRODUCT;
    }

    public void setCOL_FAMILLY_PRODUCT(String COL_FAMILLY_PRODUCT) {
        this.COL_FAMILLY_PRODUCT = COL_FAMILLY_PRODUCT;
    }

    public String getCOL_RANGE_PRODUCT() {
        return COL_RANGE_PRODUCT;
    }

    public void setCOL_RANGE_PRODUCT(String COL_RANGE_PRODUCT) {
        this.COL_RANGE_PRODUCT = COL_RANGE_PRODUCT;
    }

    public String getCOL_TYPE_PRODUCT() {
        return COL_TYPE_PRODUCT;
    }

    public void setCOL_TYPE_PRODUCT(String COL_TYPE_PRODUCT) {
        this.COL_TYPE_PRODUCT = COL_TYPE_PRODUCT;
    }

    public String getCOL_VERSION_PRODUCT() {
        return COL_VERSION_PRODUCT;
    }

    public void setCOL_VERSION_PRODUCT(String COL_VERSION_PRODUCT) {
        this.COL_VERSION_PRODUCT = COL_VERSION_PRODUCT;
    }

    public String getCOL_LIBEL_ARTICLE() {
        return COL_LIBEL_ARTICLE;
    }

    public void setCOL_LIBEL_ARTICLE(String COL_LIBEL_ARTICLE) {
        this.COL_LIBEL_ARTICLE = COL_LIBEL_ARTICLE;
    }

    public String getCOL_REF_DIMENSION() {
        return COL_REF_DIMENSION;
    }

    public void setCOL_REF_DIMENSION(String COL_REF_DIMENSION) {
        this.COL_REF_DIMENSION = COL_REF_DIMENSION;
    }

    public String getCOL_DIMENSION() {
        return COL_DIMENSION;
    }

    public void setCOL_DIMENSION(String COL_DIMENSION) {
        this.COL_DIMENSION = COL_DIMENSION;
    }

    public String getCOL_REF_ARTCILE() {
        return COL_REF_ARTCILE;
    }

    public void setCOL_REF_ARTCILE(String COL_REF_ARTCILE) {
        this.COL_REF_ARTCILE = COL_REF_ARTCILE;
    }

    public String getCOL_PRICE_HT() {
        return COL_PRICE_HT;
    }

    public void setCOL_PRICE_HT(String COL_PRICE_HT) {
        this.COL_PRICE_HT = COL_PRICE_HT;
    }

    public String getCOL_PRICE_TTC() {
        return COL_PRICE_TTC;
    }

    public void setCOL_PRICE_TTC(String COL_PRICE_TTC) {
        this.COL_PRICE_TTC = COL_PRICE_TTC;
    }

}