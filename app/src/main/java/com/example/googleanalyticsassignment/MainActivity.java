package com.example.googleanalyticsassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;


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

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button foodButton,clothesButton,electronicButton;
    Intent intent;
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseFirestore db;
    long timeEntry,timeLeave,timeSpentPerSecond;
    String userId;
    String namePage;
    @Override
    protected void onRestart() {
        super.onRestart();
        // init class Service in package utility
        Date serviceDate=new Service().calendarDate();
         timeEntry =serviceDate.getTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
//  ---------------Save to FireStore how long did he spend in the screen

//        get user_id
        mFirebaseAnalytics.getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()){
                             userId = task.getResult();
                            Log.e("tttttttttttttt",timeSpentPerSecond+"");
                            Log.e("uuuuuuuuuuuuuuuuu",userId);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ------------inflate
        setContentView(R.layout.activity_main);
        foodButton=findViewById(R.id.food_button);
        clothesButton=findViewById(R.id.clothes_button);
        electronicButton=findViewById(R.id.electronic_button);

//        get NameScreen
         namePage=this.getClass().getSimpleName();
//       ---------------------Instance-----------------------------------
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();

//      ---------- get current time
        Date serviceDate=new Service().calendarDate();
        timeEntry =serviceDate.getTime();
        Log.e("timeEntry",timeEntry+"");
//        ----------------------------------------------------------
//        ------------- Track Screen --------------------------------
        new Service().trackScreen(mFirebaseAnalytics,"Category Screen");
//        -----------------------------------------------------------

//        -------------------------OnClick--------------------
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsFood","food");
                // ---------------------------Calculate the time------------
                timeLeave = System.currentTimeMillis();
                timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");
//               firebase analytics select content
                new Service().selectContent(
                        mFirebaseAnalytics,
                        String.valueOf(new Random().nextInt(10000)),
                        "foodCategory","clickButton");
                startActivity(intent);
            }
        });
        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsClothes","clothes");
                // ---------------------------Calculate the time------------
                 timeLeave = System.currentTimeMillis();
                 timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");

//               firebase analytics select content
                new Service().selectContent(
                        mFirebaseAnalytics,
                        String.valueOf(new Random().nextInt(10000)),
                        "Clothes","clickButton");
                startActivity(intent);
            }
        });
        electronicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProductsActivity.class);
                intent.putExtra("productsElectronic","electronic");
                // ---------------------------Calculate the time------------
                timeLeave = System.currentTimeMillis();
                timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
                Log.e("timeTaken",timeSpentPerSecond+"");
                //   firebase analytics select content
                new Service().selectContent(
                        mFirebaseAnalytics,
                        String.valueOf(new Random().nextInt(10000)),
                        "electronic","clickButton");
                startActivity(intent);
            }
        });

    }


}