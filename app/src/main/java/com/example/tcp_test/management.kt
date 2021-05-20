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

    val ServerIP  = "ukc.iptime.org"
    val ServerPort = 5657

    val COM_MAC : String = "00D861C36D40"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        WOL_Button.setOnClickListener {
            var SendThread = SendWOL()
            SendThread.start()
        }

        PowerOff_Button.setOnClickListener {
            var ShutDownThread = ShutDownPacket()
            ShutDownThread.start()
        }
    }

    inner class SendWOL : Thread() {
        lateinit var InPutStream: InputStream
        lateinit var OutPutStream: OutputStream
        lateinit var sock:Socket

        override fun run() {
            try {
                this.sock = Socket(ServerIP, ServerPort)
            }catch (e:Exception){
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러",  Toast.LENGTH_SHORT).show()
                }
                return;
            }

            this.InPutStream = this.sock.getInputStream()
            this.OutPutStream = this.sock.getOutputStream()

            var SendData : ByteArray = ByteArray(8)
            var RecvData : ByteArray = ByteArray(4)

            SendData.set(0, 4) // Type Set
            SendData.set(1, 0x6) // Len

            SendData.set(2, Integer.parseInt(COM_MAC.substring(0,2), 16 ).toByte())
            SendData.set(3, Integer.parseInt(COM_MAC.substring(2,4), 16 ).toByte())
            SendData.set(4, Integer.parseInt(COM_MAC.substring(4,6), 16 ).toByte())
            SendData.set(5, Integer.parseInt(COM_MAC.substring(6,8), 16 ).toByte())
            SendData.set(6, Integer.parseInt(COM_MAC.substring(8,10), 16 ).toByte())
            SendData.set(7, Integer.parseInt(COM_MAC.substring(10,12), 16 ).toByte())

//            COM_MAC.substring(0, 1)

//            SendData.set(2, COM_MAC[0].toInt().toByte())
//            SendData.set(3, COM_MAC[1].toInt().toByte())
//            SendData.set(4, COM_MAC[2].toInt().toByte())
//            SendData.set(5, COM_MAC[3].toInt().toByte())
//            SendData.set(6, COM_MAC[4].toInt().toByte())
//            SendData.set(7, COM_MAC[5].toInt().toByte())

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
                        Toast.makeText(activity, "컴퓨터 부팅 성공",  Toast.LENGTH_SHORT).show()
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

    inner class ShutDownPacket : Thread() {
        lateinit var InPutStream: InputStream
        lateinit var OutPutStream: OutputStream
        lateinit var sock:Socket

        var SendData : ByteArray = ByteArray(8)
        var RecvData : ByteArray = ByteArray(4)

        override fun run() {
            try {
                this.sock = Socket(ServerIP, ServerPort)
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "서버 연결 에러", Toast.LENGTH_SHORT).show()
                }
                return;
            }

            this.InPutStream = this.sock.getInputStream()
            this.OutPutStream = this.sock.getOutputStream()

            SendData.set(0, 5) // Type Set

            try {
                OutPutStream.write(SendData)
                OutPutStream.flush()

                var RecvSize = InPutStream.read(RecvData)
                if(RecvSize == 0 || RecvData[0] != 5.toByte()){
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "서버 처리 에러",  Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "컴퓨터 종료",  Toast.LENGTH_SHORT).show()
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
}