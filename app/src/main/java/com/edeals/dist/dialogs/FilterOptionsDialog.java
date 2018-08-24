package com.edeals.dist.dialogs;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.edeals.dist.R;

/**
 * Created by team edeals on 27-02-2018.
 */

public class FilterOptionsDialog {

    Activity activity;

    public FilterOptionsDialog(final Activity activity) {

        this.activity = activity;
        openDialog();
    }

    public void openDialog() {
        final android.app.Dialog dialog = new android.app.Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter_options);

        final TextView tvFilterDone = dialog.findViewById(R.id.tvFilterDone);
        tvFilterDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
