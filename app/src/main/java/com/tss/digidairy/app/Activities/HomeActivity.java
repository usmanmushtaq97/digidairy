package com.tss.digidairy.app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tss.digidairy.R;
import com.tss.digidairy.app.Fragments.Carts;
import com.tss.digidairy.app.Fragments.Category;
import com.tss.digidairy.app.Fragments.Home;
import com.tss.digidairy.app.Fragments.Notification;
import com.tss.digidairy.app.Fragments.Profiles;
import static com.tss.digidairy.R.*;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Toolbar mToolbar;
    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        init();
        setUpToolbar();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
          Fragment  fragment = new Home();
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, fragment)
                    .commit();
        }
    }

    private void init() {
        mToolbar = findViewById(id.toolbar_Id);
        bottomNavigationView = findViewById(id.bottomNavigationView);
    }

    // set toolbar
    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    // set bar toggle button

    // bottom navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
        switch (id) {
            case R.id.home:
                fragment = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
                break;
            case R.id.category:
                fragment = new Category();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
                break;
            case R.id.carts:
                fragment = new Carts();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
                break;
            case R.id.notification:
                fragment = new Profiles();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .commit();
                break;
                    }
        return true;
    }
}