package com.edeals.dist.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edeals.dist.APICaller;
import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.utils.LocalStorage;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

public class AddProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity;
    LinearLayout llAddProductMain;
    LinearLayout llOptions;
    TextView tvTest;
    ImageView ivOpenDrawer;
    DrawerLayout drawerLayout;
    TextView tvToolbarTitle;
    LocalStorage localStorage;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        init();
        bind();
        start();
    }

    private void init() {
        activity = this;
        localStorage = LocalStorage.getInstance(activity);
        llAddProductMain = findViewById(R.id.llAddProductMain);
        llOptions = findViewById(R.id.llOptions);
        ivOpenDrawer = findViewById(R.id.ivOpenDrawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        tvTest = findViewById(R.id.tvTest);
        navigationView = findViewById(R.id.navView);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
    }

    private void bind() {
        navigationView.setNavigationItemSelectedListener(this);
        tvTest.setOnClickListener(new View.OnClickListener() {
            Boolean isVisible = false;
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(llAddProductMain);
                isVisible = !isVisible;
                llOptions.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            }
        });
        ivOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void start() {
        tvToolbarTitle.setText("Add New Product");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSignOut) {
            APICaller.logoutUser(activity, new FutureCallback<Response<String>>() {

                @Override
                public void onCompleted(Exception e, Response<String> status) {
                    if (e != null) {
                        Utils.showAlertDialog(activity, Config.serverNotResponding);
                        return;
                    }
                    if (status.getHeaders().code() == 200) {
                        Intent preEnter = new Intent(activity, PreEnterActivity.class);
                        localStorage.clearAll();
                        startActivity(preEnter);
                        finish();
                    }
                }

            });
            return true;
        }
        if (id == R.id.itemHome) {
            Intent home = new Intent(activity, HomeActivity.class);
            startActivity(home);
            finish();
            return true;
        }
        return true;
    }
}
