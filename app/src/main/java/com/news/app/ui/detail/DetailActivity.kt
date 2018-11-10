package com.news.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.news.app.ui.detail.DetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        if (savedInstanceState == null) {

            val kids = intent.getIntegerArrayListExtra("kids")

            val bundle = Bundle()
            bundle.putIntegerArrayList("kids", kids)

            val detailFragment = DetailFragment()

            detailFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .commitNow()


        }
    }

}
