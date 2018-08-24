package com.edeals.dist.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.edeals.dist.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

/**
 * Created by team edeals on 27-03-2018.
 */
//  TODO: Refactoring pending
public class AddProductAnimator {
    Activity activity;
    TabLayout distributorTabs;
    ImageView ivOpenDrawer;
    private ImageView ivBack;
    private ImageView ivSearchIcon;
    private TextView tvToolbarTitle;
    private EditText etSearch;
    int centerX;
    int centerY;
    int hypotenuse;
    float pixelDensity;
    Boolean flag = false;
    int tabLayoutHeight = 0;

    public AddProductAnimator(Activity activity,
                              TabLayout distributorTabs,
                              ImageView ivOpenDrawer,
                              ImageView ivBack,
                              ImageView ivSearchIcon,
                              TextView tvToolbarTitle,
                              EditText etSearch) {
        this.activity = activity;
        this.distributorTabs = distributorTabs;
        this.ivOpenDrawer = ivOpenDrawer;
        this.ivBack = ivBack;
        this.ivSearchIcon = ivSearchIcon;
        this.tvToolbarTitle = tvToolbarTitle;
        this.etSearch = etSearch;
    }

    public Boolean animate() {
        if (flag)
            return !flag;
        flag = true;
        SessionStorage.searchMode = true;
        centerX = tvToolbarTitle.getRight();
        centerY = tvToolbarTitle.getBottom();
        centerX -= ((28 * pixelDensity) + (16 * pixelDensity));
        hypotenuse = (int) Math.hypot(tvToolbarTitle.getWidth(), tvToolbarTitle.getHeight());
        tabLayoutHeight = 0 - distributorTabs.getHeight();
        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams) etSearch.getLayoutParams();
        parameters.height = tvToolbarTitle.getHeight();
        etSearch.setLayoutParams(parameters);

        android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(etSearch, centerX, centerY, 0, hypotenuse);
        anim.setDuration(400);

        anim.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {
                updateSearchIcon(true);
                switchBetweenDrawerAndBackIcon(false, tabLayoutHeight);
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                etSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {}

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {}
        });

        etSearch.setVisibility(View.VISIBLE);
        anim.start();
        return flag;
    }

    public void animateReverse() {
        flag = false;
        SessionStorage.searchMode = false;
        android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(etSearch, centerX, centerY, hypotenuse, 0);
        anim.setDuration(300);

        anim.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {
                updateSearchIcon(false);
                switchBetweenDrawerAndBackIcon(true, 0);
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                etSearch.setVisibility(View.GONE);
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {}

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {}
        });

        anim.start();
    }

    private void toggleTabs(int i) {
        final ViewGroup transitionsContainer = activity.findViewById(R.id.drawer_layout);
        TransitionSet set = new TransitionSet()
                .addTransition(new ChangeBounds().setDuration(200));
        TransitionManager.beginDelayedTransition(transitionsContainer, set);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) distributorTabs.getLayoutParams();
        params.topMargin = i;
        distributorTabs.setLayoutParams(params);
    }

    private void switchBetweenDrawerAndBackIcon(Boolean visible, final int i) {
        final ViewGroup transitionsContainer = activity.findViewById(R.id.llToolbar);
        TransitionSet set = new TransitionSet()
                .addTransition(new ChangeBounds())
                .addTransition(new Fade())
                .setInterpolator(visible ? new FastOutLinearInInterpolator() :
                        new LinearOutSlowInInterpolator());
        TransitionManager.beginDelayedTransition(transitionsContainer, set);
        ivOpenDrawer.setVisibility(!visible ? View.GONE : View.VISIBLE);
        ivBack.setVisibility(visible ? View.GONE : View.VISIBLE);
        toggleTabs(i);
    }

    private void updateSearchIcon(Boolean flag) {
        final ViewGroup transitionsContainer = activity.findViewById(R.id.llToolbar);
        TransitionManager.beginDelayedTransition(transitionsContainer, new Recolor());
        ivSearchIcon.setBackgroundDrawable(
                new ColorDrawable(activity.getResources().getColor(flag ? R.color.colorWhiteX :
                        R.color.colorPrimary)));
        ivSearchIcon.setImageResource(flag ? R.drawable.search : R.drawable.search_white);
    }
}
