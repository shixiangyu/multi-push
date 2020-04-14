package org.inagora.push.agent

import android.content.Context
import com.vivo.push.PushClient
import org.inagora.push.BuildConfig
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log
import org.inagora.push.utils.TextUtils

/**
 * Created by shixiangyu on 2019-10-28.
 */
class VivoPushAgent : IPushAgent {

    private lateinit var mMessageHandler: IPushHandler

    override fun isSupport(context: Context): Boolean {
        return PushClient.getInstance(context).isSupport
    }

    override fun getMessageHandler(): IPushHandler? {
        return this.mMessageHandler
    }

    override fun setMessageHandler(handler: IPushHandler) {
    }

    override fun register(context: Context,messageHandler: IPushHandler) {
        this.mMessageHandler = messageHandler
        PushClient.getInstance(context.applicationContext).initialize()
        PushClient.getInstance(context.applicationContext).turnOnPush {
            val regId = PushClient.getInstance(context.applicationContext).regId
            if (BuildConfig.DEBUG) {
                Log.d("VivoPushManager", "regId:$regId")
            }

            if (!TextUtils.isEmpty(regId)) {
                this.mMessageHandler.onReceiveToken(context, DeviceUtils.DEVICE_TYPE_VIVO, regId)
            }
        }
    }

    companion object {
        private var singleton: IPushAgent? = null
        @Synchronized
        fun getInstance(): IPushAgent {
            if (singleton == null) {
                singleton = VivoPushAgent()
            }
            return singleton!!
        }

    }
}
