package me.leoyuu.apk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import me.leoyuu.proto.BasePackets

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Toast.makeText(this, BasePackets.Packet::class.java.name, Toast.LENGTH_SHORT).show()
    }
}
