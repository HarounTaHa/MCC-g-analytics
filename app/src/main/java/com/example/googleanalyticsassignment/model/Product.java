package com.example.googleanalyticsassignment.model;

import android.os.Parcelable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    String nameProduct;
    String urlImage;
    String specificationProduct;

    public Product(String nameProduct, String urlImage, String specificationProduct) {
        this.nameProduct = nameProduct;
        this.urlImage = urlImage;
        this.specificationProduct = specificationProduct;
    }

    public Product() {
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSpecificationProduct() {
        return specificationProduct;
    }

    public void setSpecificationProduct(String specificationProduct) {
        this.specificationProduct = specificationProduct;
    }

}

