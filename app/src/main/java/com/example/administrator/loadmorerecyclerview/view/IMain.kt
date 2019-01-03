package com.example.administrator.loadmorerecyclerview.view

import com.example.administrator.loadmorerecyclerview.bean.Top250

interface IMain {
    fun getTop250(list: List<Top250.Subjects>)
    fun loadMoreData(list: List<Top250.Subjects>)
    fun noMore()
    fun onError()
}