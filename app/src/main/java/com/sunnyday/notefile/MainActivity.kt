package com.sunnyday.notefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("cacheDir：$cacheDir")
        println("externalCacheDir:$externalCacheDir")
    }
}