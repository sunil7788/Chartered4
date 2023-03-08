package com.chartered4.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.chartered4.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class AppDialogs {

    public static void showAlertDialog(String Msg, Context context) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setMessage(Msg);
        materialAlertDialogBuilder.setCancelable(false)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        materialAlertDialogBuilder.show();
    }

    public static void showSnackBar(Context context, String message, View view) {
        final Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction(context.getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

    public static void showSnackBarAutoHideWithAction(Context context, String message, View view) {
        final Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackBar.setAction(context.getResources().getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

    public static void showSnackBarAutoHide(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
