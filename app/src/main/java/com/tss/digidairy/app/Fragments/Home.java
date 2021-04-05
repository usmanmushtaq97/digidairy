package com.tss.digidairy.app.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Adapter.AllproductAdapter;
import com.tss.digidairy.app.Adapter.AutoSliderVIewAdapter;
import com.tss.digidairy.app.Adapter.CategoryRecAdapter;
import com.tss.digidairy.app.Constant;
import com.tss.digidairy.app.Model.AllProductsModel;
import com.tss.digidairy.app.Model.CategoryModel;
import com.tss.digidairy.app.Model.SliderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tss.digidairy.app.Constant.BaseUrl;
import static com.tss.digidairy.app.Constant.CategoryUrl;
import static com.tss.digidairy.app.Constant.MAINBASEUrl;
import static com.tss.digidairy.app.Constant.SliderUrl;

public class Home extends Fragment {
    View view;
    List<SliderItem> sliderItemList;
    SliderView mSliderView;
    List<CategoryModel> categoryModelList;
    Context mContex;
    RecyclerView recyclerView_category;
    RecyclerView recyclerView_product;
    AutoSliderVIewAdapter sliderViewAdapter;
    CategoryRecAdapter mCategory_Adapter;
    AllproductAdapter allproductAdapter;
    List<AllProductsModel> allProductsModelList;
    FusedLocationProviderClient client;
    boolean permessionchecked = false;
    Double latitude;
    Double longitude;

    public Home() {
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        BIndViewByID();
        if (ActivityCompat.checkSelfPermission(mContex, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContex, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContex, " location permestion granted", Toast.LENGTH_SHORT).show();
            // get location current
           // getCurrentLocation();
        } else {
            CheckPersmission();
            Toast.makeText(mContex, "not grant", Toast.LENGTH_SHORT).show();
        }

        // initialize the location clients
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        Toast.makeText(mContex, "location"+latitude+"long"+longitude, Toast.LENGTH_SHORT).show();
        getCurrentLocation();
        LoadSlider();
        LoadCatecory();
        Load_Product();
        return view;
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager mgr =
                (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (mgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            ///when location enable
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    } else {
                        Toast.makeText(mContex, "location is not available yet", Toast.LENGTH_SHORT).show();
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                longitude = location1.getLongitude();
                                latitude = location1.getLatitude();
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });

        }

    }
    /// this method connect layout item ///
    private void BIndViewByID() {
        recyclerView_category = view.findViewById(R.id.category_recycler_id);
        recyclerView_product = view.findViewById(R.id.product_recyclerview);
        mSliderView = view.findViewById(R.id.imageSlider);
    }
    private void LoadSlider() {
        mSliderView.startAutoCycle();
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mSliderView.setIndicatorSelectedColor(R.color.sliderfilcolor);
        mSliderView.setIndicatorUnselectedColor(Color.GRAY);
        mSliderView.setScrollTimeInSec(4);
        sliderItemList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, SliderUrl, response -> {
            Log.d("testtcode", response);
            try {
                JSONArray mMainCategory = new JSONArray(response);
                for (int i = 0; i < mMainCategory.length(); i++) {
                    JSONObject postobj = mMainCategory.getJSONObject(i);
                    int sliderid = postobj.getInt("id");
                    String mImageUrl = Constant.MAINBASEUrl + postobj.getString("image");

                    sliderItemList.add(new SliderItem(sliderid, mImageUrl));
                }
                sliderViewAdapter = new AutoSliderVIewAdapter(mContex, sliderItemList);
                mSliderView.setSliderAdapter(sliderViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(mContex, "errorrr" + error.getMessage(), Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        Volley.newRequestQueue(mContex).add(request);
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
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContex);
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView_category.setLayoutManager(layoutManager);
                mCategory_Adapter = new CategoryRecAdapter(mContex, categoryModelList);
                recyclerView_category.setAdapter(mCategory_Adapter);
                mCategory_Adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->
                Toast.makeText(mContex, "Category" + error.getMessage(), Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContex);
        requestQueue.add(request);
        //Volley.newRequestQueue(mContex).add(request);
    }

    private void Load_Product() {
        allProductsModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, MAINBASEUrl + BaseUrl + "get_nearbyproducts.php?lat=" + latitude + "&long=" + longitude, response -> {
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
                    String mImageUrl = postobj.getString("image");
                    String otherImageurl = postobj.getString("other_images");
                    String size = postobj.getString("size");
                    String Product_discription = postobj.getString("description");
                    String address_shop = postobj.getString("address");
                    int status = postobj.getInt("status");
                    int addedby = postobj.getInt("added_by");
                    allProductsModelList.add(new AllProductsModel(product_id, product_name, Product_discription, status, address_shop, product_price, mImageUrl, size, otherImageurl, cat_id, subcategoryid, addedby));
                }
                LinearLayoutManager layoutManagerRecent_preoduct = new LinearLayoutManager(mContex);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.VERTICAL);
                recyclerView_product.setLayoutManager(layoutManagerRecent_preoduct);
                allproductAdapter = new AllproductAdapter(mContex, allProductsModelList);
                recyclerView_product.setAdapter(allproductAdapter);
                allproductAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(mContex, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContex);
        requestQueue.add(request);
    }

    private void CheckPersmission() {
        Dexter.withContext(mContex)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                permessionchecked = true;
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();
    }


}