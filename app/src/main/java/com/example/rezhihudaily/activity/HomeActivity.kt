package com.example.rezhihudaily.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rezhihudaily.adapter.Adapter
import com.example.rezhihudaily.R
import com.example.rezhihudaily.network.Bean
import com.example.rezhihudaily.network.NetService
import com.example.rezhihudaily.databinding.ActivityHomeBinding
import com.example.rezhihudaily.`class`.NewsBean
import com.example.rezhihudaily.`class`.TopNewsBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread


class HomeActivity : AppCompatActivity() {
    //使用ViewBinding代替findViewById,延迟初始化
    private lateinit var binding: ActivityHomeBinding

    private val newsList = mutableListOf<NewsBean>()
    private val topNewsList = mutableListOf<TopNewsBean>()
    var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //加载布局
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //侧滑边栏
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        binding.navView.setCheckedItem(R.id.myStar)//默认选中
        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            true
        }

        //加载recyclerview的布局
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //设置adapter
        val adapter = Adapter(this, newsList, topNewsList)
        binding.recyclerView.adapter = adapter

        initNews()
        upLoadMore()

        //下拉刷新
        binding.swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        binding.swipeRefresh.setOnRefreshListener {
            refreshNews(adapter)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menu加载到toolbar
            menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {//点击事件
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.darkmode -> Toast.makeText(this, "Dark it", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "Get Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun initNews() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/news/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val newsListService = retrofit.create(NetService::class.java)
        var item: Bean?
        newsListService.dataList.enqueue(object : Callback<Bean> {
            override fun onResponse(call: Call<Bean>, response: Response<Bean>) {
                item = response.body()
                if(item != null) {
                    date = item!!.date
                    addTopNewsList(item!!)
                    addNewsList(item!!)
                }

                binding.recyclerView.adapter!!.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<Bean>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun upLoadMore() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager
                val visibleCount = layoutManager!!.childCount
                val totalCount = layoutManager.itemCount
                val lastVisiableItemPos = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (visibleCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisiableItemPos >= totalCount - 1) {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/news/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val newsListService = retrofit.create(NetService::class.java)
        var item: Bean?
        newsListService.getList(date!!).enqueue(object : Callback<Bean> {
            override fun onResponse(call: Call<Bean>, response: Response<Bean>) {
                item = response.body()
                date = item!!.date
                addNewsList(item!!)
                binding.recyclerView.adapter!!.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<Bean>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }



    private fun refreshNews(adapter: Adapter) {
        thread {
            Thread.sleep(1000)
            runOnUiThread {
                newsClear()
                initNews()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun newsClear() {
        newsList.clear()
        topNewsList.clear()
    }

    fun addNewsList(bean: Bean) {
        var title: String
        var hint: String
        var image: String
        var url: String
        for (i in bean.stories.indices) {
            title = bean.stories[i].title
            hint = bean.stories[i].hint
            image = bean.stories[i].images[0]
            url = bean.stories[i].url
            newsList.add(NewsBean(title, hint, image, url, date!!))
            //Log.d("MainActivity", "addNewsList: ${bean.stories[i].title}")
        }
    }

    fun addTopNewsList(bean: Bean) {
        var title: String
        var hint: String
        var image: String
        var url: String
        for (i in bean.top_stories.indices) {
            title = bean.top_stories[i].title
            hint = bean.top_stories[i].hint
            image = bean.top_stories[i].image
            url = bean.top_stories[i].url
            topNewsList.add(TopNewsBean(title, hint, image, url, i))
        }
    }
}