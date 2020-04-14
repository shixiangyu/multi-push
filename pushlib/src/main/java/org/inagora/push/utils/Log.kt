package org.inagora.push.utils

import android.util.Log

/**
 * Created by shixiangyu on 2019-11-26.
 */
object Log {

    const val SUPER_TAG = "push model"

    fun d(tag:String?,log: String?) {
        Log.d(SUPER_TAG, "$tag-$log")
    }
}