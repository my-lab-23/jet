package com.example.vuea

import com.getcapacitor.BridgeActivity

class MainActivity : BridgeActivity() {
    init {
        registerPlugin(HelloWorldPlugin::class.java)
    }
} //
