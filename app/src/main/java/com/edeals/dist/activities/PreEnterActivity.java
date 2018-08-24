package com.edeals.dist.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edeals.dist.Config;
import com.edeals.dist.models.DistributorProfile;
import com.edeals.dist.utils.LocalStorage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.edeals.dist.R;
import com.edeals.dist.APICaller;
import com.edeals.dist.models.LoginModel;
import com.edeals.dist.models.RegistrationModel;
import com.edeals.dist.utils.Utils;
import com.edeals.dist.dialogs.IPDialog;
import com.koushikdutta.ion.Response;

public class PreEnterActivity extends AppCompatActivity {

    private Activity activity;
    LinearLayout layoutSignIn;
    ScrollView layoutSignUp;
    TextView tvSignInNav;
    Button btnSignUpNav;
    EditText etNameSignUp, etEmailSignUp, etPasswordSignUp, etPhoneSignUp, etEmailSignIn, etPasswordSignin;
    Button btnSignIn, btnSignUp, btnSignUpToRegister;
    Spinner spnShopType;
    ImageView ivOpenIpModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_enter);
        init();
        bind();
        start();
    }

    @SuppressLint("CutPasteId")
    private void init() {
        activity = this;
        layoutSignIn = findViewById(R.id.layoutSigIn);
        layoutSignUp = findViewById(R.id.layoutSigUp);
        tvSignInNav = findViewById(R.id.tvSignInNav);
        btnSignUpNav = findViewById(R.id.btnSignUpNav);
        btnSignIn = findViewById(R.id.btnSignIn);
        spnShopType = findViewById(R.id.shopType);
        etNameSignUp = findViewById(R.id.name);
        etEmailSignUp = findViewById(R.id.email);
        etPasswordSignUp = findViewById(R.id.password);
        etPhoneSignUp = findViewById(R.id.phone);
        btnSignUp = findViewById(R.id.btnSignUp);
        etEmailSignIn = findViewById(R.id.email_signin);
        etPasswordSignin = findViewById(R.id.password_signin);
        ivOpenIpModal = findViewById(R.id.ivOpenIpModal);
    }

    private void bind() {

        tvSignInNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInLayout();
            }
        });

        btnSignUpNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpLayout();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpLayout();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailSignIn.getText().toString();
                String password = etPasswordSignin.getText().toString();
                if (Utils.isEmailValid(email) && Utils.isPasswordValid(password)) {
                    login();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etNameSignUp.getText().toString();
                String userPass = etPasswordSignUp.getText().toString();
                String shopType = spnShopType.getSelectedItem().toString();
                if (Utils.isUserNameValid(userName) && Utils.isPasswordValid(userPass) && shopType != null) {
                    registerUser();
                }
            }
        });

        new IPDialog(ivOpenIpModal, activity);
    }

    private void start() {
        attachUserTypesWithSpinner();
    }

    private void attachUserTypesWithSpinner() {
        String[] userTypes = getResources().getStringArray(R.array.shopTypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spnShopType.setAdapter(adapter);
    }

    private void showSignInLayout() {
        layoutSignIn.setVisibility(View.VISIBLE);
        layoutSignUp.setVisibility(View.GONE);
    }

    private void showSignUpLayout() {
        layoutSignUp.setVisibility(View.VISIBLE);
        layoutSignIn.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (isSignUpLayoutVisible()) {
            showSignInLayout();
            return;
        }
        super.onBackPressed();
    }

    private boolean isSignUpLayoutVisible() {
        if (layoutSignUp.getVisibility() == View.VISIBLE)
            return true;
        return false;
    }

    public void registerUser() {
        RegistrationModel model = new RegistrationModel();
        model.name = etNameSignUp.getText().toString();
        model.email = etEmailSignUp.getText().toString();
        model.password = etPasswordSignUp.getText().toString();
        model.phone = etPhoneSignUp.getText().toString();
        model.shopType = spnShopType.getSelectedItem().toString();
        APICaller.registerUser(model, activity,
                new FutureCallback<RegistrationModel>() {
                    @Override
                    public void onCompleted(Exception e, RegistrationModel result) {
                        if (e != null) {
                            Utils.showAlertDialog(activity, Config.serverNotResponding);
                        } else {
                            layoutSignUp.setVisibility(View.GONE);
                            layoutSignIn.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void login() {
        LoginModel model = new LoginModel();
        model.email = etEmailSignIn.getText().toString();
        model.password = etPasswordSignin.getText().toString();
        APICaller.loginUser(model, activity,
                new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if (e != null) {
                            Utils.showAlertDialog(activity, Config.serverNotResponding);
                            return;
                        }
                        int code = result.getHeaders().code();

                        if (code == 401) {
                            Utils.showAlertDialog(activity, "Invalid credentials");
                            return;
                        }

                        if (code == 403) {
                            Utils.showAlertDialog(activity, "You are already logged in on another device.");
                            return;
                        }

                        saveDistributor(result.getResult());
                        updateDeviceToken();

                        Intent i = new Intent(activity, HomeActivity.class);
                        startActivity(i);
                    }
                });
    }

    private void updateDeviceToken() {
        APICaller.updateDeviceToken(activity, new FutureCallback<Response<String>>() {

            @Override
            public void onCompleted(Exception e, Response<String> status) {
                if (e != null) {
                    return;
                }
                if (status.getHeaders().code() == 200) {
                }
            }
        });
    }

    private void saveDistributor(JsonObject distributor) {
        final Gson gson = new Gson();
        DistributorProfile distributorProfile = gson.fromJson(distributor, DistributorProfile.class);
        LocalStorage localStorage = new LocalStorage(activity);
        localStorage.putDistributorProfile(distributorProfile);
    }
}

