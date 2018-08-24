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
import com.edeals.dist.models.ProductModel;

import java.util.ArrayList;


/**
 * Created by team edeals on 3/2/2018.
 */

public class StockAdapterGridViewChild extends BaseAdapter {
    public static String[] gridColor;
    public int colorCount = 0;
    Activity activity;
    ArrayList<ProductModel> products;
    LayoutInflater layoutInflater = null;

    public StockAdapterGridViewChild(String[] gridColor, Activity activity, ArrayList<ProductModel> products) {
        this.gridColor = gridColor;
        this.activity = activity;
        this.products = products;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class CategoryHolder {
        TextView brandName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryHolder holder = new CategoryHolder();
        View rowView = layoutInflater.inflate(R.layout.item_stock_grid_view_child, null);
        LinearLayout lk = rowView.findViewById(R.id.content_view);
        if (colorCount >= 16) {
            colorCount = 0;
        }
        lk.setBackgroundColor(Color.parseColor(gridColor[colorCount++]));
        holder.brandName = rowView.findViewById(R.id.category_name);
        holder.brandName.setText(products.get(i).productName);
//        holder.brandName.setTag(products.get(i).productName);
        return rowView;
    }

    @Override
    public int getCount() {
        return products.size();
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
