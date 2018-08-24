package com.edeals.dist.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.models.InboxModel;
import com.edeals.dist.models.OrderModel;

import java.util.ArrayList;

public class OrdersHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    private final ArrayList<OrderModel> orders;
    public TextView tvProductName;
    public TextView tvMRP;
    public TextView tvQuantity;
    public TextView tvQuantity2;
    public TextView tvTotalPrice;

    public OrdersHolder(View view, ArrayList<OrderModel> orders) {

        super(view);
        this.orders = orders;
        tvProductName = view.findViewById(R.id.tvProductName);
        tvMRP = view.findViewById(R.id.tvMRP);
        tvQuantity = view.findViewById(R.id.tvQuantity);
        tvQuantity2 = view.findViewById(R.id.tvQuantity2);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
    }

    @Override
    public void onClick(View view) {
        OrderModel product = orders.get(getAdapterPosition());
    }
}
