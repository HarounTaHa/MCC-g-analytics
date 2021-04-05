package com.example.googleanalyticsassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.Date;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
  TextView nameProduct ;
  TextView specificationProduct ;
  ImageView imageView;
  private FirebaseAnalytics mFirebaseAnalytics;
    String namePage;
    long timeEntry, timeLeave, timeSpentPerSecond;
    String userId;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        nameProduct=findViewById(R.id.textView_name);
        specificationProduct=findViewById(R.id.textView_specification);
        imageView=findViewById(R.id.imageView);
  //        get NameScreen
        namePage=this.getClass().getSimpleName();
//       ---------------------Instance-----------------------------------
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();

//      ---------- get current time
        Date serviceDate=new Service().calendarDate();
        timeEntry =serviceDate.getTime();
 //-----------------------------------------------------------
        Product product=(Product) getIntent().getExtras().getSerializable("DATA_PRODUCT");
         nameProduct.setText(product.getNameProduct());
         specificationProduct.setText(product.getSpecificationProduct());
         imageView.setImageResource(R.drawable.woman);
        //  -------------------------Track Screen -------------
                new Service().trackScreen(mFirebaseAnalytics,"Product "+product.getNameProduct()+"Screen");
        //        ----------------------------------------------------
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        // init class Service in package utility
        Date serviceDate = new Service().calendarDate();
        timeEntry = serviceDate.getTime();
    }
    @Override
    protected void onStop() {

        super.onStop();

// ---------------------------Calculate the time------------
        timeLeave = System.currentTimeMillis();
        timeSpentPerSecond = ((timeLeave - timeEntry)/1000);
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
}