package com.example.akakcecaseandroid.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akakcecaseandroid.R
import com.example.akakcecaseandroid.adapter.StorageOptionsAdapter
import com.example.akakcecaseandroid.databinding.FragmentProductDetailBinding
import com.example.akakcecaseandroid.model.ResponseState
import com.example.akakcecaseandroid.model.data.ProductDetail
import com.example.akakcecaseandroid.model.data.ProductDetailResult
import com.example.akakcecaseandroid.utils.Constant
import com.example.akakcecaseandroid.viewmodel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private lateinit var productDetailBinding: FragmentProductDetailBinding
    private val productDetailViewModel by viewModels<ProductDetailViewModel>()
    private val args by navArgs<ProductDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productDetailBinding = FragmentProductDetailBinding.inflate(inflater,container,false)
        return productDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackBtnPressed()
        sendRequest(Constant.PRODUCT_DETAIL_ENDPOINT,args.code)
    }

    private fun sendRequest(url: String, code: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            productDetailViewModel.fetchProductDetail(url, code)
            productDetailViewModel.detailResponse.collect{state->
                when (state) {
                    is ResponseState.Success<*> -> {

                        val response = state.data as ProductDetailResult
                        withContext(Dispatchers.Main) {
                            setView(response)
                        }
                    }

                    is ResponseState.Error -> {
                        println(state.msg)
                    }

                    is ResponseState.Loading -> {
                        //productDetailBinding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setView(response: ProductDetailResult) {
        val productDetail = response.result
        Glide.with(requireContext())
            .load(productDetail.imageUrl)
            .override(80,120)
            .transform(CenterCrop(), RoundedCorners(15))
            .into(productDetailBinding.image)
        productDetailBinding.productDetailRv.adapter = StorageOptionsAdapter(productDetail.storageOptions)
        productDetailBinding.mkName.text = productDetail.mkName
        productDetailBinding.productName.text = productDetail.productName
        productDetailBinding.badge.text = productDetail.badge
        productDetailBinding.ratingBar.rating = productDetail.rating.toFloat()
        productDetailBinding.countOfPrices.text = "${productDetail.countOfPrices} satıcı içinde kargo dahil en ucuz fiyat seçeneği"
        productDetailBinding.price.text = productDetail.price.toString() + " TL"
        if (productDetail.freeShipping) {
            productDetailBinding.shipping.visibility = View.VISIBLE
            productDetailBinding.shipping.setTextColor(R.color.shipping_color)
        }
        productDetailBinding.lastUpdate.text = "Son güncelleme: ${productDetail.lastUpdate}"
        productDetailBinding.progressBar.visibility = View.GONE
        productDetailBinding.fragmentLayout.visibility = View.VISIBLE
    }

    private fun onBackBtnPressed() {
        productDetailBinding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_mainFragment)
        }
    }
}