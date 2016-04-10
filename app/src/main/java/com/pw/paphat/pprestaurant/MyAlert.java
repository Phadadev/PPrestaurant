package com.pw.paphat.pprestaurant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by paphat on 4/10/2016 AD.
 */
public class MyAlert {//set alert

    public void myDialog(Context context,
                         String strTitle,
                         String strMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setIcon(R.drawable.icon_question);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setCancelable(false);//set don't use back button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();//want to dialog gone
            }
        });
        builder.show();
    }


}   //Main Class
