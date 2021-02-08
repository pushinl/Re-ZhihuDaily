package com.example.rezhihudaily.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rezhihudaily.recylerview.Adapter
import com.example.rezhihudaily.recylerview.News
import com.example.rezhihudaily.R
import com.example.rezhihudaily.databinding.ActivityHomeBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class HomeActivity : AppCompatActivity() {
    //使用ViewBinding代替findViewById,延迟初始化
    private lateinit var binding: ActivityHomeBinding

    private val newsList = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initnews()

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
        val adapter = Adapter(this, newsList)
        binding.recyclerView.adapter = adapter

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

    private fun initnews() {
        repeat(10) {
            newsList.add(News(getRandomLengthName("Apple"), R.drawable.apple_pic))
            newsList.add(News(getRandomLengthName("Banana"), R.drawable.banana_pic))
            newsList.add(News(getRandomLengthName("Orange"), R.drawable.orange_pic))
            newsList.add(News(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic))
            newsList.add(News(getRandomLengthName("Pear"), R.drawable.pear_pic))
            newsList.add(News(getRandomLengthName("Grape"), R.drawable.grape_pic))
            newsList.add(News(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic))
            newsList.add(News(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic))
            newsList.add(News(getRandomLengthName("Cherry"), R.drawable.cherry_pic))
            newsList.add(News(getRandomLengthName("Mango"), R.drawable.mango_pic))
        }
    }

    private fun getRandomLengthName(name: String): String {
        val length = Random().nextInt(20) + 1
        val builder = StringBuilder()
        for (i in 0 until length) {
            builder.append(name)
        }
        return builder.toString()
    }

    private fun refreshNews(adapter: Adapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initnews()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

}