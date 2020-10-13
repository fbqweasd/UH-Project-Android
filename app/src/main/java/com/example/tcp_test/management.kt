package com.example.tcp_test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_management.*
import kotlinx.android.synthetic.main.fragment_youtube.*
import java.io.IOError
import java.io.InputStream
import java.io.OutputStream
import java.net.*

class management : Fragment(R.layout.fragment_management) {

    val IP  = "192.168.150.18"
    val Port = 5657
    lateinit var InPutStream: InputStream
    lateinit var OutPutStream: OutputStream
    lateinit var sock:Socket

    val COM_MAC = "00D861C36D40"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        WOL_Button.setOnClickListener {
            var SendThread = SendWOL()
            SendThread.start()
        }
    }

    inner class SendWOL : Thread() {
        override fun run() {
            
//            try {
//                sock.bind(InetSocketAddress("192.168.150.8",7))
//            }catch (e:BindException){
//                activity?.runOnUiThread {
//                    Toast.makeText(activity, "UDP 브로드캐스트 에러",  Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            var SendData : ByteArray = "FFFFFFFFFFFF".toByteArray()
//
//            for(i in 1..16){
//                SendData += COM_MAC.toByteArray()
//            }
//
//            var sendPacket = DatagramPacket(SendData, SendData.size)
//            print(SendData.size)
//
//            try {
//                sock.send(sendPacket)
//            } catch(e:Exception) {
//                activity?.runOnUiThread {
//                    Toast.makeText(activity, "UDP 전송 에러",  Toast.LENGTH_SHORT).show()
//                }
//            }


            try {
                sock = Socket(IP, Port)
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러",  Toast.LENGTH_SHORT).show()
                }
                return;
            }

            InPutStream = sock.getInputStream()
            OutPutStream = sock.getOutputStream()
            var data = "WOL"
            var SendData : ByteArray = ByteArray(1)

            SendData.set(0, 4)

            try {
                OutPutStream.write(SendData + data.toByteArray())
                OutPutStream.flush()
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "데이터 전송 에러",  Toast.LENGTH_SHORT).show()
                }
                return;
            }


        }
    }
}