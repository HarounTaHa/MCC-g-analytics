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
import android.widget.Toast;

import com.example.googleanalyticsassignment.adapter.RecyclerProductsAdapter;
import com.example.googleanalyticsassignment.model.Product;
import com.example.googleanalyticsassignment.utility.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerProductsAdapter recyclerProductsAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;
    String namePage;
    long timeEntry, timeLeave, timeSpentPerSecond;
    String userId;
    FirebaseFirestore db;
    @Override
    protected void onRestart() {
        super.onRestart();
        // init class Service in package utility
        Date serviceDate = new Service().calendarDate();
        timeEntry = serviceDate.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
 //        get NameScreen
        namePage=this.getClass().getSimpleName();
//       ---------------------Instance-----------------------------------
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();
//-----------------------------------------------------------
//      ---------- get current time
        Date serviceDate=new Service().calendarDate();
        timeEntry =serviceDate.getTime();
//-----------------------------------------------------------
        if (getIntent().getStringExtra("productsFood") != null) {
              //        ---------------------Track Screen
                        new Service().trackScreen(mFirebaseAnalytics, "Products Food Screen");
                        //   ------------------------------------------------------
                        recyclerProductsAdapter = new RecyclerProductsAdapter(ProductsActivity.this, mFirebaseAnalytics, insertProductFood());
                        recyclerView.setAdapter(recyclerProductsAdapter);
        } else if (getIntent().getStringExtra("productsClothes") != null) {
                 //        ---------------------Track Screen
                        new Service().trackScreen(mFirebaseAnalytics, "Products Clothes Screen");

                        // ------------------------------------------------------
                        recyclerProductsAdapter = new RecyclerProductsAdapter(ProductsActivity.this, mFirebaseAnalytics, insertProductClothes());
                        recyclerView.setAdapter(recyclerProductsAdapter);

        } else if (getIntent().getStringExtra("productsElectronic") != null) {
                         //  ------------------Track Screen
                         new Service().trackScreen(mFirebaseAnalytics, "Products Electronic Screen");
                        //  --------------------------------------------
                        recyclerProductsAdapter = new RecyclerProductsAdapter(ProductsActivity.this, mFirebaseAnalytics, insertProductElectronic());
                        recyclerView.setAdapter(recyclerProductsAdapter);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // ---------------------------Calculate the time------------
        timeLeave = System.currentTimeMillis();
        timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
        Log.e("timeLeave",timeLeave+"-----"+timeEntry+"-----"+timeSpentPerSecond);
//        ----------------------------------------------------

//  ---------------Save to FireStore how long did he spend in the screen

//        get user_id
        mFirebaseAnalytics.getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    userId = task.getResult();

//                  ------------- Add To FireStore ---------------------------------
                    HashMap<String,Object> detailsSession=new HashMap<>();
                    detailsSession.put("timePerSecond",timeSpentPerSecond);
                    detailsSession.put("userId",userId);
                    detailsSession.put("pageName",namePage);
                    CollectionReference collectionReference=db.collection("detailsSessionInScreen");
                    Task<DocumentReference> documentReferenceTask= collectionReference.add(detailsSession);
                    documentReferenceTask.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("success","uploadSession");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("failure","failureUploadSession");
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });


                }else if(task.isCanceled()){
                    Log.d("failure","failureGetUserIdSoNoSaveToFireStore");
                }
            }
        });
    }

    //    ------------------------ anonymous data from object product --------------------------
    List<Product> insertProductFood() {
        List foodList = new ArrayList<Product>();
        foodList.add(new Product("foodX", "food_urlX", "food_descriptionX"));
        foodList.add(new Product("foodY", "food_urlY", "food_descriptionY"));
        foodList.add(new Product("foodZ", "food_urlZ", "food_descriptionZ"));
        foodList.add(new Product("foodA", "food_urlA", "food_descriptionA"));
        return foodList;
    }

    List<Product> insertProductClothes() {
        List clothesList = new ArrayList<Product>();
        clothesList.add(new Product("clothesX", "clothes_urlX", "clothes_descriptionX"));
        clothesList.add(new Product("clothesY", "clothes_urlY", "clothes_descriptionY"));
        clothesList.add(new Product("clothesZ", "clothes_urlZ", "clothes_descriptionZ"));
        clothesList.add(new Product("clothesA", "clothes_urlA", "clothes_descriptionA"));
        return clothesList;
    }

    List<Product> insertProductElectronic() {
        List electronicList = new ArrayList<Product>();
        electronicList.add(new Product("electronicX", "electronic_urlX", "electronic_descriptionX"));
        electronicList.add(new Product("electronicY", "electronic_urlY", "electronic_descriptionY"));
        electronicList.add(new Product("electronicZ", "electronic_urlZ", "electronic_descriptionZ"));
        electronicList.add(new Product("electronicA", "electronic_urlA", "electronic_descriptionA"));
        return electronicList;
    }
}
