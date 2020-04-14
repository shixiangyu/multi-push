package org.inagora.push.receiver

import android.content.Context
import com.xiaomi.mipush.sdk.*
import org.inagora.push.BuildConfig
import org.inagora.push.agent.MiPushAgent
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log
import org.json.JSONException
import org.json.JSONObject

class MiMessageReceiver : PushMessageReceiver() {
    private var mRegId: String? = null

    override fun onReceivePassThroughMessage(context: Context?, message: MiPushMessage?) {}

    override fun onNotificationMessageClicked(context: Context, message: MiPushMessage) {
        val json = message.content
        try {
            val jsonObject = JSONObject(json)
            val action = jsonObject.optString("action")
            MiPushAgent.getInstance().getMessageHandler()?.onNotificationMessageClicked(context,action)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun onNotificationMessageArrived(context: Context?, message: MiPushMessage?) {
    }

    override fun onCommandResult(context: Context?, message: MiPushCommandMessage?) {}

    override fun onReceiveRegisterResult(context: Context, message: MiPushCommandMessage) {
        val command = message.command
        val arguments = message.commandArguments
        val cmdArg1 = if (arguments != null && arguments.size > 0) arguments[0] else null
        if (MiPushClient.COMMAND_REGISTER == command) {
            if (message.resultCode == ErrorCode.SUCCESS.toLong()) {
                mRegId = cmdArg1
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d("MiMessageReceiver", "xiaomi push on Register regId=$mRegId")
        }

        mRegId?.let {
            MiPushAgent.getInstance().getMessageHandler()?.onReceiveToken(context,DeviceUtils.DEVICE_TYPE_XIAOMI,it)
        }
    }
}