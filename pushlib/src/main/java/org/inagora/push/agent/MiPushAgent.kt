package org.inagora.push.agent

import android.content.Context
import com.xiaomi.mipush.sdk.MiPushClient
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.ManifestUtils
import org.inagora.push.utils.TextUtils

/**
 * Created by cuiqing on 2016/10/31.
 */
class MiPushAgent : IPushAgent {
    private lateinit var mMessageHandler: IPushHandler

    private val MI_APP_ID_META_NAME = "com.xiaomi.mipush.app_id"
    private val MI_APP_KEY_META_NAME = "com.xiaomi.mipush.app_key"


    override fun register(context: Context, messageHandler: IPushHandler) {
        val appId = ManifestUtils.getMetaValue(context, MI_APP_ID_META_NAME)
        val appKey = ManifestUtils.getMetaValue(context, MI_APP_KEY_META_NAME)
        this.mMessageHandler = messageHandler
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
            MiPushClient.registerPush(context.applicationContext, appId, appKey)
        }
    }

    override fun isSupport(context: Context): Boolean {
        return DeviceUtils.isMiui
    }

    override fun getMessageHandler(): IPushHandler? {
        if (::mMessageHandler.isInitialized) {
            return this.mMessageHandler
        }
        return null
    }

    override fun setMessageHandler(handler: IPushHandler) {
        this.mMessageHandler = handler
    }

    companion object {
        private var singleton: IPushAgent? = null
        @Synchronized
        fun getInstance(): IPushAgent {
            if (singleton == null) {
                singleton = MiPushAgent()
            }
            return singleton!!
        }


    }

}
