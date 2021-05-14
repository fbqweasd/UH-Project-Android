package com.example.tcp_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_management.*
import java.io.InputStream
import java.io.OutputStream
import java.net.*

class management : Fragment(R.layout.fragment_management) {

<<<<<<< HEAD
    var sock =  DatagramSocket(null)
    val COM_MAC = "00D861C36D40"
    val IP = "192.168.150.18"
=======
    val ServerIP  = "ukc.iptime.org"
    val ServerPort = 5657
>>>>>>> 61579302b6f1e7b9f1e31339a936e240261c1da9

    val IP = "192.168.150.8"
    val Port = 5656

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
            try {
                sock = Socket(ServerIP, ServerPort)
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러",  Toast.LENGTH_SHORT).show()
                }
                return;
            }

            InPutStream = sock.getInputStream()
            OutPutStream = sock.getOutputStream()

            var SendData : ByteArray = ByteArray(2)
            var RecvData : ByteArray = ByteArray(4)

            SendData.set(0, 4) // Type Set
            SendData.set(1, 0x6) // Len
//            System.arraycopy(COM_MAC, 0, SendData, 2, COM_MAC.length)
//            SendData += COM_MAC.toInt().toByte()

            try {
                OutPutStream.write(SendData)
                OutPutStream.flush()

                var RecvSize = InPutStream.read(RecvData)
                if(RecvSize == 0 || RecvData[0] != 4.toByte()){
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "서버 처리 에러",  Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "WOL 서버 정상 처리",  Toast.LENGTH_SHORT).show()
                    }
                }

                sock.close()
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "데이터 전송 에러",  Toast.LENGTH_SHORT).show()
                }
                sock.close()
                return;
            }
        }
    }

    inner class SendOff : Thread(){
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

            try {
                var data = "exit"
                var SendData : ByteArray = ByteArray(1)

                SendData.set(0, 4)
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
}