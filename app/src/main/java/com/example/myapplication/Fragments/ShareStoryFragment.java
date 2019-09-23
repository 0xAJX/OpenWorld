package com.example.myapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ShareStoryFragment extends Fragment {

    View view;
    Bitmap bmp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sharestoryfragment, null);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Share Story");


        //bmImage = findViewById(R.id.bmimage);

        /*view.post(new Runnable() {
        @Override
        public void run() {
            Log.d("canvas0", Integer.toString(view.getWidth()));
            Log.d("canvas0", Integer.toString(view.getHeight()));
            //bmImage.setImageBitmap(getBitmapFromView(view));
        }
        });*/


        byte[] byteArray = getActivity().getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView imageView = view.findViewById(R.id.collageimage);
        imageView.setImageBitmap(bmp);

        ImageButton imageButton = view.findViewById(R.id.share);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onShare();
                showPictureDialog();
            }
        });

        return view;

    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("SAVE");

        ArrayList<String> temp = new ArrayList<>();

        /*String[] pictureDialogItems = {
                "Save to Device"
                 }; */

        temp.add("Save to Device");

        PackageManager pm = getContext().getPackageManager();
        if(isPackageInstalled("com.instagram.android", pm))
        {
            temp.add("Instagram");
        }

        String pictureDialogItems[]=temp.toArray(new String[temp.size()]);


        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                saveImage();
                                //choosePhotoFromGallary();
                                break;
                            case 1:
                                onShare();
                                //takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void onShare()
    {
        ImageView imageView = (ImageView) view.findViewById(R.id.collageimage);
        Drawable mDrawable = imageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                mBitmap, "Design", null);

        Uri uri = Uri.parse(path);

        Intent share = new Intent(Intent.ACTION_SEND);
        //Intent share = new Intent("com.instagram.share.ADD_TO_STORY");
        share.setType("image/*");
        //share.setPackage("com.whatsapp");
        //share.setPackage("com.facebook.android");
        share.setPackage("com.instagram.android");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
        getContext().startActivity(Intent.createChooser(share, "Share Your Design!"));
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }

    public String saveImage() {

        Bitmap myBitmap = bmp;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/image/");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
