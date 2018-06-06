package me.leoyuu.chat.apk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splash.*
import me.leoyuu.chat.apk.module.data.net.ClientManager
import me.leoyuu.chat.apk.module.login.LoginHelper
import me.leoyuu.chat.apk.module.main.MainActivity
import me.leoyuu.chat.base.callback.Callback

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initEvent()
    }

    private fun initEvent() {
        login_btn.setOnClickListener {
            val host = ip_et.text.toString()
            val port = port_et.text.toString().toInt()
            val id = id_et.text.toString().toInt()
            val name = name_et.text.toString()
            ClientManager.initConfig(host, port, id, name)
            login_btn.isEnabled = false
            ClientManager.connect(object : Callback {
                override fun onFailed(msg: String) {
                    login_btn.isEnabled = true
                    Toast.makeText(this@SplashActivity, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess() {
                    LoginHelper.init(id, name)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            })
        }
    }
}
