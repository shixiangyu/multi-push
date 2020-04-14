package org.inagora.push.utils


/**
 * Created by shixiangyu on 2019-11-05.
 */
object DeviceUtils {

    const val DEVICE_TYPE_UMENG = "umeng"
    const val DEVICE_TYPE_XIAOMI = "xiaomi"
    const val DEVICE_TYPE_HUAWEI = "huawei"
    const val DEVICE_TYPE_VIVO = "vivo"
    const val DEVICE_TYPE_OPPO = "oppo"

    private val MIUI_PROP = "ro.miui.ui.version.name"
    private val EMUI_PROP = "ro.build.hw_emui_api_level"
    private val EMUI_VERSION_CODE = "ro.miui.version.code_time"

    /**
     * 判断你是否是MIUI Rom
     * [参考链接 ](http://dev.xiaomi.com/doc/?p=254)
     */
    val isMiui: Boolean
        get() {
            val property = getSystemProperty(MIUI_PROP)
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断你是否是华为EMUI Rom
     */
    val isEMUIRom: Boolean
        get() {
            val property = getSystemProperty(EMUI_PROP)
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断是否是华为emui 4.0以上
     */
    val isSupportHWPush: Boolean
        get() {
            var emuiLevel = 0
            try {
                val property = getSystemProperty(EMUI_PROP)
                if (!TextUtils.isEmpty(property)) {
                    emuiLevel = Integer.parseInt(property!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return emuiLevel >= 9
        }

    private fun getSystemProperty(propName: String): String? {
        var property: String? = null
        try {
            val cls = Class.forName("android.os.SystemProperties")
            val method = cls.getDeclaredMethod("get", *arrayOf<Class<*>>(String::class.java))
            property = method.invoke(cls, *arrayOf<Any>(propName)) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return property
    }
}
