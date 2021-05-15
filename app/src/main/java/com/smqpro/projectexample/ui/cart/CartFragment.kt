package com.smqpro.projectexample.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.smqpro.projectexample.R
import com.smqpro.projectexample.databinding.FragmentCartBinding
import com.smqpro.projectexample.ui.adapter.CartAdapter
import com.smqpro.projectexample.ui.main.MainActivity
import com.smqpro.projectexample.util.MarginItemDecoration
import com.smqpro.projectexample.util.onSwipeToDeleteListener
import com.smqpro.projectexample.util.showSnackbar
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
        cartAdapter.setOnCountUpListener { product ->
            mainViewModel.countItemUp(product.id)
        }
        cartAdapter.setOnCountDownListener { product ->
            mainViewModel.countItemDown(product.id)
        }
        cartAdapter.setOnEditProductListener {
            val directions =
                CartFragmentDirections.actionCartFragmentToBottomSheetEditProductFragment(it)
            findNavController().navigate(directions)
        }
        rv.onSwipeToDeleteListener {
            val product = cartAdapter.differ.currentList[it]
            mainViewModel.setItemCountToZero(product.id)
            showSnackbar("Продукт убран из корзины")
        }
        clearCartButton.setOnClickListener {
            mainViewModel.setAllItemCountToZero()
        }
    }

    private fun collectFlow() = lifecycleScope.launchWhenCreated {
        mainViewModel.cartList.collect {
            if (it.isNotEmpty()) {
                binding.apply {
                    emptyCartTv.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                    clearCartButton.visibility = View.VISIBLE
                }
                cartAdapter.submitList(it)
            } else {
                binding.apply {
                    emptyCartTv.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                    clearCartButton.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}