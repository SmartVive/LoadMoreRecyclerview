package com.example.administrator.loadmorerecyclerview

import android.app.Application
import android.content.Context

class MyApplication:Application(){
    companion object {
        private var myApplication: MyApplication? = null
        fun getContext(): Application {
            return myApplication!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }
}