package com.tss.digidairy.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smarteist.autoimageslider.SliderView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Adapter.AllproductAdapter;
import com.tss.digidairy.app.Adapter.AutoSliderVIewAdapter;
import com.tss.digidairy.app.Adapter.ProductImageSliderAdapter;
import com.tss.digidairy.app.Constant;
import com.tss.digidairy.app.Model.AllProductsModel;
import com.tss.digidairy.app.Model.SliderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SliderView mSliderView;
    List<SliderItem> sliderItemList;
    AutoSliderVIewAdapter sliderViewAdapter;
    ToggleButton buttonViewDiscription, salerdailsbtn;
    ExpandableRelativeLayout descriptionDetailLayout, salerdailslayout;
    Toolbar toolbar;
    RecyclerView recyclerViewsimilar;
    TextView productpriceView, productnameview, productDiscriptionView;
    TextView product_quantity_tv, tv_stock_status;
    TextView product_Details_price_view;
    TextView tv_shop_title;
    TextView tv_adress_shop;
    Spanned spanneddisc;
    String productfirstimage;
    String price;
    String productname;
    String description;
    String other_images;
    String shop_mobile;
    String size;
    Button addtoCartbtn;
    Button increament_Quantity_bt, decreament_Quantity_bt;
    Button bt_status_color;
    CircleImageView shopimageview;
    ImageView whatsappimage;
    int category_id;
    int subcategory_id;
    String shop_name;
    private double total_price_single_product = 0;
    private int product_qunatity = 1;
    int stock_status;
    private List< String > otherImagesList;
    private ArrayList< String > size_list;
    AllproductAdapter allproductAdapter;
    ArrayAdapter< String > size_adapter;
    List< AllProductsModel > allProductsModelList;
    Spinner spinsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
        Textbtnoclear();
        BanExpandCollapse();
        ValueGetIntend();
        SetDataOnproduct();
        AboutStock();
        GetSimilarProduct();
        SellerDetail();
        SetOtherImages();
        spinsize.setOnItemSelectedListener(this);
        whatsappimage.setOnClickListener(v -> WhatsAppUS());
        size_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, size_list);
        size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void init() {
        descriptionDetailLayout = findViewById(R.id.descreptiondetail);
        salerdailslayout = findViewById(R.id.sallerdetailslayout);
        salerdailsbtn = findViewById(R.id.Salerdetailsbtn);
        buttonViewDiscription = findViewById(R.id.fulldetail);
        tv_stock_status = findViewById(R.id.tv_status_stock_id);
        bt_status_color = findViewById(R.id.stockstatus);
        recyclerViewsimilar = findViewById(R.id.recyclerViewsimilar);
        productDiscriptionView = findViewById(R.id.discription_product_details);
        productnameview = findViewById(R.id.productname_detailsActivity);
        productpriceView = findViewById(R.id.product_price_detailActivity);
        addtoCartbtn = findViewById(R.id.addtocartbtn);
        tv_shop_title = findViewById(R.id.title_shop);
        product_quantity_tv = findViewById(R.id.tV_Quntity_product);
        increament_Quantity_bt = findViewById(R.id.bt_increament_product);
        decreament_Quantity_bt = findViewById(R.id.bt_deceremnt_product_quantity);
        product_Details_price_view = findViewById(R.id.product_price_detail_single_price);
        shopimageview = findViewById(R.id.shop_profile_image);
        whatsappimage = findViewById(R.id.chat_us_whatsapp);
        tv_adress_shop = findViewById(R.id.adress_location);
        mSliderView = findViewById(R.id.imageSlider_product);
        spinsize = findViewById(R.id.sizespinner);
    }
    private void ValueGetIntend() {
        Intent intent = getIntent();
        category_id = intent.getIntExtra("product_id", 0);
        subcategory_id = intent.getIntExtra("sub_category", 0);
        productname = intent.getStringExtra("product_name");
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");
        productfirstimage = intent.getStringExtra("product_image_url");
        other_images = intent.getStringExtra("other_images");
        size = intent.getStringExtra("size");
        stock_status = intent.getIntExtra("status", 0);
        Toast.makeText(this, "" + stock_status, Toast.LENGTH_SHORT).show();
        otherImagesList = new ArrayList<>();
        size_list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(other_images);
            for (int i = 0; i < jsonArray.length(); i++) {
                otherImagesList.add(jsonArray.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray json = new JSONArray(size);
            for (int i = 0; i < json.length(); i++) {
                size_list.add(json.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void BanExpandCollapse() {
        buttonViewDiscription.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                descriptionDetailLayout.expand();
                buttonView.setBackgroundResource(R.drawable.ic_btncollaps);
            } else {
                descriptionDetailLayout.collapse();
                buttonView.setBackgroundResource(R.drawable.ic_expandbtn_b);
            }
        });
        salerdailsbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                salerdailslayout.expand();
                buttonView.setBackgroundResource(R.drawable.ic_btncollaps);
            } else {
                salerdailslayout.collapse();
                buttonView.setBackgroundResource(R.drawable.ic_expandbtn_b);
            }
        });
    }

    private void SetDataOnproduct() {
        // Calculate_product_totlal();
        spanneddisc = Html.fromHtml(description);
        productnameview.setText(productname);
        productDiscriptionView.setText(spanneddisc);
        productpriceView.setText(String.valueOf("KSh." + total_price_single_product));
        product_quantity_tv.setText(String.valueOf(product_qunatity));
        product_Details_price_view.setText("Total Price:  " + total_price_single_product);
    }

    // btn text on of clear
    private void Textbtnoclear() {
        buttonViewDiscription.setText(null);
        buttonViewDiscription.setTextOn(null);
        buttonViewDiscription.setTextOff(null);
        salerdailsbtn.setText(null);
        salerdailsbtn.setTextOn(null);
        salerdailsbtn.setTextOff(null);
    }

    private void AboutStock() {
        if (stock_status == 1) {
            tv_stock_status.setText("Available");
            bt_status_color.setBackgroundColor(Color.YELLOW);
        } else {
            tv_stock_status.setText("Short Stock");
            bt_status_color.setBackgroundColor(Color.YELLOW);
        }
    }
    private void GetSimilarProduct() {
        allProductsModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, "https://usmanmushtaq.e-sialkot.com/api-reset-php/get-products-by-subcategory-id.php?id=" + subcategory_id, response -> {
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
                    String mImageUrl = Constant.MAINBASEUrl + postobj.getString("image");
                    String otherImageurl = postobj.getString("other_images");
                    String Product_discription = postobj.getString("description");
                    String size = postobj.getString("size");
                    int status = postobj.getInt("status");
                    String address_shop = postobj.getString("address");
                    int addedby = postobj.getInt("added_by");
                    allProductsModelList.add(new AllProductsModel(product_id, product_name, Product_discription, status, address_shop, product_price, mImageUrl, size, otherImageurl, cat_id, subcategoryid, addedby));
                }
                LinearLayoutManager layoutManagerRecent_preoduct = new LinearLayoutManager(this);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.HORIZONTAL);
                recyclerViewsimilar.setLayoutManager(layoutManagerRecent_preoduct);
                allproductAdapter = new AllproductAdapter(ProductDetails.this, allProductsModelList);
                recyclerViewsimilar.setAdapter(allproductAdapter);
                allproductAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ProductDetails.this, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        Volley.newRequestQueue(this).add(request);
    }


    private void SellerDetail() {
        StringRequest mystringrequest = new StringRequest(Request.Method.GET, "http://usmanmushtaq.e-sialkot.com/api-reset-php/getshopbyid.php?id=3", new Response.Listener< String >() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject postobj = jsonArray.getJSONObject(i);
                        int shop_id = postobj.getInt("id");
                        shop_name = postobj.getString("name");
                        String image = postobj.getString("image");
                        String description_shop = postobj.getString("description");
                        String address_shop = postobj.getString("address");
                        shop_mobile = postobj.getString("shop_mob");
                        String latitude = postobj.getString("latitude");
                        String longitude = postobj.getString("longitude");
                        int added_by = postobj.getInt("added_by");
                        String shopimageurl = Constant.MAINBASEUrl + image;
                        Toast.makeText(ProductDetails.this, "" + shopimageurl, Toast.LENGTH_SHORT).show();
                        tv_adress_shop.setText(address_shop);
                        tv_shop_title.setText(shop_name);
                        Glide.with(ProductDetails.this).load(shopimageurl).into(shopimageview);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, error -> {
            Toast.makeText(this, "error to get in response", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(this).add(mystringrequest);
    }

    //whats app us
    private void WhatsAppUS() {
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {
            Uri uri = Uri.parse("smsto:" + shop_mobile);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Product" + productname);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } else {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        }
    }

    //check if available whats app in user mobile then send the sms
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = this.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    // slider images
    private void SetOtherImages() {
        mSliderView.startAutoCycle();
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mSliderView.setIndicatorSelectedColor(R.color.sliderfilcolor);
        mSliderView.setIndicatorUnselectedColor(Color.GRAY);
        mSliderView.setScrollTimeInSec(4);
        ProductImageSliderAdapter productImageSliderAdapter = new ProductImageSliderAdapter(ProductDetails.this, otherImagesList);
        mSliderView.setSliderAdapter(productImageSliderAdapter);
    }
    @Override
    public void onItemSelected(AdapterView< ? > parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView< ? > parent) {

    }
}