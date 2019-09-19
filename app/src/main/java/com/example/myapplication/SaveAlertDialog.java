package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SaveAlertDialog{


    public SaveAlertDialog(final Context context, final String imagelocation)
    {
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        AlertDialog dialog;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.savepopup, null);

        Button instagram = view.findViewById(R.id.instagramshare);
        Button facebook = view.findViewById(R.id.facebookshare);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShare(context, imagelocation);
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

    public void onShare(Context context, String imagelocation)
    {

        Uri uri = Uri.parse(imagelocation);
        Intent share = new Intent(Intent.ACTION_SEND);
        //Intent share = new Intent("com.instagram.share.ADD_TO_STORY");
        share.setType("image/*");
        //share.setPackage("com.whatsapp");
        //share.setPackage("com.facebook.android");
        share.setPackage("com.instagram.android");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
        context.startActivity(Intent.createChooser(share, "Share Your Design!"));
    }

}
