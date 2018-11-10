package com.news.app.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.news.app.R
import com.news.app.databinding.RowDetailBinding
import com.news.app.model.Detail

/**
 * Created by Ankit on 07/11/2018.
 */

class DetailAdapter(val list:ArrayList<Detail>, val context: Context):RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val binding : RowDetailBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_detail, parent, false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: DetailAdapter.ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.title.text = data.Title
        holder.binding.time.text =  Html.escapeHtml(context.getString(R.string.name, data.Name, data.Time))
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: RowDetailBinding) : RecyclerView.ViewHolder(binding.cardView)

}
