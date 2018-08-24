package com.edeals.dist.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edeals.dist.R;
import com.edeals.dist.holders.OrdersHolder;
import com.edeals.dist.models.OrderModel;
import com.edeals.dist.utils.Utils;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrdersHolder> {
    private Activity activity;
    private ArrayList<OrderModel> orders;
    public OrderAdapter(Activity activity, ArrayList<OrderModel> orders) {

        this.activity = activity;
        this.orders = orders;
    }

    @Override
    public OrdersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_orders, parent, false);
        return new OrdersHolder(view, orders);
    }

    @Override
    public void onBindViewHolder(OrdersHolder holder, int position) {
        OrderModel order = orders.get(position);
        holder.tvProductName.setText(Utils.toSentenceCase(order.productName));
        holder.tvMRP.setText("₹ " + order.MRP);
        holder.tvQuantity.setText(String.valueOf(order.quantity));
        holder.tvQuantity2.setText(String.valueOf(order.quantity));
        double total = order.quantity * order.MRP;
        holder.tvTotalPrice.setText(String.valueOf(total));
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }
}
