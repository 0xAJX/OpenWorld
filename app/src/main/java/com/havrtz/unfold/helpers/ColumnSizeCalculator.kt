package com.havrtz.unfold.helpers

import com.havrtz.unfold.util.Constants.sColumnWidth
import kotlin.math.floor

object ColumnSizeCalculator {
    /*
    * Helps to set the span count for column
    */
    fun calculateSize(width: Int) : Int {
        return floor(width / sColumnWidth).toInt()
    }
}