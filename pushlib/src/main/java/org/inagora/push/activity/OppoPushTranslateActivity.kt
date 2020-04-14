package org.inagora.push.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.inagora.push.agent.OppoPushAgent

/**
 * Created by shixiangyu on 2017/11/1.
 */

class OppoPushTranslateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val action = intent.dataString as String
            action.replace("oppopush", "wonderfull")
            OppoPushAgent.getMessageHandler()?.onNotificationMessageClicked(this, action)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finish()
    }
}
