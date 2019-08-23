package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ShareStoryFragment extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sharestoryfragment, null);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Story");


        //bmImage = findViewById(R.id.bmimage);

        /*view.post(new Runnable() {
        @Override
        public void run() {
            Log.d("canvas0", Integer.toString(view.getWidth()));
            Log.d("canvas0", Integer.toString(view.getHeight()));
            //bmImage.setImageBitmap(getBitmapFromView(view));
        }
        });*/


        return view;
    }

    public void onShare(View view)
    {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
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
