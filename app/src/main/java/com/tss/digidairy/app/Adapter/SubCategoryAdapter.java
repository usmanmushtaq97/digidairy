package com.tss.digidairy.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tss.digidairy.R;
import com.tss.digidairy.app.Activities.ProductByCategoryIdActivity;
import com.tss.digidairy.app.Model.SubcategoryModel;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    private final Context mContex;
    private final List<SubcategoryModel> mList;
    public SubCategoryAdapter(Context mCintex, List<SubcategoryModel> mList) {
        this.mContex = mCintex;
        this.mList = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title_subCategory);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContex).inflate(R.layout.subcategoriesitem, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      SubcategoryModel rowp = mList.get(position);
        holder.textViewTitle.setText(rowp.getTitle());
        holder.itemView.setOnClickListener(v -> {
            Intent intent  = new Intent(mContex, ProductByCategoryIdActivity.class);
            intent.putExtra("id",rowp.getId());
            intent.putExtra("name", rowp.getTitle());
            mContex.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
