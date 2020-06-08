package com.example.myapplication.helpers

import android.content.pm.PackageManager

object AppPackage {
    /**
     * Checks if application is installed on device
     */
    fun isPackageInstalled(packageName: String?, packageManager: PackageManager): Boolean {
        var found = true
        try {
            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            found = false
        }
        return found
    }
}