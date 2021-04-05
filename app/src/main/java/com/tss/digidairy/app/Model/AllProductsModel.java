package com.tss.digidairy.app.Model;

public class AllProductsModel {
    int mP_id;
    String mProduct_title;
    String mP_Discreption;
    int statusstock;
    String address_shop;
    String mPrice;
    String mImage_url;
    String mP_Size;
    String otherimage;
    int category_id;
    int subcategory_id;
    int added_by;

    public AllProductsModel(int mP_id, String mProduct_title, String mP_Discreption, int statusstock, String address_shop, String mPrice, String mImage_url, String mP_Size, String otherimage, int category_id, int subcategory_id, int added_by) {
        this.mP_id = mP_id;
        this.mProduct_title = mProduct_title;
        this.mP_Discreption = mP_Discreption;
        this.statusstock = statusstock;
        this.address_shop = address_shop;
        this.mPrice = mPrice;
        this.mImage_url = mImage_url;
        this.mP_Size = mP_Size;
        this.otherimage = otherimage;
        this.category_id = category_id;
        this.subcategory_id = subcategory_id;
        this.added_by = added_by;
    }
    public int getmP_id() {
        return mP_id;
    }

    public void setmP_id(int mP_id) {
        this.mP_id = mP_id;
    }

    public String getmProduct_title() {
        return mProduct_title;
    }

    public void setmProduct_title(String mProduct_title) {
        this.mProduct_title = mProduct_title;
    }

    public String getmP_Discreption() {
        return mP_Discreption;
    }

    public void setmP_Discreption(String mP_Discreption) {
        this.mP_Discreption = mP_Discreption;
    }

    public int getStatusstock() {
        return statusstock;
    }

    public void setStatusstock(int statusstock) {
        this.statusstock = statusstock;
    }

    public String getAddress_shop() {
        return address_shop;
    }

    public void setAddress_shop(String address_shop) {
        this.address_shop = address_shop;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImage_url() {
        return mImage_url;
    }

    public void setmImage_url(String mImage_url) {
        this.mImage_url = mImage_url;
    }

    public String getmP_Size() {
        return mP_Size;
    }

    public void setmP_Size(String mP_Size) {
        this.mP_Size = mP_Size;
    }

    public String getOtherimage() {
        return otherimage;
    }

    public void setOtherimage(String otherimage) {
        this.otherimage = otherimage;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }
}
