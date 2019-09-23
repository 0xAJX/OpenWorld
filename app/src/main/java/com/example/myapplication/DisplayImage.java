package com.example.myapplication;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DisplayImage extends ImageView{

    public DisplayImage(Context context) {
        super(context);
    }

    public DisplayImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DisplayImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
