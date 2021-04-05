package com.example.googleanalyticsassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleanalyticsassignment.ProductDetailsActivity;
import com.example.googleanalyticsassignment.R;
import com.example.googleanalyticsassignment.model.Product;
import com.example.googleanalyticsassignment.utility.Service;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerProductsAdapter extends RecyclerView.Adapter<RecyclerProductsAdapter.VHolderProduct> {
    List<Product> products;
    Context context;
    FirebaseAnalytics mFirebaseAnalytics;

    public RecyclerProductsAdapter(Context context,FirebaseAnalytics mFirebaseAnalytics,List<Product> products) {
        this.products = products;
        this.context=context;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }

    @NonNull
    @Override
    public VHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_item_list_of_products,parent,false);
        return new VHolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolderProduct holder, int position) {
        Product product= products.get(position);
        holder.textViewName.setText(product.getNameProduct());
        String imageUrl=product.getUrlImage();
        Picasso.get().load(R.drawable.woman).into(holder.imageView);
        Log.e("imageUrlLLLLLLLLL",imageUrl);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class VHolderProduct extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        public VHolderProduct(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_product);
            textViewName=itemView.findViewById(R.id.nameText_product);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ProductDetailsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("DATA_PRODUCT",products.get(getAdapterPosition()));
                    intent.putExtras(bundle);

 //               firebase analytics select content
                    new Service().selectContent(
                            mFirebaseAnalytics,
                            String.valueOf(new Random().nextInt(10000)),
                            products.get(getAdapterPosition()).getNameProduct(),"clickButton");
                    context.startActivity(intent);
                }
            });
        }
    }
}
