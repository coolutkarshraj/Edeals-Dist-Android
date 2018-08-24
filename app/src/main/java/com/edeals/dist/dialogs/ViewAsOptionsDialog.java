package com.edeals.dist.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.edeals.dist.R;
import com.edeals.dist.fragments.StockFragment;
import com.edeals.dist.utils.SessionStorage;
import com.edeals.dist.utils.Utils;

/**
 * Created by team edeals on 27-02-2018.
 */

public class ViewAsOptionsDialog {

    Activity activity;

    public ViewAsOptionsDialog(final Activity activity) {

        this.activity = activity;
        openDialog();
    }

    public void openDialog() {
        final Dialog dialog = Utils.getModalDialog(activity, false, R.layout.dialog_view_as_options);
        final RadioGroup radioGroup = dialog.findViewById(R.id.rgViewAs);
        radioGroup.check(SessionStorage.lastViewAsOptionSelected);
        TextView tvViewAsDone = dialog.findViewById(R.id.tvViewAsDone);
        dialog.show();
        bind(dialog, tvViewAsDone, radioGroup);
    }

    private void bind(final Dialog dialog, TextView tvViewAsDone, final RadioGroup radioGroup) {
        tvViewAsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadio = radioGroup.getCheckedRadioButtonId();
                SessionStorage.lastViewAsOptionSelected = selectedRadio;
                switch (selectedRadio) {
                    case R.id.rbListView:
                        activity.sendBroadcast(new Intent(StockFragment.SHOW_LIST_VIEW));
                        break;
                    case R.id.rbGridView:
                        activity.sendBroadcast(new Intent(StockFragment.SHOW_GRID_VIEW));
                        break;
                }
                dialog.dismiss();
            }
        });
    }
}
