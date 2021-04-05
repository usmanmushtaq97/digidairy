package com.tss.digidairy.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.tss.digidairy.R;

import static com.tss.digidairy.app.Constant.SPLASH_TIME;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//
//        }
//        else {
//            Intent intent = new Intent(SplashActivity.this, GetMobileNumber.class);
//            startActivity(intent);
//            finish();
//        }
        handler = new Handler();
        handler.postDelayed(() -> {
          intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME);

    }

}