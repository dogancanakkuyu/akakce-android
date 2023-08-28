package com.example.akakcecaseandroid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akakcecaseandroid.R
import com.example.akakcecaseandroid.model.data.Product
import com.example.akakcecaseandroid.utils.Constant
import com.example.akakcecaseandroid.view.MainFragment
import com.example.akakcecaseandroid.view.MainFragmentDirections

class ProductListAdapter(private val products: MutableList<Product>,private val listTag: String) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {
    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val dropRatioLayout: ConstraintLayout = itemView.findViewById(R.id.dropRatioCircle)
        private val dropRatio: TextView = itemView.findViewById(R.id.dropRatio)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val price: TextView = itemView.findViewById(R.id.productPrice)
        private val countOfPrices: TextView = itemView.findViewById(R.id.countOfPrices)
        private val followCount: TextView = itemView.findViewById(R.id.followCount)

        @SuppressLint("SetTextI18n")
        fun bindData(product: Product) {
            Glide.with(itemView.context)
                .load(product.imageUrl)
                .override(80,120)
                .transform(CenterCrop(), RoundedCorners(15))
                .into(image)
            productName.text = product.name
            price.text = product.price.toString() + " TL"
            product.countOfPrices?.let {
                countOfPrices.text = "$it satıcı >"
            }
            product.followCount?.let {
                followCount.text = "$it takip"
            }
            product.dropRatio?.let {
                dropRatio.text = "%$it"
                dropRatioLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ProductListViewHolder {
        if(listTag == Constant.HORIZONTAL_TAG) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_products,parent,false)
            return ProductListViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_products,parent,false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductListAdapter.ProductListViewHolder,
        position: Int
    ) {
        val code: Int = products[position].code
        val imageUrl: String = products[position].imageUrl
        val productName: String = products[position].name
        val price: Double = products[position].price
        val dropRatio: Double? = products[position].dropRatio
        val countOfPrices: Int? = products[position].countOfPrices
        val followCount: Int? = products[position].followCount
        holder.bindData(Product(code,imageUrl,productName,dropRatio, price, countOfPrices, followCount))
        holder.itemView.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToProductDetailFragment(products[position].code)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addProducts(list: List<Product>) {
        val startPos = products.size
        list.forEach { product->
            products.add(product)
        }
        notifyItemRangeInserted(startPos,list.size)
    }
}