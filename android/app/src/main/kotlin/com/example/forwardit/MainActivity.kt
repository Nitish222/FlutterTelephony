package com.example.forwardit

import android.content.Context
import android.os.Build
import android.os.Messenger
import android.provider.Telephony
import android.telephony.TelephonyManager
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val METHOD_CHANNEL_NAME = "com.example.forwardit/method"
    private val PERSSURE_CHANNEL_NAME = "com.example.forwardit/pressure"

    private lateinit var telephonyManager:TelephonyManager
    private var methodChannel:MethodChannel? = null

    //see notes on OneNote
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        //Setup the Channels
        setupChannels(context,flutterEngine.dartExecutor.binaryMessenger)
    }

    override fun onDestroy() {

        //to destroy the channels
        teadownChannels()
        super.onDestroy();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupChannels(context:Context, messenger: BinaryMessenger){
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //setup channels on call
        methodChannel = MethodChannel(messenger, METHOD_CHANNEL_NAME)

        //setup a call handler for it
        // double ! is null safety for kotlin
        methodChannel!!.setMethodCallHandler{
            call,result ->
            if (call.method == "isTelephonyAvailable") {
                    result.success(telephonyManager!!.getSimState(0))
            }
            else{
                result.notImplemented()
            }
        }
    }

    private fun teadownChannels(){
            methodChannel!!.setMethodCallHandler(null)
    }
}
