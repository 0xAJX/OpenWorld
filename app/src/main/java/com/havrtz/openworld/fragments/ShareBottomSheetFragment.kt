package com.havrtz.openworld.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.havrtz.openworld.R
import com.havrtz.openworld.helpers.AppPackage
import com.havrtz.openworld.models.Story
import com.havrtz.openworld.models.StoryElement
import com.havrtz.openworld.util.Constants.googlePlayUrl
import com.havrtz.openworld.viewmodels.StoryViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

class ShareBottomSheetFragment : BottomSheetDialogFragment, EasyPermissions.PermissionCallbacks {
    var bitmap: Bitmap? = null
    var bitmapLocation: String? = null
    var bundle: Bundle? = null
    var appPackageName: String? = null
    var imageItems: List<StoryElement>? = null
    lateinit var imageLocation: Array<String>

    constructor(bitmapLocation: String?) {
        this.bitmapLocation = bitmapLocation
    }

    constructor(bundle: Bundle, bitmap: Bitmap?, imageItems: List<StoryElement>?) {
        this.bundle = bundle
        this.bitmap = bitmap
        this.imageItems = imageItems
        imageLocation = bundle.getStringArray("imageLocation") as Array<String>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.share_bottom_sheet_fragment, null, false)
        val navigationView: BottomNavigationView = view.findViewById(R.id.nav_view)
        if (bitmapLocation != null) {
            navigationView.menu.findItem(R.id.device_share).isVisible = false
        }
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        return view
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.device_share -> {
                saveImage()
                dismiss()
            }
            R.id.facebook_share -> {
                appPackageName = "com.facebook.katana"
                if (AppPackage.isPackageInstalled(appPackageName, requireActivity().packageManager)) {
                    onShare(appPackageName)
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayUrl + appPackageName)))
                }
                dismiss()
            }
            R.id.instagram_share -> {
                appPackageName = "com.instagram.android"
                if (AppPackage.isPackageInstalled(appPackageName, requireActivity().packageManager)) {
                    onShare(appPackageName)
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayUrl + appPackageName)))
                }
                dismiss()
            }
        }
        true
    }


    fun onShare(packageName: String?) {

        askPermission()

        val uri: Uri
        uri = if (bitmapLocation == null) {
            val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver,
                    bitmap, "Design", null)
            Uri.parse(path)
        } else {
            Uri.parse(bitmapLocation)
        }
        val share = Intent(Intent.ACTION_SEND)
        //Intent share = new Intent("com.instagram.share.ADD_TO_STORY");
        share.type = "image/*"
        share.setPackage(packageName)
        share.putExtra(Intent.EXTRA_STREAM, uri)
        share.putExtra(Intent.EXTRA_TEXT, "I created something cool!")
        startActivity(Intent.createChooser(share, "Share Your Story!"))
    }

    @AfterPermissionGranted(123)
    private fun askPermission() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {

        } else {

            EasyPermissions.requestPermissions(this, "We need permissions to store image",
                    123, *perms)
        }
    }

    /// @param folderName can be your app's name
    private fun saveImage() {

        askPermission()

        try {
            val folderName: String = "OpenWorld"
            val bytes = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

            val image = BitmapFactory.decodeByteArray(bytes.toByteArray(), 0, bytes.toByteArray().size)

            if (android.os.Build.VERSION.SDK_INT >= 29) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
                values.put(MediaStore.Images.Media.IS_PENDING, true)
                // RELATIVE_PATH and IS_PENDING are introduced in API 29.

                val uri: Uri? = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                if (uri != null) {
                    saveImageToStream(image, requireContext().contentResolver.openOutputStream(uri))
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    requireContext().contentResolver.update(uri, values, null, null)
                    addToDB(getRealPathFromURI(uri))

                    val snackbar = Snackbar.make(requireActivity().findViewById(R.id.templateloader), "Image Saved", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            } else {
                val directory = File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
                // getExternalStorageDirectory is deprecated in API 29

                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val fileName = System.currentTimeMillis().toString() + ".png"
                val file = File(directory, fileName)
                saveImageToStream(image, FileOutputStream(file))
                if (file.absolutePath != null) {
                    val values = contentValues()
                    values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                    // .DATA is deprecated in API 29
                    requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                    addToDB(file.absolutePath)
                    val snackbar = Snackbar.make(requireActivity().findViewById(R.id.templateloader), "Image Saved", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
        } catch (e: java.lang.Exception) {
            val snackbar = Snackbar.make(requireActivity().findViewById(R.id.templateloader), "Error Saving Image", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = requireContext().getContentResolver().query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    fun saveImage() {
//
//        askPermission()
//
//        val bytes = ByteArrayOutputStream()
//        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//        val wallpaperDirectory = File(Environment.DIRECTORY_PICTURES + File.separator + "OpenWorld")
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs()
//        }
//
//        Log.d("file_absolute_path_1", Environment.DIRECTORY_PICTURES + File.separator + "OpenWorld")
////        Log.d("file_absolute_path_3", MediaStore.)
//
//        try {
//            val f = File(wallpaperDirectory, Calendar.getInstance()
//                    .timeInMillis.toString() + ".jpg")
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//            MediaScannerConnection.scanFile(context, arrayOf(f.path), arrayOf("image/jpeg"), null)
//            fo.close()
//            //Log.d("TAG", "File Saved::--->" + f.absolutePath)
////            if (bundle!!.getBoolean("isUpdate")) {
////                //UpdateDB(f.getAbsolutePath());
////            } else {
//                Log.d("file_absolute_path_2", f.absolutePath)
//                addToDB(f.absolutePath)
//            //}
//            val snackbar = Snackbar.make(requireActivity().findViewById(R.id.templateloader), "Image Saved", Snackbar.LENGTH_SHORT)
//            //            .setAction("UNDO", new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    // Respond to the click, such as by undoing the modification that caused
////                    // this message to be displayed
////                    //Log.d("here", "here");
////                }
////            });
//            snackbar.show()
//
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        }
//    }

    /*public void UpdateDB(String filepath) {
        databaseHandler.updateUserTemplate(bundle.getString("user_template_id"), bundle.getString("title"), filepath);

        for (int i = 0; i < imageItems.size(); i++) {
            imageItems.get(i).getImage_location(imageLocation[i]);
        }
        databaseHandler.updateImages(bundle.getString("user_template_id"), imageItems);
    }*/
    fun addToDB(filepath: String?) {
        var text = bundle!!.getString("title")
        if (bundle!!.getString("title")?.isEmpty()!! || text?.trim { it <= ' ' }?.length == 0) {
            text = "My Story"
        }
        var storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)
        storyViewModel.insert(text?.let { Story(bundle!!.getInt("template_id"), 0, it, filepath!!) })
        Log.d("storyview", storyViewModel.allStories.toString())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}