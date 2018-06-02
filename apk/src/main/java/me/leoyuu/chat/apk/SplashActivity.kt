package me.leoyuu.chat.apk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import me.leoyuu.proto.BasePackets

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Toast.makeText(this, "Hello World~", Toast.LENGTH_LONG).show()
    }
}
