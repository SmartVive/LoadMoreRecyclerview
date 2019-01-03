package com.example.administrator.loadmorerecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.administrator.loadmorerecyclerview.adapter.RecyclerViewAdapter
import com.example.administrator.loadmorerecyclerview.bean.Top250
import com.example.administrator.loadmorerecyclerview.presenter.MainPresenterImpl
import com.example.administrator.loadmorerecyclerview.view.IMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMain {
    private var list: ArrayList<Top250.Subjects> = ArrayList();
    private var layoutManager: LinearLayoutManager? = null
    private var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this, list)
    private val mainPresenterImpl: MainPresenterImpl = MainPresenterImpl(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    override fun getTop250(list: List<Top250.Subjects>) {
        this.list.clear()
        this.list.addAll(list)
        recyclerViewAdapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun loadMoreData(list: List<Top250.Subjects>) {
        this.list.addAll(list)
        recyclerViewAdapter.loadMore(list.first().index!!)
    }

    override fun noMore() {
        //没有更多
        recyclerViewAdapter.noMore()
    }

    override fun onError() {
        Toast.makeText(this, "网络出错", Toast.LENGTH_SHORT).show()
        swipeRefreshLayout.isRefreshing = false
    }


    private fun initData() {
        swipeRefreshLayout.isRefreshing = true
        recyclerViewAdapter.refresh()
        mainPresenterImpl.getTop250()
    }

    private fun initView() {
        layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        rvMain.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvMain.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnLoadMoreDataListener(object : RecyclerViewAdapter.OnLoadMoreDataListener {
            override fun onLoadMoreData() {
                val start: Int = list.last().index!!.plus(1)
                mainPresenterImpl.loadMoreData(start)
            }

        })

        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }

    }


}
