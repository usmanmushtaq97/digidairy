package com.tss.digidairy.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.tss.digidairy.R;

public class GetLoginSignActivity extends AppCompatActivity {
    Button mLogin;
    Button mSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_login_sign);
        //methods
        init();
        mSignup.setOnClickListener(v -> {
            Intent intent = new Intent(GetLoginSignActivity.this, GetMobileNumber.class);
            startActivity(intent);
        });
        mLogin.setOnClickListener(v -> {
            Intent intent = new Intent(GetLoginSignActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    // layout bind with id
    private void init(){
        mLogin = findViewById(R.id.Loginget);
        mSignup = findViewById(R.id.signup_get);
    }
}