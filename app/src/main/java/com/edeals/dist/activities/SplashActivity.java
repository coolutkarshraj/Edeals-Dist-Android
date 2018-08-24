package com.edeals.dist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.models.DistributorProfile;
import com.edeals.dist.utils.LocalStorage;

public class SplashActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.context = this;
        GoToNextScreen();
    }

    private void GoToNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isUserLoggedIn()) {
                    intent = new Intent(context, HomeActivity.class);
                } else {
                    intent = new Intent(context, PreEnterActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, Config.splashTimeout);
    }

    private Boolean isUserLoggedIn() {
        LocalStorage localStorage = LocalStorage.getInstance(context);
        DistributorProfile profile = localStorage.getDistributorProfile();
        if (profile == null) {
            return false;
        }
        return true;
    }
}
