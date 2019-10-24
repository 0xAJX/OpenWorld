package com.example.myapplication.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Models.Image_Item;
import com.example.myapplication.R;
import com.example.myapplication.UTDatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ShareBottomSheetFragment extends BottomSheetDialogFragment {

    Bitmap bitmap;
    List<Image_Item> imageItems;
    Bundle bundle;
    String imagelocation[];
    String bitmapLocation = null;
    UTDatabaseHandler databaseHandler;

    public ShareBottomSheetFragment(String bitmapLocation) {
        this.bitmapLocation = bitmapLocation;
    }

    public ShareBottomSheetFragment(Bundle bundle, Bitmap bitmap, List<Image_Item> imageItems) {
        this.bundle = bundle;
        this.bitmap = bitmap;
        this.imageItems = imageItems;
        imagelocation = bundle.getStringArray("imageLocation");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_bottom_sheet_fragment, null, false);
        databaseHandler = new UTDatabaseHandler(getContext());
        BottomNavigationView navigationView = view.findViewById(R.id.nav_view);

        if (bitmapLocation != null) {
           navigationView.getMenu().findItem(R.id.device_share).setVisible(false);
        }

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {

                case R.id.device_share:
                    saveImage();
                    break;

                case R.id.facebook_share:
                    if (isPackageInstalled("com.facebook.android", getActivity().getPackageManager())) {
                        onShare("com.facebook.android");
                    }
                    else {

                    }
                    break;

                case R.id.instagram_share:
                    if (isPackageInstalled("com.instagram.android", getActivity().getPackageManager())) {
                        onShare("com.instagram.android");
                    }
                    else {

                    }
                    break;

            }
            return true;
        }
    };

    public void onShare(String packageName) {

        Uri uri;
        if (bitmapLocation == null) {
            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    bitmap, "Design", null);
            uri = Uri.parse(path);
        } else {
            uri = Uri.parse(bitmapLocation);
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        //Intent share = new Intent("com.instagram.share.ADD_TO_STORY");
        share.setType("image/*");
        //share.setPackage("com.whatsapp");
        //share.setPackage("com.facebook.android");
        //share.setPackage("com.instagram.android");
        share.setPackage(packageName);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
        startActivity(Intent.createChooser(share, "Share Your Design!"));
    }


    public String saveImage() {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/app/image/");
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
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            if (bundle.getBoolean("isUpdate")) {
                UpdateDB(f.getAbsolutePath());
            } else {
                addToDB(f.getAbsolutePath());
            }

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.templateloader), "Image Saved", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Respond to the click, such as by undoing the modification that caused
                    // this message to be displayed
                    //Log.d("here", "here");
                }
            });
            //int snackbarTextId = android.support.design.R.id.snackbar_text;
            //TextView textView = snackbar.getView().findViewById(snackbarTextId);
            //textView.setTextColor(getColor(R.color.colorAccent));
            snackbar.show();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return "";
    }

    public void UpdateDB(String filepath) {
        databaseHandler.UpdateUserTemplate(bundle.getString("utid"), bundle.getString("title"), filepath);

        for (int i = 0; i < imageItems.size(); i++) {
            imageItems.get(i).setImageLocation(imagelocation[i]);
        }

        databaseHandler.UpdateImages(bundle.getString("utid"), imageItems);
    }

    public void addToDB(String filepath) {
        String text = bundle.getString("title");

        if (bundle.getString("title").isEmpty() || text.trim().length() == 0) {
            text = "My Story";
        }

        String usertemplateid = databaseHandler.addUserTemplate(bundle.getString("template_id"), "", text, filepath);

        //Log.d("utid1", String.valueOf(utid));

        for (int i = 0; i < bundle.getInt("no_of_images"); i++) {
            Image_Item item = new Image_Item();

            item.setImageID(i + 1);
            item.setImageLocation(imagelocation[i]);
            item.setUserTemplateID(usertemplateid);
            imageItems.add(item);
        }

        databaseHandler.addImages(imageItems);
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
}
