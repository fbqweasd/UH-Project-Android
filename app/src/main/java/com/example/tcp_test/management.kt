package com.example.tcp_test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_management.*
import java.io.IOError
import java.net.*

class management : Fragment(R.layout.fragment_management) {

    var sock =  DatagramSocket(null)
    val COM_MAC = "00D861C36D40"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        WOL_Button.setOnClickListener {
            var SendThread = SendWOL()
            SendThread.start()
        }
    }

    inner class SendWOL : Thread() {
        override fun run() {
            
            try {
                sock.bind(InetSocketAddress("192.168.150.8",7))
            }catch (e:BindException){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "UDP 브로드캐스트 에러",  Toast.LENGTH_SHORT).show()
                }
            }

            var SendData : ByteArray = "FFFFFFFFFFFF".toByteArray()

            for(i in 1..16){
                SendData += COM_MAC.toByteArray()
            }

            var sendPacket = DatagramPacket(SendData, SendData.size)
            print(SendData.size)

            try {
                sock.send(sendPacket)
            } catch(e:Exception) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "UDP 전송 에러",  Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}