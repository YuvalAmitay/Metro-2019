package com.example.textapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.BroadcastReceiver
import android.net.wifi.p2p.WifiP2pManager
import android.content.Context
import android.content.IntentFilter
import android.util.Log


class MainActivity : AppCompatActivity() {

    val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }
    var mChannel: WifiP2pManager.Channel? = null
    var receiver: BroadcastReceiver? = null

    val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Steven", "fuckboi")

        super.onCreate(savedInstanceState)

        mChannel = manager?.initialize(this, mainLooper, null)
        mChannel?.also { channel ->
            receiver = WiFiDirectBroadcastReceiver(manager, channel, this)
        }


        setContentView(R.layout.activity_main)

        manager?.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
                Log.d("Discover", "Success")
            }

            override fun onFailure(reasonCode: Int) {
                Log.d("Discover", reasonCode.toString())
            }
        })
    }



}
