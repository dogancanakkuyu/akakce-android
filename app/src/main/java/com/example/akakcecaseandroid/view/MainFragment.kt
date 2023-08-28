package com.example.akakcecaseandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.akakcecaseandroid.adapter.ProductListAdapter
import com.example.akakcecaseandroid.databinding.FragmentMainBinding
import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.data.Root
import com.example.akakcecaseandroid.utils.Constant
import com.example.akakcecaseandroid.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var mainFragmentBinding: FragmentMainBinding
    private val mainFragmentViewModel by viewModels<MainViewModel>()

    private var isLoading = false
    private var hasNextUrl = false //Whether response has nextUrl
    private var nextUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = FragmentMainBinding.inflate(inflater,container,false)
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentBinding.viewPager.adapter = ProductListAdapter(mutableListOf(),Constant.HORIZONTAL_TAG)
        managePagination()
    }

    private fun managePagination() {


        val layoutManager = mainFragmentBinding.verticalRecyclerview.layoutManager as GridLayoutManager
        mainFragmentBinding.verticalRecyclerview.adapter = ProductListAdapter(mutableListOf(),Constant.VERTICAL_TAG)
        mainFragmentBinding.verticalRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCount = layoutManager.itemCount
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && itemCount <= lastVisibleItemPos + 1 && hasNextUrl) {
                    isLoading = true
                    println("enter")
                    fetchProducts(nextUrl)
                }
            }
        })
        fetchProducts(Constant.FIRST_REQUEST_ENDPOINT)
    }

    private fun fetchProducts(url: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            mainFragmentViewModel.fetchProducts(url)
            val state = mainFragmentViewModel.responseFlow.value
            when(state) {
                is ResponseState.Success<*> -> {
                    val root = state.data as Root
                    withContext(Dispatchers.Main){
                        val viewPagerAdapter = mainFragmentBinding.viewPager.adapter as ProductListAdapter
                        root.result.horizontalProducts?.let {
                            viewPagerAdapter.addProducts(it)
                        }
                        mainFragmentBinding.wormDot.attachTo(mainFragmentBinding.viewPager)
                        hasNextUrl = root.result.nextUrl != null
                        nextUrl = root.result.nextUrl ?: ""
                        val rvAdapter = mainFragmentBinding.verticalRecyclerview.adapter as ProductListAdapter
                        rvAdapter.addProducts(root.result.products)
                        mainFragmentBinding.progressBar.visibility = View.GONE
                        mainFragmentBinding.mainFragmentLayout.visibility = View.VISIBLE
                        isLoading = false
                    }

                }
                is ResponseState.Error -> println(state.msg)
                is ResponseState.Loading -> {}
                else -> {}
            }
        }
    }
}