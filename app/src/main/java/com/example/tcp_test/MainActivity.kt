package com.example.tcp_test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.LineHeightSpan
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.PagerAdapter
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.StandardCharsets

open class Setting_Data : RealmObject(){
    lateinit var server_ip:String
    var server_port:Int = 0
    lateinit var computer_mac:String
}

class MainActivity : AppCompatActivity() {

    var YouTubeLink : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = PagerManger(supportFragmentManager)
        tabbar.setupWithViewPager(pager)

        Realm.init(applicationContext)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
            println(it)
            YouTubeLink = it.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.Setting -> {
                var SettingActivite = Intent(this, SettingActivity::class.java)
                startActivity(SettingActivite)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun GetYoutubeLink(): String? {
        return this!!.YouTubeLink
    }
}