package com.example.a20mis0158// com.example.a20mis0158.SplashActivity.kt
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Simulate loading
        Thread.sleep(2000)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
