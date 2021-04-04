package com.example.googleanalyticsassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.googleanalyticsassignment.adapter.RecyclerProductsAdapter;
import com.example.googleanalyticsassignment.model.Product;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerProductsAdapter recyclerProductsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    if(getIntent().getStringExtra("productsFood")!=null){
        recyclerProductsAdapter=new RecyclerProductsAdapter(ProductsActivity.this,insertProductFood());
        recyclerView.setAdapter(recyclerProductsAdapter);
    }else if(getIntent().getStringExtra("productsClothes")!=null){
        recyclerProductsAdapter=new RecyclerProductsAdapter(ProductsActivity.this,insertProductClothes());
        recyclerView.setAdapter(recyclerProductsAdapter);
    }else if (getIntent().getStringExtra("productsElectronic")!=null){
        recyclerProductsAdapter=new RecyclerProductsAdapter(ProductsActivity.this,insertProductElectronic());
        recyclerView.setAdapter(recyclerProductsAdapter);
        }

    }

    List<Product>  insertProductFood( ){
        List foodList=new ArrayList<Product>();
        foodList.add(new Product("foodX","food_urlX","food_descriptionX"));
        foodList.add(new Product("foodY","food_urlY","food_descriptionY"));
        foodList.add(new Product("foodZ","food_urlZ","food_descriptionZ"));
        foodList.add(new Product("foodA","food_urlA","food_descriptionA"));
        return foodList;
    }
    List<Product>  insertProductClothes(){
        List clothesList=new ArrayList<Product>();
        clothesList.add(new Product("clothesX","clothes_urlX","clothes_descriptionX"));
        clothesList.add(new Product("clothesY","clothes_urlY","clothes_descriptionY"));
        clothesList.add(new Product("clothesZ","clothes_urlZ","clothes_descriptionZ"));
        clothesList.add(new Product("clothesA","clothes_urlA","clothes_descriptionA"));
        return clothesList;
    }
    List<Product>  insertProductElectronic(){
        List electronicList=new ArrayList<Product>();
        electronicList.add(new Product("electronicX","electronic_urlX","electronic_descriptionX"));
        electronicList.add(new Product("electronicY","electronic_urlY","electronic_descriptionY"));
        electronicList.add(new Product("electronicZ","electronic_urlZ","electronic_descriptionZ"));
        electronicList.add(new Product("electronicA","electronic_urlA","electronic_descriptionA"));
        return electronicList;
    }
}
