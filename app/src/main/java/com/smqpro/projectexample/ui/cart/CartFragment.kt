package com.smqpro.projectexample.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.smqpro.projectexample.R
import com.smqpro.projectexample.databinding.FragmentCartBinding
import com.smqpro.projectexample.ui.MainActivity
import com.smqpro.projectexample.ui.adapter.CartAdapter
import com.smqpro.projectexample.util.MarginItemDecoration
import kotlinx.coroutines.flow.collect

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartAdapter = CartAdapter()
    private val mainViewModel by lazy { (activity as MainActivity).mainViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        setUi()
        return binding.root
    }

    private fun setUi() {
        setViews()
        setListeners()
        collectFlow()
    }

    private fun setViews() = binding.apply {
        rv.apply {
            adapter = cartAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_16)))
        }


    }

    private fun setListeners() = binding.apply {
        cartAdapter.setOnDeleteListener { _, product ->
            mainViewModel.deleteCartItem(product)
        }
    }

    private fun collectFlow() = lifecycleScope.launchWhenCreated {
        mainViewModel.cartList.collect {
            if (it.isNotEmpty()) {
                binding.apply {
                    emptyCartTv.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                    orderButton.visibility = View.VISIBLE
                }
                cartAdapter.submitList(it)
            } else {
                binding.apply {
                    emptyCartTv.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                    orderButton.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}