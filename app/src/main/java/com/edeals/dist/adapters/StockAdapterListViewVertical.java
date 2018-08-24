package com.edeals.dist.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edeals.dist.R;
import com.edeals.dist.holders.StockHolderVertical;
import com.edeals.dist.models.BrandAndProducts;

import java.util.ArrayList;

/**
 * Created by team edeals on 19-02-2018.
 */

public class StockAdapterListViewVertical extends RecyclerView.Adapter<StockHolderVertical> {
    private Activity activity;
    private ArrayList<BrandAndProducts> products;

    public StockAdapterListViewVertical(Activity activity, ArrayList<BrandAndProducts> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public StockHolderVertical onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_stock_list_vertical, parent, false);
        return new StockHolderVertical(view, products);
    }

    @Override
    public void onBindViewHolder(StockHolderVertical holder, int position) {
        BrandAndProducts p = products.get(position);
        holder.tvBrandName.setText(p.brandName);
        StockAdapterListViewHorizontal adapter = new StockAdapterListViewHorizontal(activity, p.products);
        holder.rvProductsHorizontal.setAdapter(adapter);
        holder.rvProductsHorizontal.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        holder.rvProductsHorizontal.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
