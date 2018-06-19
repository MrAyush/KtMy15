package com.example.ayushgupta.ktmy15

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    private var switch: Switch? = null
    private var lview: ListView? = null
    private var btAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch = findViewById(R.id.s1)
        lview = findViewById(R.id.lview)
        switch?.isChecked = btAdapter!!.isEnabled
        switch?.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked)
                btAdapter?.enable()
            else
                btAdapter?.disable()
        })
    }

    fun getBTDevices(v: View) {
        val list = mutableListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        lview?.adapter = adapter
        btAdapter?.startDiscovery()
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val device = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                list.add("${device?.name} \n${device?.address}")
                adapter.notifyDataSetChanged()
            }
        },filter)
    }
}
