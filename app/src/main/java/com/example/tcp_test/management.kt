package com.example.tcp_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_management.*
import java.net.*

class management : Fragment() {

    var sock =  DatagramSocket(null)
    val COM_MAC = "00D861C36D40"
    val IP = "192.168.150.18"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WOL_Button.setOnClickListener {

        }

    }

    inner class SendWOL : Thread() {
        override fun run() {
            sock.bind(InetSocketAddress("192.168.150.255",7))

            var SendData : ByteArray = "FFFFFFFFFFFF".toByteArray()

            for(i in 1..16){
                SendData += COM_MAC.toByteArray()
            }

            var sendPacket = DatagramPacket(SendData, SendData.size)
            print(SendData.size)

            sock.send(sendPacket)
        }
    }
}