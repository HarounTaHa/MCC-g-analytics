package com.example.googleanalyticsassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleanalyticsassignment.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {
  TextView nameProduct ;
  TextView specificationProduct ;
  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        nameProduct=findViewById(R.id.textView_name);
        specificationProduct=findViewById(R.id.textView_specification);
        imageView=findViewById(R.id.imageView);
        Product product=(Product) getIntent().getExtras().getSerializable("DATA_PRODUCT");
         nameProduct.setText(product.getNameProduct());
         specificationProduct.setText(product.getSpecificationProduct());
         imageView.setImageResource(R.drawable.woman);
    }
}