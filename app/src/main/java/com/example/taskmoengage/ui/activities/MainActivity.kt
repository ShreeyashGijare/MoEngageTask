package com.example.taskmoengage.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmoengage.R
import com.example.taskmoengage.base.UiState
import com.example.taskmoengage.databinding.ActivityMainBinding
import com.example.taskmoengage.databinding.BottomsheetFilterBinding
import com.example.taskmoengage.model.newsModel.Article
import com.example.taskmoengage.ui.adapters.FilterBottomSheetAdapter
import com.example.taskmoengage.ui.adapters.ViewPagerAdapter
import com.example.taskmoengage.ui.newsInterface.NewsClickInterface
import com.example.taskmoengage.ui.viewModel.MainViewModel
import com.example.taskmoengage.utils.Constants
import com.example.taskmoengage.utils.KeyValuePairImpl
import com.example.taskmoengage.utils.OrderType
import com.example.taskmoengage.widgets.ViewPagerPageTransformer
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), NewsClickInterface {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsPagerAdapter: ViewPagerAdapter
    private lateinit var filterBottomSheetAdapter: FilterBottomSheetAdapter
    private lateinit var viewFilterBottomSheet: BottomSheetDialog
    private lateinit var bottomSheetFilterBinding: BottomsheetFilterBinding
    private var selectedFilter: String = "Latest News"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpFilterBottomSheet()
        setUpFilterAdapter()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getNewsData(Constants.BASE_URL, OrderType.LatestFirst)

        mBinding.btnFilter.setOnClickListener {
            viewFilterBottomSheet.show()
        }

        setObserver()
    }

    private fun setObserver() {
        mainViewModel.onSuccess().observe(this) {
            if (it != null) {
                renderUI(it)
            }
        }
    }

    private fun renderUI(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                showProgressBarAndStartAnimation()
            }

            is UiState.Success -> {
                hideProgressBarAndStopAnimation()
                setUpAdapter(uiState.responseData)
            }

            is UiState.Error -> {
                hideProgressBarAndStopAnimation()
                Toast.makeText(this, uiState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showProgressBarAndStartAnimation() {
        mBinding.ivProgressBar.visibility = View.VISIBLE
        mBinding.ivProgressBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.loading))
    }

    private fun hideProgressBarAndStopAnimation() {
        mBinding.ivProgressBar.visibility = View.GONE
        mBinding.ivProgressBar.clearAnimation()
    }

    private fun setUpAdapter(articleList: List<Article>) {
        newsPagerAdapter = ViewPagerAdapter(this, articleList, this)
        mBinding.newsViewPager.adapter = newsPagerAdapter
        mBinding.newsViewPager.setPageTransformer(false, ViewPagerPageTransformer())
    }

    private fun setUpFilterAdapter() {
        filterBottomSheetAdapter =
            FilterBottomSheetAdapter(this, Constants.getOrderTypesList(), this, selectedFilter)

        val layoutManager: LinearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bottomSheetFilterBinding.rvFilterTypes.layoutManager = layoutManager
        bottomSheetFilterBinding.rvFilterTypes.adapter = filterBottomSheetAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun newsClickListener(newsItem: Article) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
        startActivity(browserIntent)
    }

    override fun bottomSheetFilterClickListener(filterItem: KeyValuePairImpl) {

        when (filterItem.key) {
            is OrderType.LatestFirst -> {
                selectedFilter = filterItem.value.toString()
                mainViewModel.getNewsData(Constants.BASE_URL, OrderType.LatestFirst)
            }

            is OrderType.OldestFirst -> {
                selectedFilter = filterItem.value.toString()
                mainViewModel.getNewsData(Constants.BASE_URL, OrderType.OldestFirst)
            }

            else -> {
                selectedFilter = filterItem.value.toString()
                mainViewModel.getNewsData(Constants.BASE_URL, OrderType.Title)
            }
        }
        viewFilterBottomSheet.dismiss()
    }


    private fun setUpFilterBottomSheet() {
        bottomSheetFilterBinding =
            BottomsheetFilterBinding.inflate(layoutInflater)
        viewFilterBottomSheet =
            BottomSheetDialog(this, R.style.Bottomsheet)
        viewFilterBottomSheet.setContentView(bottomSheetFilterBinding.root)

    }

}