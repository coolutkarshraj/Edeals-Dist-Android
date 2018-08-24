package com.edeals.dist.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edeals.dist.R;
import com.edeals.dist.utils.Utils;

/**
 * Created by team edeals on 27-02-2018.
 */

public class GroupByOptionsDialog {

    Activity activity;

    public GroupByOptionsDialog(final Activity activity) {

        this.activity = activity;
        openDialog();
    }

    public void openDialog() {
        final android.app.Dialog dialog = Utils.getModalDialog(activity, false, R.layout.dialog_group_by_options);
        final RadioGroup radioGroup = dialog.findViewById(R.id.rgGroupBy);
        TextView tvGroupByDone = dialog.findViewById(R.id.tvGroupByDone);
        dialog.show();
        bind(dialog, tvGroupByDone, radioGroup);
    }

    private void bind(final Dialog dialog, TextView tvGroupByDone, final RadioGroup radioGroup) {
        tvGroupByDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadio = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = dialog.findViewById(selectedRadio);

                switch (selectedRadio) {
                    case R.id.rbBrand:
                        break;
                    case R.id.rbCategory:
                        break;
                    case R.id.rbSale:
                        break;
                }
                Toast.makeText(activity, radioButton.getText(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }
}
