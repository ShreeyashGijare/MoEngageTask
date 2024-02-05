package com.example.taskmoengage.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.taskmoengage.R
import com.example.taskmoengage.databinding.ItemNewsPagerBinding
import com.example.taskmoengage.model.newsModel.Article
import com.example.taskmoengage.ui.newsInterface.NewsClickInterface

class ViewPagerAdapter(
    private val context: Context,
    private var articleList: List<Article>?,
    private val newsClickInterface: NewsClickInterface
) : PagerAdapter() {
    override fun getCount(): Int = articleList!!.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val mBinding = ItemNewsPagerBinding.inflate(layoutInflater, container, false)

        Glide.with(context).load(articleList!![position].urlToImage)
            .error(R.drawable.image_not_found)
            .into(mBinding.ivArticleImage)

        mBinding.tvArticleTitle.text = articleList!![position].title

        if (articleList!![position].author.isNullOrEmpty()) {
            mBinding.tvArticleAuthor.visibility = View.GONE
        } else {
            mBinding.tvArticleAuthor.text = context.getString(
                R.string.set_ui_text,
                context.getString(R.string.tv_lbl_author),
                articleList!![position].author
            )
        }


        mBinding.tvArticleSource.text = context.getString(
            R.string.set_ui_text,
            context.getString(R.string.tv_lbl_source),
            articleList!![position].source.name
        )
        mBinding.tvArticleDescription.text = context.getString(
            R.string.set_ui_text,
            context.getString(R.string.tv_lbl_description),
            articleList!![position].description
        )

        mBinding.btnSeeMoreDetails.setOnClickListener {
            newsClickInterface.newsClickListener(articleList!![position])
        }

        if (position == 0) {
            mBinding.swipeToSeeMore.visibility = View.VISIBLE
            val animation = AnimationUtils.loadAnimation(
                context,
                R.anim.slide_up_anim
            )
            mBinding.swipeToSeeMore.startAnimation(
                animation
            )
        }else{
            mBinding.swipeToSeeMore.visibility = View.GONE

        }

        container.addView(mBinding.root, position)
        return mBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}