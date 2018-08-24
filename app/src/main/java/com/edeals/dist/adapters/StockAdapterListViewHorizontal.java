package com.edeals.dist.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edeals.dist.R;
import com.edeals.dist.holders.StockHolderHorizontal;
import com.edeals.dist.models.ProductModel;
import com.edeals.dist.utils.Utils;

import java.util.ArrayList;

/**
 * Created by team edeals on 3/22/2018.
 */

public class StockAdapterListViewHorizontal extends RecyclerView.Adapter<StockHolderHorizontal> {
    private Activity activity;
    private ArrayList<ProductModel> products;

    public StockAdapterListViewHorizontal(Activity activity, ArrayList<ProductModel> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public StockHolderHorizontal onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_stock_list_horizontal, parent, false);
        return new StockHolderHorizontal(view, products);
    }

    @Override
    public void onBindViewHolder(StockHolderHorizontal holder, int position) {
        ProductModel product = products.get(position);
        holder.tvProductName.setText(Utils.toSentenceCase(product.productName));
        holder.tvCategoryName.setText(product.categoryName);
        holder.tvPrice.setText("₹ " + product.MRP);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
