package com.edeals.dist.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by team edeals on 19-02-2018.
 */

public class StockHolderHorizontal extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    private final ArrayList<ProductModel> products;
    public TextView tvProductName;
    public TextView tvCategoryName;
    public TextView tvPrice;

    public StockHolderHorizontal(View view, ArrayList<ProductModel> products) {
        super(view);
        this.products = products;
        tvProductName = view.findViewById(R.id.tvProductName);
        tvCategoryName = view.findViewById(R.id.tvCategoryName);
        tvPrice = view.findViewById(R.id.tvPrice);
    }

    @Override
    public void onClick(View view) {
        ProductModel product = products.get(getAdapterPosition());
    }
}
