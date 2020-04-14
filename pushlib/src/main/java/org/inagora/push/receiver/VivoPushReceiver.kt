package org.inagora.push.receiver

import android.content.Context
import com.vivo.push.model.UPSNotificationMessage
import com.vivo.push.sdk.OpenClientPushMessageReceiver
import org.inagora.push.BuildConfig
import org.inagora.push.agent.VivoPushAgent
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log

/**
 * Created by shixiangyu on 2019-10-28.
 */
class VivoPushReceiver : OpenClientPushMessageReceiver() {

    override fun onNotificationMessageClicked(context: Context, upsNotificationMessage: UPSNotificationMessage) {
        val action = upsNotificationMessage.skipContent
        VivoPushAgent.getInstance().getMessageHandler()?.onNotificationMessageClicked(context, action)
    }

    override fun onReceiveRegId(context: Context, regId: String) {
        if (BuildConfig.DEBUG) {
            Log.d("VivoPushReceiver", "regId=$regId")
        }
        VivoPushAgent.getInstance().getMessageHandler()?.onReceiveToken(context, DeviceUtils.DEVICE_TYPE_VIVO, regId)
    }
}
