package com.tss.digidairy.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Activities.ProductDetails;
import com.tss.digidairy.app.Model.AllProductsModel;

import java.util.List;

public class AllproductAdapter extends RecyclerView.Adapter<AllproductAdapter.MyViewHolder> {
    Context mContex;
    List<AllProductsModel> mList;
    View view;

    public AllproductAdapter(Context mContext, List<AllProductsModel> mList) {
        this.mContex = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContex).inflate(R.layout.productitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AllProductsModel rowp = mList.get(position);
        holder.textViewTitle.setText(rowp.getmProduct_title());
        holder.textViewPrice.setText(rowp.getmPrice());
        Glide.with(mContex).load(rowp.getmImage_url()).into(holder.mImageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContex, ProductDetails.class);
            intent.putExtra("product_id", rowp.getmP_id());
            intent.putExtra("sub_category",rowp.getSubcategory_id());
            intent.putExtra("product_name", rowp.getmProduct_title());
            intent.putExtra("product_image_url", rowp.getmImage_url());
            intent.putExtra("price", rowp.getmPrice());
            intent.putExtra("size", rowp.getmP_Size());
            intent.putExtra("other_images", rowp.getOtherimage());
            intent.putExtra("description", rowp.getmP_Discreption());
            intent.putExtra("status", rowp.getStatusstock());
            intent.putExtra("product_category_id", rowp.getCategory_id());
            intent.putExtra("sub_category_id", rowp.getSubcategory_id());
            mContex.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewPrice;
        TextView textView_adress;
        ImageView mImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.product_Name_);
            mImageView = itemView.findViewById(R.id.product_images);
            textViewPrice = itemView.findViewById(R.id.price_id);
            textView_adress = itemView.findViewById(R.id.adress);
        }
    }
}
