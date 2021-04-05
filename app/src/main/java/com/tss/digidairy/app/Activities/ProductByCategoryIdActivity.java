package com.tss.digidairy.app.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Adapter.AllproductAdapter;
import com.tss.digidairy.app.Constant;
import com.tss.digidairy.app.Model.AllProductsModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProductByCategoryIdActivity extends AppCompatActivity {
    int category_id;
    String categoryName;
    RecyclerView recyclerViewAllproduct;
    Toolbar toolbar;
    AllproductAdapter allproductAdapter;
    List<AllProductsModel> allProductsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category_id);
        GetInt_Value();
        BindViewWithId();
        setupToolbar();
        Load_Product();
    }

    private void BindViewWithId() {
        recyclerViewAllproduct = findViewById(R.id.recycler_product);
        toolbar = findViewById(R.id.toolbar_all_product);
    }

    private void GetInt_Value() {
        Intent intent = getIntent();
        category_id = intent.getIntExtra("id", 0);
        categoryName = intent.getStringExtra("name");

    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
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
    private void Load_Product() {
        allProductsModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, "https://usmanmushtaq.e-sialkot.com/api-reset-php/get-products-by-subcategory-id.php?id=" + category_id, response -> {
               Log.d("response product", response);
            try {
                JSONArray productobj = new JSONArray(response);
                for (int i = 0; i < productobj.length(); i++) {
                    JSONObject postobj = productobj.getJSONObject(i);
                    int product_id = postobj.getInt("id");
                    String product_name = postobj.getString("name");
                    int cat_id = postobj.getInt("category_id");
                    int subcategoryid = postobj.getInt("subcategory_id");
                    String product_price = postobj.getString("price");
                    String mImageUrl = Constant.MAINBASEUrl+postobj.getString("image");
                    String size   =  postobj.getString("size");
                    String otherImageurl = postobj.getString("other_images");
                    String Product_discription = postobj.getString("description");
                    int status  = postobj.getInt("status");
                    String shopadress = postobj.getString("address");
                    int addedby = postobj.getInt("added_by");
                    allProductsModelList.add(new AllProductsModel(product_id, product_name, Product_discription, status,shopadress,product_price, mImageUrl, size, otherImageurl, cat_id, subcategoryid, addedby));
                }
                GridLayoutManager layoutManagerRecent_preoduct = new GridLayoutManager(ProductByCategoryIdActivity.this, 2);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.VERTICAL);
                recyclerViewAllproduct.setLayoutManager(layoutManagerRecent_preoduct);
                allproductAdapter = new AllproductAdapter(ProductByCategoryIdActivity.this, allProductsModelList);
                recyclerViewAllproduct.setAdapter(allproductAdapter);
                allproductAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ProductByCategoryIdActivity.this, "internet slow down ", Toast.LENGTH_SHORT).show());
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