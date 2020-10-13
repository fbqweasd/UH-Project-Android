package com.example.tcp_test

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_youtube.*
import java.io.InputStream
import java.io.OutputStream
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

class youtube : Fragment(R.layout.fragment_youtube) {

    val IP  = "192.168.150.8"
    val Port = 5656
    lateinit var InPutStream: InputStream
    lateinit var OutPutStream: OutputStream
    lateinit var sock:Socket
    var text : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button.setOnClickListener {
            var connet = Connet_Server()
            connet.start()
            connet.join()

            var send = SendYoutubeData()
            send.start()
        }

        if(text != null) {
            input_Text.setText(text.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        context as MainActivity
        text = context.GetYoutubeLink()

        println(text)
    }

    inner class SendYoutubeData : Thread() {
        override fun run() {
            try {
                var data = input_Text.text.toString()
                var SendData : ByteArray = ByteArray(1)

                SendData.set(0, 1)
                OutPutStream.write(SendData + data.toByteArray())
                OutPutStream.flush()
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러",  Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    inner class Connet_Server : Thread() {
        override fun run() {
            try {
                sock = Socket(IP, Port)

                InPutStream = sock.getInputStream()
                OutPutStream = sock.getOutputStream()
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러",  Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}