package com.edeals.dist.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Window;

import com.edeals.dist.R;

/**
 * Created by team edeals on 19-02-2018.
 */

public class Utils {
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
//        if (!email.contains(".") || !email.contains("@") || email.length() < 5) {
//            return false;
//        }
        return true;
    }

    public static boolean isMobileNumberValid(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
//        if (number.length() != 10) {
//            return false;
//        }
        return true;
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
//        if (password.length() < 8) {
//            return false;
//        }
        return true;
    }

    public static boolean isUserNameValid(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
//        if (password.length() < 8) {
//            return false;
//        }
        return true;
    }

    public static String toSentenceCase(String string) {
        if (string == null || string.isEmpty())
            return "";

        String charAt0 = String.valueOf(string.charAt(0));
        return string.replaceFirst(charAt0, charAt0.toUpperCase());
    }

    public static String toTitleCase(String string) {
        if (string == null || string.isEmpty())
            return "";

        String[] words = string.split("\\s+");
        String str = "";
        for (int i = 0; i < words.length; i++) {
            str += toSentenceCase(words[i].replaceAll("[^\\w]", "")) + " ";
        }
        str += "\b";
        return str;
    }

    public static void showAlertDialog(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.show();
    }

    public static ProgressDialog getProgressDialog(Activity activity, String message) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }

    public static Dialog getModalDialog(Activity activity, boolean cancelable, int layout) {
        final android.app.Dialog dialog = new android.app.Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(cancelable);
        dialog.setContentView(layout);
        return dialog;
    }
}
