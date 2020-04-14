package org.inagora.push.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.inagora.push.agent.HWPushAgent

/**
 * Created by shixiangyu on 2017/11/1.
 */

class HWPushTranslateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val action = intent.data!!.getQueryParameter("action")
            HWPushAgent.getInstance().getMessageHandler()?.onNotificationMessageClicked(this,action)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        finish()
    }
}
