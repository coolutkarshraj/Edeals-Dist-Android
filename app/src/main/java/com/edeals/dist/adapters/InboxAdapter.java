package com.edeals.dist.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.holders.InboxHolder;
import com.edeals.dist.models.NotificationModel;
import com.edeals.dist.utils.UrlLocator;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by team edeals on 05-04-2018.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxHolder> {
    private Activity activity;
    private ArrayList<NotificationModel> notification;

    public InboxAdapter(Activity activity, ArrayList<NotificationModel> notification) {
        this.activity = activity;
        this.notification = notification;
    }

    public void setItems(ArrayList<NotificationModel> notification) {
        this.notification = notification;
    }


    @Override
    public InboxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_inbox, parent, false);
        return new InboxHolder(view, notification);
    }

    @Override
    public void onBindViewHolder(InboxHolder holder, int position) {
        NotificationModel notification = this.notification.get(position);
        holder.tvTitle.setText(Utils.toSentenceCase(notification.title));
        holder.tvMessage.setText(Utils.toSentenceCase(notification.message));
        holder.tvDate.setText(notification.date);
        updateView(notification, holder);
    }

    private void updateView(NotificationModel notification, InboxHolder holder) {
        int messageType = notification.type;
        holder.tvIconText.setVisibility(View.GONE);
        holder.tvPrice.setVisibility(View.GONE);
        holder.ivNotification.setImageResource(R.drawable.notify);
        if (messageType == Config.PaymentReceived) {
            holder.ivNotification.setImageResource(R.drawable.notify_payment_recieved);
            holder.tvPrice.setText(notification.orderPrice + " ₹");
            holder.tvIconText.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
        } else if (messageType == Config.StockOutdated) {
            holder.ivNotification.setImageResource(R.drawable.notify_red);
        } else if (messageType == Config.NewProductInTrend) {
            holder.ivNotification.setImageResource(R.drawable.notify_trending);
        } else if (messageType == Config.NewOrder) {
            holder.ivNotification.setImageResource(R.drawable.notifi_new_order);
        } else if (messageType == Config.OrderDelivered) {
            holder.ivNotification.setImageResource(R.drawable.notifi_tick);
        }
    }

    @Override
    public int getItemCount() {
        return notification.size();
    }
}
