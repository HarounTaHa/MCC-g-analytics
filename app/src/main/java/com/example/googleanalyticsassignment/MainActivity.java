package com.example.googleanalyticsassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;


import com.example.googleanalyticsassignment.model.Product;
import com.example.googleanalyticsassignment.utility.Service;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button foodButton,clothesButton,electronicButton;
    Intent intent;
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseFirestore db;
    long timeEntry;
    @Override
    protected void onRestart() {
        super.onRestart();
        // init class Service in package utility
        Date serviceDate=new Service().calendarDate();
         timeEntry =serviceDate.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodButton=findViewById(R.id.food_button);
        clothesButton=findViewById(R.id.clothes_button);
        electronicButton=findViewById(R.id.electronic_button);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();
        Date serviceDate=new Service().calendarDate();
        timeEntry =serviceDate.getTime();
        Log.e("timeEntry",timeEntry+"");
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsFood","food");
                Log.e("timeEntry",timeEntry+"");
                long timeLeave = System.currentTimeMillis();
                Log.e("timeLeave",timeLeave+"");
                long timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");
                startActivity(intent);

            }
        });
        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsClothes","clothes");
                Log.e("timeEntry",timeEntry+"");
                long timeLeave = System.currentTimeMillis();
                Log.e("timeLeave",timeLeave+"");
                long timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");
                startActivity(intent);
            }
        });
        electronicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsElectronic","electronic");
                Log.e("timeEntry",timeEntry+"");
                long timeLeave = System.currentTimeMillis();
                Log.e("timeLeave",timeLeave+"");
                long timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");
                startActivity(intent);
            }
        });


    }


}