package com.news.app.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.news.app.R
import com.news.app.adapter.DetailAdapter
import com.news.app.model.Detail
import kotlinx.android.synthetic.main.detail_fragment.*

/**
 * Created by Ankit on 08/11/2018.
 */
class DetailFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: DetailAdapter
    private var isFirstTime: Boolean = true
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.detail_fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val kids = getArguments()?.getIntegerArrayList("kids")
        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(kids!!)).get(DetailViewModel::class.java)

        if (isNetworkAvailable(activity!!)) {
            attachObserver()
        }else{
            Toast.makeText(activity, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
    }

    private fun attachObserver(){
        viewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { showLoadingDialog(it) }
        })

        viewModel.getDetail().observe(this, Observer<ArrayList<Detail>> { article ->
            if(isFirstTime){
                adapter = DetailAdapter(article!!, activity!!)
                recyclerView.adapter = adapter
                isFirstTime = false
            }else{
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

    private fun showLoadingDialog(show: Boolean) {
        if (show) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
    }

}
