package com.edeals.dist.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.models.BrandAndProducts;

import java.util.ArrayList;

/**
 * Created by team edeals on 19-02-2018.
 */

public class StockHolderVertical extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    private final ArrayList<BrandAndProducts> products;
    public TextView tvBrandName;
    public RecyclerView rvProductsHorizontal;

    public StockHolderVertical(View view, ArrayList<BrandAndProducts> products) {
        super(view);
        this.products = products;
        tvBrandName = view.findViewById(R.id.tvBrandName);
        rvProductsHorizontal = view.findViewById(R.id.rvProductsHorizontal);
    }

    @Override
    public void onClick(View view) {
//        Product0Model product = products.get(getAdapterPosition());
    }
}
