package org.inagora.push.receiver

import android.content.Context
import android.os.Bundle
import com.huawei.hms.support.api.push.PushReceiver
import org.inagora.push.BuildConfig
import org.inagora.push.agent.HWPushAgent
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log

/*
 * 接收HuaWeiPush所有消息的广播接收器
 */
class HWPushReceiver : PushReceiver() {

    override fun onToken(context: Context, token: String, extras: Bundle) {
        val belongId = extras.getString("belongId")
        if (BuildConfig.DEBUG) {
            Log.d("HuaweiPushRevicer", "Token为:$token")
        }
        HWPushAgent.getInstance().getMessageHandler()?.onReceiveToken(context,DeviceUtils.DEVICE_TYPE_HUAWEI,token)
    }

    override fun onPushMsg(context: Context, msg: ByteArray, bundle: Bundle): Boolean {
        //透传消息，开发者可以自己解析消息内容，然后做相应的处理
        return false
    }

    override fun onEvent(context: Context?, event: PushReceiver.Event?, extras: Bundle?) {
        //点击回调，官方建议避免在此处处理，应交由系统做统一处理，故注释
        //        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
        //            String json = extras.getString(BOUND_KEY.pushMsgKey);
        //            try {
        //                JSONArray array =  new JSONArray(json);
        //                String action = array.optString(0);
        //                ActionUtil.startAction(context, ActionUtil.PREFIX + action, true);
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //
        //            super.onEvent(context, event, extras);
        //        }
    }

    override fun onPushState(context: Context?, b: Boolean) {
        Log.d("HuaweiPushRevicer", "Push连接状态为:$b")
    }

}
