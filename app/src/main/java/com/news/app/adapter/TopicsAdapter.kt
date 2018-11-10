package com.news.app.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.news.app.R
import com.news.app.databinding.RowNewsBinding
import com.news.app.model.Article

/**
 * Created by Ankit on 07/11/2018.
 */

class TopicsAdapter(val list:ArrayList<Article>,val context: Context):RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val binding : RowNewsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_news, parent, false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TopicsAdapter.ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.cardView.setOnClickListener {
            listener.onClick(holder.itemView, data)
        }

        holder.binding.title.text = data.Title
        holder.binding.score.text = context.getString(R.string.score, data.Score, data.Name)
        holder.binding.time.text =  context.getString(R.string.ago, data.Time)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: Article)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(var binding: RowNewsBinding) : RecyclerView.ViewHolder(binding.cardView)




    /*class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItems(data : Article){
            *//*val _textView:TextView = itemView.findViewById(R.id.textview)
            val _imageView: ImageView = itemView.findViewById(R.id.imageview)
            _textView.text = data.text
            _imageView.setImageBitmap(data.image)
*//*
            //set the onclick listener for the singlt list item
            itemView.setOnClickListener({
                //Log.e("ItemClicked", data.text)
            })
        }

    }*/
}

/*
*
* class MyAdapter(var dataList: List<MyData>) : RecyclerView.Adapter<MyAdapter.BindingHolder>() {
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder? {
        setOnItemClickListener(listener)
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val binding = OriginalItemLayoutBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val data = dataList[position]
        holder.binding.setData(data)
        holder.binding.originalLinearLayout.setOnClickListener({
            listener.onClick(it, data)
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: MyData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BindingHolder(var binding: OriginalItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
*
* */