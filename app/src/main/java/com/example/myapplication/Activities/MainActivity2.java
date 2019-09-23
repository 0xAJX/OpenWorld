package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.UTDatabaseHandler;
import com.example.myapplication.Models.Image_Item;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    ImageView addImage[];
    private static int RESULT_LOAD_IMAGE = 1;
    private static int[] REQUEST_IMAGE_ID;
    View template;
    ImageView tempimage;
    ImageView img[];
    String id;
    int no_of_images;
    int maxid;
    Bitmap bmp;
    LinearLayout templateloader;
    EditText title;
    UTDatabaseHandler mydb;
    public List<Image_Item> imageItems;
    String utid;

    Boolean isUpdate = false;
    String imagelocation[];
    String titletext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mydb = new UTDatabaseHandler(this);

        imageItems = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.bringToFront();

        /*Fragment fragment = new NewStoryFragment();


        //String id = "1";

        //getIntent().putExtra("template_id", id);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container , fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); */


        try
        {
            if(getIntent().getStringExtra("user_template_id") != null)
            {
                utid = getIntent().getStringExtra("user_template_id");
                titletext = getIntent().getStringExtra("story_title");
                isUpdate = true;
            }
        }
        catch (Exception e)
        {

        }

        id = getIntent().getStringExtra("template_id");

        Log.d("id", id);

        no_of_images = mydb.getNoOfImages(id);

        Log.d("no of images", Integer.toString(no_of_images));
        //no_of_images = getIntent().getIntExtra("no_of_images",1);
        REQUEST_IMAGE_ID = new int[no_of_images];

        maxid = no_of_images;
        img = new ImageView[no_of_images];
        addImage = new ImageView[no_of_images];
        imagelocation = new String[no_of_images];

        for(int i = 0; i < no_of_images; i++)
        {
            imagelocation[i] = "";
        }

        int templateLayout = getResources().getIdentifier(
                "template" + id,
                "layout",
                this.getPackageName());


        templateloader = findViewById(R.id.templateloader);
        template = getLayoutInflater()
                .inflate(templateLayout, templateloader, false);

        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) template.getLayoutParams();

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;*/

        float scalingfactor = 0.85f;
        /*params.height = (int)(1920 * scalingfactor);//(int)(height * scalingfactor);
        params.width = (int)(1080 * scalingfactor);//(int)(width * scalingfactor);
        template.setLayoutParams(params);*/

        template.setScaleX(scalingfactor);
        template.setScaleY(scalingfactor);

        templateloader.addView(template);
        
        for(int i = 1; i <= no_of_images; i++)
        {
            img[i-1] = findViewById(getResources().getIdentifier(
                    "displayimage" + i,
                    "id",
                    this.getPackageName()));

            addImage[i-1] = findViewById(getResources().getIdentifier(
                    "addimage" + i,
                    "id",
                    this.getPackageName()));

            addImage[i-1].setOnClickListener(this);
            img[i-1].setOnTouchListener(this);

            REQUEST_IMAGE_ID[i-1] = i;
        }


        title = findViewById(R.id.storytitle);


        if(isUpdate) {

            try {

                if (titletext != null) {
                    title.setText(titletext);
                }

                imageItems = mydb.loadImages(utid);

                for (int x = 0; x < no_of_images; x++) {
                    if (imageItems.get(x).getImageLocation() != null) {
                        Log.d("query 1", imageItems.get(x).getImageLocation());
                        img[x].setImageBitmap(BitmapFactory.decodeFile(new File(imageItems.get(x).getImageLocation()).getAbsolutePath()));
                        addImage[x].setImageAlpha(0);
                    }
                }


            } catch (Exception e) {
            }
        }
        //img = view.findViewById(R.id.myimageview);
        //img.setMaxZoom(4f);

        /*int ressourceId = getResources().getIdentifier(
                "desctext",
                "id",
                this.getPackageName());*/


        //img[0].setImageBitmap(BitmapFactory.decodeFile(new File("/storage/emulated/0/DCIM/Camera/IMG_20190913_203025.jpg").getAbsolutePath()));

        ImageButton demoview = findViewById(R.id.fullscreenbutton);
        demoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                templateloader.post(new Runnable() {
                    @Override
                    public void run() {

                        for(int x = 0; x < addImage.length ; x++)
                        {
                            addImage[x].setImageAlpha(0);
                        }

                        Intent i = new Intent(getBaseContext(), FullscreenView.class);

                        bmp = getBitmapFromView(templateloader);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        i.putExtra("demoimage", byteArray);

                        for(int x = 0; x < addImage.length ; x++)
                        {
                            if(img[x].getDrawable() == null)
                            {
                                addImage[x].setImageAlpha(255);
                            }
                        }

                        startActivity(i);
                    }
                });
            }
        });

        ImageButton save = findViewById(R.id.imageshare);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                template.post(new Runnable() {
                    @Override
                    public void run() {

                        bmp = getBitmapFromView(template);

                        showPictureDialog();

                        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        //byte[] byteArray = stream.toByteArray();

                        //Bundle bundle = new Bundle();
                        //bundle.putByteArray("image",byteArray);

                        /*Fragment f = new NewStoryFragment();
                        f.setArguments(bundle);

                        getIntent().putExtra("image", byteArray);

                        Fragment fragment = new ShareStoryFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container , fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit(); */

                    }
                });
            }
        });


        FloatingActionButton ftb = findViewById(R.id.addedittext);
        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        for(int i = 1 ; i <=no_of_images ; i++)
        {
            if(v.getId() == getResources().getIdentifier(
                    "addimage" + i,
                    "id",
                    this.getPackageName()))
            {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);

                //showPictureDialog();
            }
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            for(int i = 1; i <= no_of_images; i++)
            {
                if (requestCode == REQUEST_IMAGE_ID[i-1]) {
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        String path = getRealPathFromURI(selectedImageUri);


                        imagelocation[requestCode-1] = path;

                        Log.d("image path", path + "");

                        tempimage = findViewById(getResources().getIdentifier(
                                "displayimage" + requestCode,
                                "id",
                                this.getPackageName()));

                        tempimage.setImageURI(selectedImageUri);
                        addImage[requestCode-1].setImageAlpha(0);
                    }
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    public static String fileNAME;
    public static int framePos = 0;

    private float scale = 0;
    private float newDist = 0;

    // Fields
    private String TAG = this.getClass().getSimpleName();

    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    // Remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        // Dump touch event to log
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: //first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG" );
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;

            case MotionEvent.ACTION_UP: //first finger lifted
            case MotionEvent.ACTION_POINTER_UP: //second finger lifted
                mode = NONE;
                Log.d(TAG, "mode=NONE" );
                break;


            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // ...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    float newDist = spacing(event);
                    matrix.set(savedMatrix);
                    if (newDist > 10f) {
                        scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        matrix.postRotate(r, view.getMeasuredWidth() / 2,
                                view.getMeasuredHeight() / 2);
                    }
                }
                break;

        }
        // Perform the transformation
        view.setImageMatrix(matrix);

        return true; // indicate event was handled

    }
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);

        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);

    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x/2, y/2);

    }


    /** Show an event in the LogCat view, for debugging */

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
                "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_" ).append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid " ).append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")" );
        }

        sb.append("[" );

        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#" ).append(i);
            sb.append("(pid " ).append(event.getPointerId(i));
            sb.append(")=" ).append((int) event.getX(i));
            sb.append("," ).append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())

                sb.append(";" );
        }

        sb.append("]" );
        Log.d(TAG, sb.toString());

    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view

        Log.d("canvas", Integer.toString(view.getWidth()));
        Log.d("canvas", Integer.toString(view.getHeight()));
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);


        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);

        Bitmap resizedbitmap = Bitmap.createScaledBitmap(returnedBitmap, 1080,1920, false);

        //return the bitmap
        //return returnedBitmap;
        return resizedbitmap;
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("SAVE");

        ArrayList<String> temp = new ArrayList<>();

        /*String[] pictureDialogItems = {
                "Save to Device"
                 }; */

        temp.add("Save to Device");

        PackageManager pm = this.getPackageManager();
        if(isPackageInstalled("com.instagram.android", pm))
        {
            temp.add("Instagram");
        }

        String pictureDialogItems[]=temp.toArray(new String[temp.size()]);


        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i;
                        switch (which) {

                            case 0:
                                /*i = new Intent(getBaseContext(), FullscreenView.class);
                                i.putExtra("template_id", id);
                                i.putExtra("mode", "device");
                                for(int x = 1 ; x <= maxid ; x++)
                                {
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                    Matrix m = img[x-1].getImageMatrix();

                                    //BitmapDrawable drawable = (BitmapDrawable) img[x-1].getDrawable();
                                    //drawable.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);

                                    //((BitmapDrawable)img[x-1].getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byte[] byteArray = stream.toByteArray();
                                    getIntent().putExtra("image" + i, byteArray);
                                }
                                startActivity(i);*/
                                saveImage();
                                //choosePhotoFromGallary();
                                break;
                            case 1:
                                /*i = new Intent(getBaseContext(), FullscreenView.class);
                                i.putExtra("template_id", id);
                                i.putExtra("mode", "instagram");
                                for(int x = 1 ; x <= maxid ; x++)
                                {

                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    ((BitmapDrawable)img[x-1].getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byte[] byteArray = stream.toByteArray();
                                    getIntent().putExtra("image" + i, byteArray);
                                }
                                startActivity(i);*/
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
        ImageView imageView = findViewById(R.id.collageimage);
        Drawable mDrawable = imageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
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
        startActivity(Intent.createChooser(share, "Share Your Design!"));
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
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            if(isUpdate)
            {
                UpdateDB(f.getAbsolutePath());
            }
            else {
                addToDB(f.getAbsolutePath());
            }

            Snackbar snackbar = Snackbar.make(findViewById(R.id.templateloader), "Image Saved", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Respond to the click, such as by undoing the modification that caused
                    // this message to be displayed
                    Log.d("here" ,"here");
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

    public void UpdateDB(String filepath)
    {
        mydb.UpdateUserTemplate(utid,title.getText().toString(),filepath);

        for(int i = 0; i<imageItems.size();i++)
        {
            imageItems.get(i).setImageLocation(imagelocation[i]);
        }

        mydb.UpdateImages(utid, imageItems);
    }

    public void addToDB(String filepath)
    {
        String text = title.getText().toString();

        if(title.getText().toString().isEmpty() || text.trim().length() == 0)
        {
            text = "My Story";
        }

        String usertemplateid = mydb.addUserTemplate(id, "" , text, filepath);

        Log.d("utid1" , String.valueOf(utid));

        for(int i = 0; i < img.length; i++)
        {
            Image_Item item = new Image_Item();

            item.setImageID(i+1);
            item.setImageLocation(imagelocation[i]);
            item.setUserTemplateID(usertemplateid);
            imageItems.add(item);
        }

        mydb.addImages(imageItems);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mydb.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydb.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydb = new UTDatabaseHandler(this);
    }
}
