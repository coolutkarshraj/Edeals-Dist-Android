package com.edeals.dist.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.models.InboxModel;
import com.edeals.dist.models.NotificationModel;

import java.util.ArrayList;

/**
 * Created by himanshu on 05-04-2018.
 */

public class InboxHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private final ArrayList<NotificationModel> inbox;
    public TextView tvTitle;
    public TextView tvMessage;
    public ImageView ivNotification;
    public TextView tvPrice;
    public TextView tvIconText;
    public TextView tvDate;

    public InboxHolder(View view, ArrayList<NotificationModel> inbox) {
        super(view);
        this.inbox = inbox;
        tvTitle = view.findViewById(R.id.tvTitle);
        tvMessage = view.findViewById(R.id.tvMessage);
        ivNotification = view.findViewById(R.id.ivNotification);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvIconText = view.findViewById(R.id.tvIconText);
        tvDate = view.findViewById(R.id.tvDate);
    }

    @Override
    public void onClick(View v) {
        NotificationModel notification = inbox.get(getAdapterPosition());
    }
}
