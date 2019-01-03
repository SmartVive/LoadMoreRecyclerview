package com.example.administrator.loadmorerecyclerview.presenter

import com.example.administrator.loadmorerecyclerview.bean.Top250
import com.example.administrator.loadmorerecyclerview.api.ApiManager
import com.example.administrator.loadmorerecyclerview.view.IMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl constructor(main:IMain): IMainPresenter {
    private val mMain = main

    override fun getTop250() {
        ApiManager.getInstance().getTopServer()
                .getTop250(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<List<Top250.Subjects>> { Top250->
                    val subjects = Top250.subjects
                    subjects?.let {
                        for (i in subjects.indices){
                            subjects[i].index = Top250.start!!.plus(i)
                        }
                    }
                    subjects
                }
                .subscribe({
                    mMain.getTop250(it)
                },{
                    mMain.onError()
                })
    }

    override fun loadMoreData(start: Int) {
        ApiManager.getInstance().getTopServer()
                .getTop250(start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map<List<Top250.Subjects>> { Top250->
                    val subjects = Top250.subjects
                    subjects?.let {
                        for (i in subjects.indices){
                            subjects[i].index = Top250.start!!.plus(i)
                        }
                    }
                    subjects
                }
                .subscribe({
                    if (it.isNotEmpty()){
                        mMain.loadMoreData(it)
                    }else{
                        mMain.noMore()
                    }

                },{
                    mMain.onError()
                })
    }
}