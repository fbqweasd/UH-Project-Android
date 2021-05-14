package com.example.tcp_test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_youtube.*
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class youtubeClient : Fragment(R.layout.fragment_youtube) {

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