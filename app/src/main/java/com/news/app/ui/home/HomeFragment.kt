package com.news.app.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.news.app.DetailActivity
import com.news.app.R
import com.news.app.adapter.TopicsAdapter
import com.news.app.model.Article
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Created by Ankit on 07/11/2018.
 */

class HomeFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TopicsAdapter
    private var isFirstTime: Boolean = true
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        if (isNetworkAvailable(activity!!)) {

            attachObserver()

        } else {
            Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
    }

    private fun attachObserver(){
        viewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { showLoadingDialog(it) }
        })

        viewModel.getTopics().observe(this, Observer<ArrayList<Article>> { article ->

            if (isFirstTime) {
                adapter = TopicsAdapter(article!!, activity!!)
                recyclerView.adapter = adapter
                isFirstTime = false

                adapter.setOnItemClickListener(object : TopicsAdapter.OnItemClickListener {
                    override fun onClick(view: View, data: Article) {
                        if (!data.Kids.isEmpty()) {
                            val intent = Intent(activity, DetailActivity::class.java)
                            intent.putExtra("kids", data.Kids)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, getString(R.string.not_found), Toast.LENGTH_LONG).show()
                        }
                    }
                })
            } else {
                if (adapter != null) {
                    adapter!!.notifyDataSetChanged()
                }
            }
        })

        viewModel.apiError.observe(this, Observer<Throwable> {
            it?.let {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun isNetworkAvailable(activity: FragmentActivity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {


                }
            }
        })
    }

    private fun showLoadingDialog(show: Boolean) {
        if (show) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
    }

}
