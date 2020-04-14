package org.inagora.push.manager

import android.content.Context
import org.inagora.push.agent.*
import org.inagora.push.utils.DeviceUtils

/**
 * Push统一管理
 */
class WDPushManager private constructor() {
    companion object {

        private var mInstance: WDPushManager? = null

        val instance: WDPushManager
            get() {
                if (mInstance == null) {
                    mInstance = WDPushManager()
                }
                return mInstance!!
            }

        fun initialize(context: Context, messageHandler: IPushHandler) {

            when {
                DeviceUtils.isMiui -> {
                    MiPushAgent.getInstance().register(context, messageHandler)
                }

                DeviceUtils.isSupportHWPush -> {
                    HWPushAgent.getInstance().register(context, messageHandler)
                }

                VivoPushAgent.getInstance().isSupport(context) -> {
                    VivoPushAgent.getInstance().register(context, messageHandler)
                }

                OppoPushAgent.isSupport(context) -> OppoPushAgent.register(context, messageHandler)
            }
            UmPushAgent.register(context, messageHandler)
        }

    }
}
