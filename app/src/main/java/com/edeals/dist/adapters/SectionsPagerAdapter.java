package com.edeals.dist.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.edeals.dist.fragments.InboxFragment;
import com.edeals.dist.fragments.OrdersFragment;
import com.edeals.dist.fragments.StockFragment;

/**
 * Created by team edeals on 18-02-2018.
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;
    public SectionsPagerAdapter(Activity activity, FragmentManager fm) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = StockFragment.newInstance(activity);
                break;
            case 1:
                fragment =  OrdersFragment.newInstance(activity);
                break;
            case 2:
                fragment = InboxFragment.newInstance(activity);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
