package org.inagora.push.agent

import android.content.Context

/**
 * Created by shixiangyu on 2020/4/13.
 */
interface IPushHandler {
    fun onNotificationMessageClicked(context: Context, action: String)
    fun onReceiveToken(context: Context, deviceType: String, token: String)
}