package org.inagora.push.agent

import android.content.Context
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.*
import com.umeng.message.entity.UMessage
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log
import org.inagora.push.utils.ManifestUtils
import org.inagora.push.utils.TextUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by shixiangyu on 2019-11-05.
 */
object UmPushAgent {
    private const val UMENG_APP_ID_META_NAME = "UMENG_APPKEY"
    private const val UMENG_APP_SECRET_META_NAME = "UMENG_MESSAGE_SECRET"

    fun onAppStart(context: Context) {
        PushAgent.getInstance(context).onAppStart()
    }

    fun register(context: Context, messageHandler: IPushHandler) {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息

        val appKey = ManifestUtils.getMetaValue(context, UMENG_APP_ID_META_NAME)
        val appSecret = ManifestUtils.getMetaValue(context, UMENG_APP_SECRET_META_NAME)

        if (TextUtils.isEmpty(appKey) || TextUtils.isEmpty(appSecret)) {
            Log.d("UmPushManager", "未查询到UMENG_APPKEY或UMENG_MESSAGE_SECRET的相关配置")
            return
        }

        UMConfigure.init(context, appKey, "public", UMConfigure.DEVICE_TYPE_PHONE, appSecret)

        val mPushAgent = PushAgent.getInstance(context)
        PushAgent.DEBUG = false
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {

            override fun onSuccess(deviceToken: String) {
                //注册成功会返回device token
                Log.d("UmPushManager", "umeng deviceToken : $deviceToken")
                messageHandler.onReceiveToken(context, DeviceUtils.DEVICE_TYPE_UMENG, deviceToken)
            }

            override fun onFailure(s: String, s1: String) {
                Log.d("UmPushManager", "umeng push onFail s=$s  s1=$s1")

            }
        })
        val notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun dealWithCustomAction(context: Context, msg: UMessage) {
                try {
                    val json = JSONObject(msg.custom)
                    val action = json.optString("action")
                    messageHandler.onNotificationMessageClicked(context, action)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
        mPushAgent.messageHandler = object : UmengMessageHandler() {
            override fun dealWithNotificationMessage(context: Context, uMessage: UMessage) {
                super.dealWithNotificationMessage(context, uMessage)
                // EventBus.getDefault().post(new Event(Event.PUSH_MESSAGE_NUM_UPDATE));

            }
        }
        mPushAgent.displayNotificationNumber = 2
        mPushAgent.notificationClickHandler = notificationClickHandler
        mPushAgent.notificationPlayVibrate = MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE
    }
}
