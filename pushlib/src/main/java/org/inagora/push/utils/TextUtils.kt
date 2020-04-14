package org.inagora.push.utils

/**
 * Created by shixiangyu on 2019-11-05.
 */
object TextUtils {

    /**
     * 字符串是否为空
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }
}
