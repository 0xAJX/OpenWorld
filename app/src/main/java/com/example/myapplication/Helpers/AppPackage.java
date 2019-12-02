package com.example.myapplication.Helpers;

import android.content.pm.PackageManager;

public class AppPackage {

    /**
     * Checks if application is installed on device
     * */

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }
}
