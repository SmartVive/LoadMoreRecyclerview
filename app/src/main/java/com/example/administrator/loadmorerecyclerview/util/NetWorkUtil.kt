package com.example.administrator.loadmorerecyclerview.util

import android.content.Context
import android.net.ConnectivityManager

class NetWorkUtil private constructor(){
    companion object {
        /**
         * 判断网络是否可用
         *
         * @param context
         * @return
         */
        fun isNetWorkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        /**
         * 检测wifi是否连接
         *
         * @return
         */
        fun isWifiConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * 检测3G是否连接
         *
         * @return
         */
        fun is3gConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE
        }
    }
}