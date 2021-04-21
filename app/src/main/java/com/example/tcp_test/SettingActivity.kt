package com.example.tcp_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        Realm.init(applicationContext)
    }
}