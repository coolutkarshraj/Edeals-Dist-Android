package com.edeals.dist.fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edeals.dist.models.OrdersModel;
import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.adapters.OrderAdapter;
import com.edeals.dist.APICaller;
import com.edeals.dist.models.OrderModel;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    ProgressBar progressBarOrder;
    static Activity activity;
    private ArrayList<OrderModel> orders;
    private RecyclerView rvOrders;
    private OrderAdapter orderAdapter;
    private TextView tvEmptyOrder;
    private TextView tvRefresh;

    public OrdersFragment() {}
    public static OrdersFragment newInstance(Activity activity) {
        OrdersFragment.activity = activity;
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        init(view);
        bind();
        loadOrders();
        return view;
    }

    private void init(View view) {
        progressBarOrder = view.findViewById(R.id.pbOrders);
        rvOrders = view.findViewById(R.id.rvOrders);
        tvEmptyOrder = view.findViewById(R.id.tvEmptyOrder);
        tvRefresh = view.findViewById(R.id.tvRefreshOrder);
    }

    private void bind() {
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRefresh.setVisibility(View.GONE);
                loadOrders();
            }
        });
    }

    private void loadOrders() {
        progressBarOrder.setVisibility(View.VISIBLE);
        APICaller.getAllOrders(activity,
                new FutureCallback<OrdersModel>() {

                    @Override
                    public void onCompleted(Exception e, OrdersModel result) {
                        progressBarOrder.setVisibility(View.GONE);
                        if (e != null) {
                            tvRefresh.setVisibility(View.VISIBLE);
                            Utils.showAlertDialog(activity, Config.serverNotResponding);
                            return;
                        }

                        tvRefresh.setVisibility(View.GONE);
                        orders = result.orders;
                        loadOrdersInView();
                    }
                }
        );
    }

    private void loadOrdersInView() {
        if (orders.size() == 0) {
            tvEmptyOrder.setVisibility(View.VISIBLE);
            return;
        }
        orderAdapter = new OrderAdapter(activity, orders);
        rvOrders.setAdapter(orderAdapter);
        rvOrders.setVisibility(View.VISIBLE);
    }
}
