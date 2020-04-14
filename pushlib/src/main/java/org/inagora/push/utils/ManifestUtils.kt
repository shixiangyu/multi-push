package org.inagora.push.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by shixiangyu on 2019-11-25.
 */
object ManifestUtils {

    fun getMetaValue(context: Context, metaName: String): String? {
        val metaData = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData
        val metaValue = metaData.getString(metaName)
        if (TextUtils.isEmpty(metaValue)) {
            Log.d(this.javaClass.simpleName, "AndroidManifest查询不到当前$metaName,请检查是否配置")
        }
        return metaValue
    }
}