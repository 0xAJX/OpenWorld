package com.havrtz.unfold.activities

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.havrtz.unfold.fragments.ShareBottomSheetFragment
import com.havrtz.unfold.models.StoryElement
import com.havrtz.unfold.R
import com.havrtz.unfold.viewmodels.TemplateViewModel
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.ExecutionException

class UpsertPageActivity : AppCompatActivity(), OnTouchListener, View.OnClickListener {
    lateinit var addImage: Array<ImageView?>
    lateinit var displayImage: Array<ImageView?>
    lateinit var imageItems: List<StoryElement>
    lateinit var imageLocation: Array<String?>
    var isUpdate = false
    var noOfImages = 0
    var storyBitmap: Bitmap? = null
    lateinit var template: View
    var templateID = 0
    lateinit var tempImage: ImageView
    lateinit var templateLoader: LinearLayout
    lateinit var title: EditText
    var userTemplateID = 0

    /** Touch event variables  */
    var d = 0f
    var lastEvent: FloatArray? = null
    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    var newRot = 0f
    private val newDist = 0f
    private val scale = 0f

    // Fields
    private val TAG = this.javaClass.simpleName
    private var mode = NONE

    // Remember some things for zooming
    private val start = PointF()
    private val mid = PointF()
    var oldDist = 1f

    /** Touch event variables  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upsert_page)
        imageItems = ArrayList()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.bringToFront()
        /** Check if it is create or update story  */
        /*try {
            if (getIntent().getStringExtra("user_template_id") != null) {
                userTemplateID = getIntent().getStringExtra("user_template_id");
                titleText = getIntent().getStringExtra("story_title");
                isUpdate = true;
            }
        } catch (Exception e) {

        }*/
        /** Check if it is create or update story  */
        /** Get no. of images from TemplateViewModel  */
        templateID = intent.getIntExtra("template_id", 1)
        val templateViewModel = ViewModelProviders.of(this).get(TemplateViewModel::class.java)
        try {
            noOfImages = templateViewModel.getTemplateById(templateID).no_of_images
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        /** Get no. of images from TemplateViewModel  */
        Log.d("no of images", Integer.toString(noOfImages))
        //no_of_images = getIntent().getIntExtra("no_of_images",1);
        REQUEST_IMAGE_ID = IntArray(noOfImages)
        displayImage = arrayOfNulls(noOfImages)
        addImage = arrayOfNulls(noOfImages)
        imageLocation = arrayOfNulls(noOfImages)
        /** Making all values of array empty so that no error is caused ahead  */
        for (i in 0 until noOfImages) {
            imageLocation[i] = ""
        }
        /** Making all values of array empty so that no error is caused ahead  */
        /** Get the required layout based on template ID  */
        val templateLayout = resources.getIdentifier(
                "template$templateID",
                "layout",
                this.packageName)
        /** Get the required layout based on template ID  */
        templateLoader = findViewById(R.id.templateloader)
        template = layoutInflater
                .inflate(templateLayout, templateLoader, false)

        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) template.getLayoutParams();

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;*/
        /** Scaling the template down  */
        val scalingfactor = 0.85f
        /*params.height = (int)(1920 * scalingfactor);//(int)(height * scalingfactor);
        params.width = (int)(1080 * scalingfactor);//(int)(width * scalingfactor);
        template.setLayoutParams(params);*/template.setScaleX(scalingfactor)
        template.setScaleY(scalingfactor)
        /** Scaling the template down  */
        /** Add the template to template loader  */
        templateLoader.addView(template)
        /** Add the template to template loader  */
        /** Get imageViews based on no. of images  */
        for (i in 1..noOfImages) {
            displayImage[i - 1] = findViewById(resources.getIdentifier(
                    "displayimage$i",
                    "id",
                    this.packageName))
            addImage[i - 1] = findViewById(resources.getIdentifier(
                    "addimage$i",
                    "id",
                    this.packageName))
            addImage[i - 1]?.setOnClickListener(this)
            displayImage[i - 1]?.setOnTouchListener(this)
            REQUEST_IMAGE_ID[i - 1] = i
        }
        /** Get imageViews based on no. of images  */
        title = findViewById(R.id.storytitle)
        /** IF its update then restore images to imageViews  */
        /*if (isUpdate) {

            try {

                if (titleText != null) {
                    title.setText(titleText);
                }

                imageItems = databaseHandler.loadImages(userTemplateID);

                for (int x = 0; x < noOfImages; x++) {
                    if (imageItems.get(x).getImageLocation() != null) {
                        Log.d("query 1", imageItems.get(x).getImageLocation());
                        displayImage[x].setImageBitmap(BitmapFactory.decodeFile(new File(imageItems.get(x).getImageLocation()).getAbsolutePath()));
                        addImage[x].setImageAlpha(0);
                    }
                }


            } catch (Exception e) {
            }
        }*/
        /** IF its update then restore images to imageViews  */
        //img = view.findViewById(R.id.myimageview);
        //img.setMaxZoom(4f);

        /*int ressourceId = getResources().getIdentifier(
                "desctext",
                "id",
                this.getPackageName());*/


        //img[0].setImageBitmap(BitmapFactory.decodeFile(new File("/storage/emulated/0/DCIM/Camera/IMG_20190913_203025.jpg").getAbsolutePath()));
        val demoview = findViewById<ImageButton>(R.id.fullscreenbutton)
        demoview.setOnClickListener {
            templateLoader.post(Runnable {
                for (x in addImage.indices) {
                    addImage[x]!!.imageAlpha = 0
                }
                val i = Intent(baseContext, DisplayStoryPageActivity::class.java)
                storyBitmap = getBitmapFromView(template)
                val stream = ByteArrayOutputStream()
                storyBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                i.putExtra("demo_image", byteArray)
                for (x in addImage.indices) {
                    if (displayImage[x]!!.drawable == null) {
                        addImage[x]!!.imageAlpha = 255
                    }
                }
                startActivity(i)
            })
        }
        /** IF save button is clicked then send data to share bottom sheet  */
        val save = findViewById<ImageButton>(R.id.imageshare)
        save.setOnClickListener {
            template.post(Runnable {
                storyBitmap = getBitmapFromView(template)
                val bundle = Bundle()
                bundle.putInt("user_template_id", userTemplateID)
                bundle.putInt("template_id", templateID)
                bundle.putString("title", title.getText().toString())
                bundle.putStringArray("imageLocation", imageLocation)
                bundle.putBoolean("isUpdate", isUpdate)
                bundle.putInt("no_of_images", noOfImages)
                val shareBottomSheetFragment = ShareBottomSheetFragment(bundle, storyBitmap, imageItems)
                shareBottomSheetFragment.show(supportFragmentManager, shareBottomSheetFragment.getTag())

                //showPictureDialog();

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
            })
        }
        /** IF save button is clicked then send data to share bottom sheet  */

        /*FloatingActionButton ftb = findViewById(R.id.addedittext);
        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    /** addImage onclick  */
    override fun onClick(v: View) {
        for (i in 1..noOfImages) {
            if (v.id == resources.getIdentifier(
                            "addimage$i",
                            "id",
                            this.packageName)) {
                val intent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                //intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), i)
            }
        }
    }
    /** addImage onclick  */
    /** Get the actual path of image added to displayImage  */
    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path.toString()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
    /** Get the actual path of image added to displayImage  */
    /** Used to add selected image to display image  */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            for (i in 1..noOfImages) {
                if (requestCode == REQUEST_IMAGE_ID[i - 1]) {
                    val selectedImageUri = data!!.data
                    if (null != selectedImageUri) {
                        val path = getRealPathFromURI(selectedImageUri)
                        imageLocation[requestCode - 1] = path
                        Log.d("image path", path + "")
                        tempImage = findViewById(resources.getIdentifier(
                                "displayimage$requestCode",
                                "id",
                                this.packageName))
                        tempImage.setImageURI(selectedImageUri)
                        addImage[requestCode - 1]!!.imageAlpha = 0
                    }
                }
            }
        }
    }
    /** Used to add selected image to display image  */
    /** Convert created story to image  */
    private fun getBitmapFromView(view: View?): Bitmap {
        //Define a bitmap with the same size as the view
        Log.d("canvas", Integer.toString(view!!.width))
        Log.d("canvas", Integer.toString(view.height))
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)


        //Bind a canvas to it
        val canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)

        //return the bitmap
        //return returnedBitmap;
        return Bitmap.createScaledBitmap(returnedBitmap, 1080, 1920, false)
    }
    /** Convert created story to image  */
    /** Image drag, scale and rotate functionality  */ //TODO make a separate class
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val view = v as ImageView
        view.scaleType = ImageView.ScaleType.MATRIX
        val scale: Float
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(matrix)
                start[event.x] = event.y
                Log.d(TAG, "mode=DRAG")
                mode = DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrix)
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                Log.d(TAG, "mode=NONE")
            }
            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                // ...
                matrix.set(savedMatrix)
                matrix.postTranslate(event.x - start.x, event.y
                        - start.y)
            } else if (mode == ZOOM && event.pointerCount == 2) {
                val newDist = spacing(event)
                matrix.set(savedMatrix)
                if (newDist > 10f) {
                    scale = newDist / oldDist
                    matrix.postScale(scale, scale, mid.x, mid.y)
                }
                if (lastEvent != null) {
                    newRot = rotation(event)
                    val r = newRot - d
                    matrix.postRotate(r, view.measuredWidth / 2.toFloat(),
                            view.measuredHeight / 2.toFloat())
                }
            }
        }
        // Perform the transformation
        view.imageMatrix = matrix
        return true // indicate event was handled
    }

    private fun rotation(event: MotionEvent): Float {
        val delta_x = (event.getX(0) - event.getX(1)).toDouble()
        val delta_y = (event.getY(0) - event.getY(1)).toDouble()
        val radians = Math.atan2(delta_y, delta_x)
        return Math.toDegrees(radians).toFloat()
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt(x * x + y * y.toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }
    /** Image drag, scale and rotate functionality  */
    /**
     * Show an event in the LogCat view, for debugging
     */
    /*private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");

        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())

                sb.append(";");
        }

        sb.append("]");
        Log.d(TAG, sb.toString());

    }*/
    //    private void showPictureDialog(){
    //        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
    //        pictureDialog.setTitle("SAVE");
    //
    //        ArrayList<String> temp = new ArrayList<>();
    //
    //        /*String[] pictureDialogItems = {
    //                "Save to Device"
    //                 }; */
    //
    //        temp.add("Save to Device");
    //
    //        PackageManager pm = this.getPackageManager();
    //        if(isPackageInstalled("com.instagram.android", pm))
    //        {
    //            temp.add("Instagram");
    //        }
    //
    //        String pictureDialogItems[]=temp.toArray(new String[temp.size()]);
    //
    //
    //        pictureDialog.setItems(pictureDialogItems,
    //                new DialogInterface.OnClickListener() {
    //                    @Override
    //                    public void onClick(DialogInterface dialog, int which) {
    //                        Intent i;
    //                        switch (which) {
    //
    //                            case 0:
    //                                /*i = new Intent(getBaseContext(), DisplayStoryPageActivity.class);
    //                                i.putExtra("template_id", id);
    //                                i.putExtra("mode", "device");
    //                                for(int x = 1 ; x <= maxid ; x++)
    //                                {
    //                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //
    //                                    Matrix m = img[x-1].getImageMatrix();
    //
    //                                    //BitmapDrawable drawable = (BitmapDrawable) img[x-1].getDrawable();
    //                                    //drawable.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
    //
    //                                    //((BitmapDrawable)img[x-1].getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
    //                                    byte[] byteArray = stream.toByteArray();
    //                                    getIntent().putExtra("image" + i, byteArray);
    //                                }
    //                                startActivity(i);*/
    //                                //saveImage();
    //                                //choosePhotoFromGallary();
    //                                break;
    //                            case 1:
    //                                /*i = new Intent(getBaseContext(), DisplayStoryPageActivity.class);
    //                                i.putExtra("template_id", id);
    //                                i.putExtra("mode", "instagram");
    //                                for(int x = 1 ; x <= maxid ; x++)
    //                                {
    //
    //                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //                                    ((BitmapDrawable)img[x-1].getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
    //                                    byte[] byteArray = stream.toByteArray();
    //                                    getIntent().putExtra("image" + i, byteArray);
    //                                }
    //                                startActivity(i);*/
    //                                //onShare();
    //                                //takePhotoFromCamera();
    //                                break;
    //                        }
    //                    }
    //                });
    //        pictureDialog.show();
    //    }
    companion object {
        private lateinit var REQUEST_IMAGE_ID: IntArray
        var fileNAME: String? = null
        var framePos = 0

        // We can be in one of these 3 states
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
    }
}