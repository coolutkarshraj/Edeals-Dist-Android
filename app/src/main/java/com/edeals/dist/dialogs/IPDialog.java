package com.edeals.dist.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.edeals.dist.R;
import com.edeals.dist.utils.UrlLocator;

import static com.edeals.dist.utils.SessionStorage.tabCount;

/**
 * Created by team edeals on 27-02-2018.
 */

public class IPDialog {

    Activity activity;

    public IPDialog(View view, final Activity activity) {

        this.activity = activity;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabCount++;
                if (tabCount > 2) {
                    tabCount = 0;
                    openDialog();
                }
            }
        });
    }

    public void openDialog(){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ip);

        final EditText etIp = dialog.findViewById(R.id.etIP);
        etIp.setText(UrlLocator.getBaseIP());
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IP = etIp.getText().toString().trim();
                UrlLocator.setBaseIP(IP);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
