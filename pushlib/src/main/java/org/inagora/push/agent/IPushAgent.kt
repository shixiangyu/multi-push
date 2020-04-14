package org.inagora.push.agent

import android.content.Context

/**
 * Created by shixiangyu on 2020/4/13.
 */
interface IPushAgent {
    fun isSupport(context: Context):Boolean

    fun getMessageHandler(): IPushHandler?

    fun setMessageHandler(handler: IPushHandler)

    fun register(context: Context,messageHandler: IPushHandler)
}