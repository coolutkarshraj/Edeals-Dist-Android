package com.edeals.dist.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edeals.dist.Config;
import com.edeals.dist.R;
import com.edeals.dist.adapters.StockAdapterGridViewChild;
import com.edeals.dist.adapters.StockAdapterGridViewParent;
import com.edeals.dist.adapters.StockAdapterListViewVertical;
import com.edeals.dist.APICaller;
import com.edeals.dist.dialogs.FilterOptionsDialog;
import com.edeals.dist.dialogs.GroupByOptionsDialog;
import com.edeals.dist.dialogs.ViewAsOptionsDialog;
import com.edeals.dist.models.BrandAndProducts;
import com.edeals.dist.models.BrandAndProductsModel;
import com.edeals.dist.utils.SessionStorage;
import com.edeals.dist.utils.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import static com.edeals.dist.StaticData.gridColor;

public class StockFragment extends Fragment {

    ProgressBar progressBarStock;
    static Activity activity;
    private ArrayList<BrandAndProducts> brandAndProducts;
    private RecyclerView rvDefaultProducts;
    private StockAdapterListViewVertical defaultProductsAdapter;
    private TextView tvEmptyStock;
    private TextView tvRefresh;
    private TextView tvGroupByIcon;
    private TextView tvGroupByText;
    private TextView tvMoreIcon;
    private TextView tvMoreText;
    private TextView tvViewAsIcon;
    private TextView tvViewAsText;
    private TextView tvFilterIcon;
    private TextView tvFilterText;
    private CardView cViewFooter;
    private GridView gridViewParent, gridViewChild;
    private BroadcastReceiver br;

    public static String SHOW_LIST_VIEW = "show_list_view";
    public static String SHOW_GRID_VIEW = "show_grid_view";
    public static String HIDE_GRID_VIEW_CHILD = "hide_grid_view_child";

    public StockFragment() {
    }

    public static StockFragment newInstance(Activity activity) {
        StockFragment.activity = activity;
        StockFragment fragment = new StockFragment();
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
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        init(view);
        bind();
        loadDefaultProducts();
        return view;
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(br);
        super.onDestroyView();
    }

    private void init(View view) {
        progressBarStock = view.findViewById(R.id.pbStock);
        rvDefaultProducts = view.findViewById(R.id.rvProducts);
        tvEmptyStock = view.findViewById(R.id.tvEmptyStock);
        tvRefresh = view.findViewById(R.id.tvRefresh);
        tvGroupByIcon = view.findViewById(R.id.tvGroupByIcon);
        tvGroupByText = view.findViewById(R.id.tvGroupByText);
        tvViewAsIcon = view.findViewById(R.id.tvViewAsIcon);
        tvViewAsText = view.findViewById(R.id.tvViewAsText);
        tvFilterIcon = view.findViewById(R.id.tvFilterIcon);
        tvFilterText = view.findViewById(R.id.tvFilterText);
        tvMoreIcon = view.findViewById(R.id.tvMoreIcon);
        tvMoreText = view.findViewById(R.id.tvMoreText);
        cViewFooter = view.findViewById(R.id.cViewFooter);
        gridViewParent = view.findViewById(R.id.gridViewParent);
        gridViewChild = view.findViewById(R.id.gridViewChild);
    }

    private void bind() {
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRefresh.setVisibility(View.GONE);
                loadDefaultProducts();
            }
        });

        bindReceiver();
        bindGroupByIconListener();
        bindViewAsIconListener();
        bindFilterIconListener();
        bindMoreIconListener();
    }

    private void loadDefaultProducts() {
        progressBarStock.setVisibility(View.VISIBLE);
        APICaller.getProducts(activity,
                new FutureCallback<BrandAndProductsModel>() {
                    @Override
                    public void onCompleted(Exception e, BrandAndProductsModel result) {
                        progressBarStock.setVisibility(View.GONE);
                        if (e != null) {
                            tvRefresh.setVisibility(View.VISIBLE);
                            Utils.showAlertDialog(activity, Config.serverNotResponding);
                            return;
                        }
                        tvRefresh.setVisibility(View.GONE);
                        brandAndProducts = result.products;
                        loadListView();
                    }
                }
        );
    }

    private void bindGroupByIconListener() {
        tvGroupByIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GroupByOptionsDialog(activity);
            }
        });
        tvGroupByText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GroupByOptionsDialog(activity);
            }
        });
    }

    private void bindViewAsIconListener() {
        tvViewAsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ViewAsOptionsDialog(activity);
            }
        });
        tvViewAsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ViewAsOptionsDialog(activity);
            }
        });
    }

    private void bindFilterIconListener() {
        tvFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FilterOptionsDialog(activity);
            }
        });
        tvFilterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FilterOptionsDialog(activity);
            }
        });
    }

    private void bindMoreIconListener() {
        tvMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUp();
            }
        });
        tvMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUp();
            }
        });
    }

    private void loadListView() {
        gridViewParent.setVisibility(View.GONE);
        gridViewChild.setVisibility(View.GONE);
        if (brandAndProducts.size() == 0) {
            tvEmptyStock.setVisibility(View.VISIBLE);
            return;
        }
        defaultProductsAdapter = new StockAdapterListViewVertical(activity, brandAndProducts);
        rvDefaultProducts.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rvDefaultProducts.setLayoutManager(horizontalLayoutManager);
        rvDefaultProducts.setAdapter(defaultProductsAdapter);
        rvDefaultProducts.setVisibility(View.VISIBLE);
    }

    private void loadGridView() {
        rvDefaultProducts.setVisibility(View.GONE);
        if (brandAndProducts.size() == 0) {
            tvEmptyStock.setVisibility(View.VISIBLE);
            return;
        }
        StockAdapterGridViewParent adapter = new StockAdapterGridViewParent(gridColor, activity, brandAndProducts);
        gridViewParent.setAdapter(adapter);
        gridViewParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockAdapterGridViewChild adapter = new StockAdapterGridViewChild(gridColor, activity, brandAndProducts.get(position).products);
                gridViewChild.setAdapter(adapter);
                gridViewParent.setVisibility(View.GONE);
                gridViewChild.setVisibility(View.VISIBLE);
                SessionStorage.childGridActive = true;
            }
        });
        gridViewParent.setVisibility(View.VISIBLE);
    }

    public void bindReceiver() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().matches(SHOW_LIST_VIEW)) {
                    loadListView();
                } else if (intent.getAction().matches(SHOW_GRID_VIEW)) {
                    loadGridView();
                } else if (intent.getAction().matches(HIDE_GRID_VIEW_CHILD)) {
                    gridViewChild.setVisibility(View.GONE);
                    gridViewParent.setVisibility(View.VISIBLE);
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(SHOW_LIST_VIEW);
        filter.addAction(SHOW_GRID_VIEW);
        filter.addAction(HIDE_GRID_VIEW_CHILD);
        getActivity().registerReceiver(br, filter);
    }

    private void openPopUp() {
        PopupMenu popup = new PopupMenu(activity, cViewFooter, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_footer_more_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item0:
                        Toast.makeText(activity, "item 0 clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item1:
                        Toast.makeText(activity, "item 1 clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }
}
