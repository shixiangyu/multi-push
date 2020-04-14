package org.inagora.push.agent

import android.content.Context
import com.huawei.hms.api.HuaweiApiClient
import com.huawei.hms.support.api.push.HuaweiPush
import org.inagora.push.BuildConfig
import org.inagora.push.utils.DeviceUtils
import org.inagora.push.utils.Log

/**
 * 华为push管理类
 */
class HWPushAgent private constructor() : IPushAgent {

    private lateinit var mMessageHandler: IPushHandler
    private var client: HuaweiApiClient? = null

    private var mInstance: HWPushAgent? = null

    override fun register(context: Context,messageHandler: IPushHandler) {
        this.mMessageHandler = messageHandler
        mInstance = run { mInstance ?: HWPushAgent() }
        mInstance?.let {
            it.client = HuaweiApiClient.Builder(context)
                    .addApi(HuaweiPush.PUSH_API)
                    .addConnectionCallbacks(object : HuaweiApiClient.ConnectionCallbacks {
                        override fun onConnected() {
                            getTokenAsyn(it.client)
                            if (BuildConfig.DEBUG) {
                                Log.d("HWPushAgent", "hw push onConnected ")
                            }
                        }

                        override fun onConnectionSuspended(i: Int) {}
                    })
                    .addOnConnectionFailedListener { connectionResult ->
                        if (BuildConfig.DEBUG) {
                            Log.d("HWPushAgent", "hw push onConnectionFailed " + connectionResult.errorCode)
                        }
                    }
                    .build()

            //建议在oncreate的时候连接华为移动服务
            //业务可以根据自己业务的形态来确定client的连接和断开的时机，但是确保connect和disconnect必须成对出现
            it.client?.apply { connect() }
        }
    }

    private fun getTokenAsyn(client: HuaweiApiClient?) {
        val tokenResult = HuaweiPush.HuaweiPushApi.getToken(client)
        tokenResult.setResultCallback { mInstance?.client?.disconnect() }
    }

    override fun isSupport(context: Context): Boolean {
        return  DeviceUtils.isSupportHWPush
    }


    override fun getMessageHandler(): IPushHandler? {
        return this.mMessageHandler
    }

    override fun setMessageHandler(handler: IPushHandler) {
        this.mMessageHandler = handler
    }

    companion object {
        private var singleton: IPushAgent? = null
        @Synchronized
        fun getInstance(): IPushAgent {
            if (singleton == null) {
                singleton = HWPushAgent()
            }
            return singleton!!
        }


    }

}
