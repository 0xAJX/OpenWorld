package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShareStoryFragment extends Fragment {

    View view;
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

        Fragment f = new NewStoryFragment();



        byte[] byteArray = getActivity().getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView imageView = view.findViewById(R.id.collageimage);
        imageView.setImageBitmap(bmp);

        ImageButton imageButton = view.findViewById(R.id.share);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShare();
            }
        });

        return view;

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
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
        getContext().startActivity(Intent.createChooser(share, "Share Your Design!"));
    }
}
