package com.tss.digidairy.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Adapter.SubCategoryAdapter;
import com.tss.digidairy.app.Model.SubcategoryModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static com.tss.digidairy.app.Constant.SubcategoryUrl;

public class SubCategories extends AppCompatActivity {
    int category_id;
    String categoryName;
    RecyclerView recyclerView_sub_category;
    Toolbar toolbar;
    List<SubcategoryModel> categoryModelList;
    SubCategoryAdapter mCategory_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        BindViewWithId();
        GetInt_Value();
        setupToolbar();
       Load_Sub_Catecory();
    }
    private void BindViewWithId() {
        recyclerView_sub_category = findViewById(R.id.recycler_sub_category);
        toolbar = findViewById(R.id.toolbar_subcategory);
    }
    private void GetInt_Value() {
        Intent intent = getIntent();
        category_id = intent.getIntExtra("id", 0);
        categoryName = intent.getStringExtra("name");

        Toast.makeText(this, ""+category_id, Toast.LENGTH_SHORT).show();
    }
    public void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            (getSupportActionBar()).setTitle(categoryName);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void Load_Sub_Catecory() {
        categoryModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(SubCategories.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_sub_category.setLayoutManager(layoutManager);
        StringRequest request = new StringRequest(Request.Method.GET, SubcategoryUrl + category_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(SubCategories.this, "not loading", Toast.LENGTH_SHORT).show();
                }
                Log.d("Sub_category", response);
                try {
                    JSONArray mMainCategory = new JSONArray(response);
                    for (int i = 0; i < mMainCategory.length(); i++) {
                        JSONObject postobj = mMainCategory.getJSONObject(i);
                        int mCategory_id = postobj.getInt("id");
                        String mCategory_name = postobj.getString("name");
                        categoryModelList.add(new SubcategoryModel(mCategory_id, mCategory_name));
                    }
                    mCategory_Adapter = new SubCategoryAdapter(SubCategories.this, categoryModelList);
                    recyclerView_sub_category.setAdapter(mCategory_Adapter);
                    mCategory_Adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(SubCategories.this, "Slow Internet", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}