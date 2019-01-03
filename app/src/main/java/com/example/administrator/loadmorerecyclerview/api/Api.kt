package com.example.administrator.loadmorerecyclerview.api

import com.example.administrator.loadmorerecyclerview.bean.Top250
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("top250?count=20")
    fun getTop250(@Query("start") start: Int): Observable<Top250>
}