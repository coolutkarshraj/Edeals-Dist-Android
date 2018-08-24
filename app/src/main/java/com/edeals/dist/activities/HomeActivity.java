package com.edeals.dist.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edeals.dist.Config;
import com.edeals.dist.fragments.InboxFragment;
import com.edeals.dist.fragments.StockFragment;
import com.edeals.dist.models.DistributorProfile;
import com.edeals.dist.utils.SearchBoxAnimator;
import com.edeals.dist.utils.SessionStorage;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.edeals.dist.R;
import com.edeals.dist.adapters.SectionsPagerAdapter;
import com.edeals.dist.APICaller;
import com.edeals.dist.utils.LocalStorage;
import com.edeals.dist.dialogs.IPDialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    DrawerLayout drawerLayout;
    TextView tvToolbarTitle;
    ImageView ivOpenDrawer;
    ImageView ivBack;
    EditText etSearch;
    ImageView ivToggleOnlineStatus;
    NavigationView navigationView;
    LocalStorage localStorage;
    TextView tvUserNameOnSideNav;
    TextView tvUserEmailOnSideNav;
    ImageView ivProfilePic;
    View headerView;
    ImageView ivSearchIcon;
    SearchBoxAnimator animator;
    int selectedTabPosition = 0;
    TextView tvOnlineStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        bind();
        start();
    }

    private void init() {
        activity = this;
        localStorage = LocalStorage.getInstance(activity);
        mSectionsPagerAdapter = new SectionsPagerAdapter(activity, getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.distributorTabs);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        ivOpenDrawer = findViewById(R.id.ivOpenDrawer);
        ivBack = findViewById(R.id.ivBack);
        etSearch = findViewById(R.id.etSearch);
        ivSearchIcon = findViewById(R.id.ivSearchIcon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        headerView = navigationView.getHeaderView(0);
        ivToggleOnlineStatus = headerView.findViewById(R.id.ivToggleOnlineStatus);
        tvUserNameOnSideNav = headerView.findViewById(R.id.tvUserNameOnSideNav);
        tvUserEmailOnSideNav = headerView.findViewById(R.id.tvUserEmailOnSideNav);
        animator = new SearchBoxAnimator(activity, tabLayout, ivOpenDrawer, ivBack, ivSearchIcon, tvToolbarTitle, etSearch);
        ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
        tvOnlineStatus = headerView.findViewById(R.id.tvOnlineStatus);
        if (localStorage.getInt(LocalStorage.ONLINE_STATUS) == 1) {
            ivToggleOnlineStatus.setImageResource(R.drawable.toggle_on);
            tvOnlineStatus.setText("ONLINE");
        }
    }

    private void bind() {
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        ivOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        new IPDialog(tvToolbarTitle, activity);

        ivToggleOnlineStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleOnlineStatusImage();
            }
        });

        ivSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionStorage.searchMode) {
                    filter();
                } else {
                    animator.animate();
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSearchState();
                animator.animateReverse();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabPosition = tab.getPosition();
                updateHint();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void updateHint() {
        etSearch.setText("");
        if (selectedTabPosition == 0) {
            etSearch.setHint("Search products");
            return;
        }
        if (selectedTabPosition == 1) {
            etSearch.setHint("Search orders");
            return;
        }
        if (selectedTabPosition == 2) {
            etSearch.setHint("Search inbox");
            return;
        }
    }

    private void resetSearchState() {
        etSearch.setText("");
        filter();
    }

    private void filter() {
        String searchText = etSearch.getText().toString();
        selectedTabPosition = tabLayout.getSelectedTabPosition();
        if (selectedTabPosition == 0) {
            return;
        }
        if (selectedTabPosition == 1) {
            return;
        }
        if (selectedTabPosition == 2) {
            Intent intent = new Intent(InboxFragment.Filter_Inbox);
            intent.putExtra("searchText", searchText);
            activity.sendBroadcast(intent);
            return;
        }
    }

    private void start() {
        updateSideNav();
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @SuppressLint("NewApi")
    private void toggleOnlineStatusImage() {
        Bitmap b1 = ((BitmapDrawable) getDrawable(R.drawable.toggle_on)).getBitmap();
        Bitmap b2 = ((BitmapDrawable) ivToggleOnlineStatus.getDrawable()).getBitmap();
        if (b1 == b2) {
            updateOnlineStatus(0);
        } else {
            updateOnlineStatus(1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
        if (id == R.id.itemAddProduct) {
            Intent addProduct = new Intent(activity, AddProductActivity.class);
            startActivity(addProduct);
            finish();
            return true;
        }
        return true;
    }

    private void updateOnlineStatus(final int onlineStatus) {
        APICaller.updateOnlineStatus(activity, onlineStatus, new FutureCallback<Response<String>>() {

            @Override
            public void onCompleted(Exception e, Response<String> status) {
                if (e != null) {
                    Utils.showAlertDialog(activity, Config.serverNotResponding);
                    return;
                }
                if (status.getHeaders().code() == 200) {
                    ivToggleOnlineStatus.setImageResource(R.drawable.toggle_off);
                    tvOnlineStatus.setText("OFFLINE");
                    if (onlineStatus == 1) {
                        ivToggleOnlineStatus.setImageResource(R.drawable.toggle_on);
                        tvOnlineStatus.setText("ONLINE");
                    }
                    localStorage.putInt(LocalStorage.ONLINE_STATUS, onlineStatus);
                }
            }

        });
    }

    private void updateSideNav() {
        DistributorProfile profile = localStorage.getDistributorProfile();
        String userName = Utils.toTitleCase(profile.name);
        String userEmail = profile.email;
        try {
            URL url = new URL(profile.imageUrl.replace("localhost", Config.IP));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ivProfilePic.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvUserNameOnSideNav.setText(userName);
        tvUserEmailOnSideNav.setText(userEmail);
    }

    @Override
    public void onBackPressed() {
        SessionStorage.backPressCounter++;
        if (SessionStorage.searchMode) {
            SessionStorage.backPressCounter = 0;
            resetSearchState();
            animator.animateReverse();
            return;
        }
        if (SessionStorage.childGridActive) {
            SessionStorage.backPressCounter = 0;
            SessionStorage.childGridActive = false;
            activity.sendBroadcast(new Intent(StockFragment.HIDE_GRID_VIEW_CHILD));
            return;
        }
        if (SessionStorage.backPressCounter < 2) {
            Toast.makeText(activity, Config.backPressMessage, Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SessionStorage.backPressCounter = 0;
                }
            }, 2000);
            return;
        }
        super.onBackPressed();
    }
}
