package org.inagora.push.agent

import android.content.Context
import com.heytap.mcssdk.PushManager
import com.heytap.mcssdk.callback.PushAdapter
import org.inagora.push.BuildConfig
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log
import org.inagora.push.utils.ManifestUtils
import org.inagora.push.utils.TextUtils

/**
 * Created by shixiangyu on 2019-10-28.
 */
object OppoPushAgent : IPushAgent {

    private lateinit var mMessageHandler: IPushHandler
    private const val OPPO_APP_KEY_META_NAME = "com.heytap.oppopush.app_key"
    private const val OPPO_APP_SECRET_META_NAME = "com.heytap.oppopush.app_secret"

    override fun isSupport(context: Context): Boolean {
        return PushManager.isSupportPush(context)
    }

    override fun getMessageHandler(): IPushHandler? {
        return this.mMessageHandler
    }

    override fun setMessageHandler(handler: IPushHandler) {
        this.mMessageHandler = handler
    }

    override fun register(context: Context,messageHandler: IPushHandler) {

        this.mMessageHandler = messageHandler
        val appKey = ManifestUtils.getMetaValue(context, OPPO_APP_KEY_META_NAME)
        val appSecret = ManifestUtils.getMetaValue(context, OPPO_APP_SECRET_META_NAME)
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            PushManager.getInstance().register(context, appKey, appSecret, object : PushAdapter() {

                override fun onRegister(responseCode: Int, regId: String?) {
                    super.onRegister(responseCode, regId)
                    // responseCode 0 代表成功,其他代码失败
                    if (BuildConfig.DEBUG) {
                        Log.d("OppoPushAgent", "regId=$regId")
                    }
                    regId?.let {
                        this@OppoPushAgent.mMessageHandler.onReceiveToken(context,DeviceUtils.DEVICE_TYPE_OPPO,it) }
                }
            })
        }

    }
}
