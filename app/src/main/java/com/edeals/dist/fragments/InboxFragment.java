package com.edeals.dist.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edeals.dist.APICaller;
import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.adapters.InboxAdapter;
import com.edeals.dist.models.InboxModel;
import com.edeals.dist.models.NotificationModel;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

public class InboxFragment extends Fragment {
    ProgressBar progressBarInbox;
    static Activity activity;
    private ArrayList<NotificationModel> notifications;
    private ArrayList<NotificationModel> notificationsWorkingCopy;
    private RecyclerView rvInbox;
    private InboxAdapter inboxAdapter;
    private TextView tvEmptyInbox;
    private LinearLayout llEmptyInbox;
    private TextView tvRefresh;
    private BroadcastReceiver br;
    public final static String Filter_Inbox = "filter_inbox";

    public InboxFragment() {
    }

    public static InboxFragment newInstance(Activity activity) {
        InboxFragment.activity = activity;
        InboxFragment fragment = new InboxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        init(view);
        bind();
        loadInbox();
        return view;
    }

    private void init(View view) {
        progressBarInbox = view.findViewById(R.id.pbInbox);
        rvInbox = view.findViewById(R.id.rvInbox);
        llEmptyInbox = view.findViewById(R.id.llEmptyInbox);
        tvEmptyInbox = view.findViewById(R.id.tvEmptyInbox);
        tvRefresh = view.findViewById(R.id.tvRefreshInbox);
    }

    private void bind() {
        bindReceiver();
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRefresh.setVisibility(View.GONE);
                loadInbox();
            }
        });
    }

    private void loadInbox() {
        progressBarInbox.setVisibility(View.VISIBLE);
        APICaller.getInbox(activity,
                new FutureCallback<InboxModel>() {

                    @Override
                    public void onCompleted(Exception e, InboxModel result) {
                        progressBarInbox.setVisibility(View.GONE);
                        if (e != null) {
                            tvRefresh.setVisibility(View.VISIBLE);
                            Utils.showAlertDialog(activity, Config.serverNotResponding);
                            return;
                        }

                        if (result == null) {
                            tvRefresh.setVisibility(View.VISIBLE);
                            return;
                        }

                        tvRefresh.setVisibility(View.GONE);
                        notifications = result.inbox;
                        notificationsWorkingCopy = (ArrayList<NotificationModel>) notifications.clone();
                        loadInboxInView();
                    }
                }
        );
    }

    private void loadInboxInView() {
        if (notificationsWorkingCopy.size() == 0) {
            tvEmptyInbox.setText(Config.emptyInbox);
            llEmptyInbox.setVisibility(View.VISIBLE);
            return;
        }
        inboxAdapter = new InboxAdapter(activity, notificationsWorkingCopy);
        rvInbox.setAdapter(inboxAdapter);
        rvInbox.setVisibility(View.VISIBLE);
    }

    public void bindReceiver() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().matches(Filter_Inbox)) {
                    String searchText = intent.getStringExtra("searchText").toLowerCase();
                    filterInbox(searchText);
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Filter_Inbox);
        getActivity().registerReceiver(br, filter);
    }

    private void filterInbox(String searchText) {
        notificationsWorkingCopy.clear();
        for (NotificationModel n : notifications) {
            if (n.title.toLowerCase().contains(searchText) ||
                    n.message.toLowerCase().contains(searchText) ||
                    n.date.toLowerCase().contains(searchText) ||
                    String.valueOf(n.orderPrice).contains(searchText)) {
                notificationsWorkingCopy.add(n);
            }
        }

        if (notificationsWorkingCopy.size() == 0) {
            rvInbox.setVisibility(View.GONE);
            tvEmptyInbox.setText(Config.zeroSearchResuls);
            llEmptyInbox.setVisibility(View.VISIBLE);
            return;
        }

        rvInbox.setVisibility(View.VISIBLE);
        llEmptyInbox.setVisibility(View.GONE);
        inboxAdapter.setItems(notificationsWorkingCopy);
        inboxAdapter.notifyDataSetChanged();
    }
}
