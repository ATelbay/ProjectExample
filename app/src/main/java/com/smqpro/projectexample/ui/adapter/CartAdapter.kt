package com.smqpro.projectexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smqpro.projectexample.databinding.CartItemBinding
import com.smqpro.projectexample.model.dto.Product
import com.smqpro.projectexample.util.Position

class CartAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectingState = false

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem.hashCode() == newItem.hashCode()

    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            this
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Product>) {
        differ.submitList(list)
    }

    class ProductViewHolder
    constructor(
        private val binding: CartItemBinding,
        private val adapter: CartAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) = with(binding) {

            val countText = if (item.type == Product.ProductType.NUMBER) "шт" else "кг"
            productTitleTv.text = item.title
            productCount.text = String.format("%d$countText", item.count)

            countUpButton.setOnClickListener { adapter.onCountUpListener?.let { it(item) } }
            countDownButton.setOnClickListener { adapter.onCountDownListener?.let { it(item) } }
            productTitleTv.setOnClickListener { adapter.onClickListener?.let { it(item) } }
            productTitleTv.setOnLongClickListener { adapter.onEditProductListener?.let { it(item) }; true }

        }


    }

    private var onClickListener: ((Product) -> Unit)? = null
    fun setOnClickListener(listener: (Product) -> Unit) {
        onClickListener = listener
    }

    private var onDeleteListener: ((Position, Product) -> Unit)? = null
    fun setOnDeleteListener(listener: (Position, Product) -> Unit) {
        onDeleteListener = listener
    }

    private var onCountUpListener: ((Product) -> Unit)? = null
    fun setOnCountUpListener(listener: (Product) -> Unit) {
        onCountUpListener = listener
    }

    private var onCountDownListener: ((Product) -> Unit)? = null
    fun setOnCountDownListener(listener: (Product) -> Unit) {
        onCountDownListener = listener
    }

    private var onCountZeroListener: ((Product) -> Unit)? = null
    fun setOnCountZeroListener (listener: (Product) -> Unit) {
        onCountZeroListener = listener
    }

    private var onEditProductListener: ((Product) -> Unit)? = null
    fun setOnEditProductListener(listener: (Product) -> Unit) {
        onEditProductListener = listener
    }
}