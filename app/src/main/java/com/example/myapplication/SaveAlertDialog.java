package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class SaveAlertDialog{


    public SaveAlertDialog(Context context)
    {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        AlertDialog dialog;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.savepopup, null);

        Button device = view.findViewById(R.id.deviceshare);
        Button instagram = view.findViewById(R.id.instagramshare);
        Button facebook = view.findViewById(R.id.facebookshare);

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        dialogbuilder.setView(view);
        dialog = dialogbuilder.create();
        dialog.show();
    }



}
