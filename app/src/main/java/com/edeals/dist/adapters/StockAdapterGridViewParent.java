package com.edeals.dist.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.models.BrandAndProducts;

import java.util.ArrayList;


/**
 * Created by team edeals on 3/2/2018.
 */

public class StockAdapterGridViewParent extends BaseAdapter {
    public static String[] gridColor;
    public int colorCount = 0;
    Activity activity;
    ArrayList<BrandAndProducts> brandAndProducts;
    LayoutInflater layoutInflater = null;

    public StockAdapterGridViewParent(String[] gridColor, Activity activity, ArrayList<BrandAndProducts> brandAndProducts) {
        this.gridColor = gridColor;
        this.activity = activity;
        this.brandAndProducts = brandAndProducts;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class CategoryHolder {
        TextView brandName;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        CategoryHolder holder = new CategoryHolder();
        View rowView = layoutInflater.inflate(R.layout.item_stock_grid_view_parent, null);
        LinearLayout lk = rowView.findViewById(R.id.content_view);
        if (colorCount >= 16) {
            colorCount = 0;
        }
        lk.setBackgroundColor(Color.parseColor(gridColor[colorCount++]));
        holder.brandName = (TextView) rowView.findViewById(R.id.category_name);
        holder.brandName.setText(brandAndProducts.get(i).brandName);
        holder.brandName.setTag(brandAndProducts.get(i).products);
        return rowView;
    }

    @Override
    public int getCount() {
        return brandAndProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
