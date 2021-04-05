package com.tss.digidairy.app.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Adapter.CategoryRecAdapter;
import com.tss.digidairy.app.Constant;
import com.tss.digidairy.app.Model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tss.digidairy.app.Constant.CategoryUrl;

public class Category extends Fragment {
    View view;
    List<CategoryModel> categoryModelList;
    Context mContex;
    RecyclerView recyclerView_category;
  //mCategory_Adapter;

    public Category() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContex = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        intit();
             LoadCatecory();
        return view;
    }

    private void intit() {
        recyclerView_category = view.findViewById(R.id.category_id);
    }

    private void LoadCatecory() {
        categoryModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, CategoryUrl, response -> {
            Log.d("testtcode", response);
            try {
                JSONArray mMainCategory = new JSONArray(response);
                for (int i = 0; i < mMainCategory.length(); i++) {
                    JSONObject postobj = mMainCategory.getJSONObject(i);
                    int mCategory_id = postobj.getInt("id");
                    String mCategory_name = postobj.getString("name");
                    String mImageUrl = Constant.MAINBASEUrl + postobj.getString("image");
                    categoryModelList.add(new CategoryModel(mCategory_id, mCategory_name, mImageUrl));
                }
                GridLayoutManager layoutManager = new GridLayoutManager(mContex, 3);
                recyclerView_category.setLayoutManager(layoutManager);
                CategoryRecAdapter  mCategory_Adapter = new CategoryRecAdapter(mContex, categoryModelList);
                recyclerView_category.setAdapter(mCategory_Adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->
                Toast.makeText(mContex, "Category" + error.getMessage(), Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContex);
        requestQueue.add(request);
    }
}