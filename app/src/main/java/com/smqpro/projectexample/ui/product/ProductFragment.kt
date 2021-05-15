package com.smqpro.projectexample.ui.product

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.smqpro.projectexample.R
import com.smqpro.projectexample.databinding.FragmentProductBinding
import com.smqpro.projectexample.ui.adapter.ProductAdapter
import com.smqpro.projectexample.ui.main.MainActivity
import com.smqpro.projectexample.util.MarginItemDecoration
import com.smqpro.projectexample.util.onSwipeToDeleteListener
import com.smqpro.projectexample.util.showSnackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val productAdapter = ProductAdapter()
    private val mainViewModel by lazy { (activity as MainActivity).mainViewModel }
    private var uiStateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        setUi()
        return binding.root
    }

    private fun setUi() {
        setViews()
        setListeners()
        collectFlow()
        setHasOptionsMenu(true)
    }

    private fun setViews() = binding.apply {
        rv.apply {
            adapter = productAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_16)))
        }
    }

    private fun setListeners() = binding.apply {
        productAdapter.setOnClickListener { _, product ->
            if (product.isInCart) {
                mainViewModel.setItemCountToZero(product.id)
            } else {
                mainViewModel.countItemUp(product.id)
            }
            Toast.makeText(context, "$product", Toast.LENGTH_SHORT).show()
        }
        productAdapter.setOnCountUpListener { product ->
            mainViewModel.countItemUp(product.id)
        }
        productAdapter.setOnCountDownListener { product ->
            mainViewModel.countItemDown(product.id)
        }
        productAdapter.setOnCountZeroListener { product ->
            mainViewModel.setItemCountToZero(product.id)
        }
        productAdapter.setOnEditProductListener {
            val directions = ProductFragmentDirections
                .actionProductFragmentToBottomSheetEditProductFragment(it)
            findNavController().navigate(directions)
        }
        rv.onSwipeToDeleteListener {
            val product = productAdapter.differ.currentList[it]
            mainViewModel.deleteProduct(product)
            showSnackbar("Продукт удален")
        }
        fab.setOnClickListener {
            val directions = ProductFragmentDirections.actionProductFragmentToAddProductFragment()
            findNavController().navigate(directions)
        }
    }

    private fun collectFlow() {
        uiStateJob = lifecycleScope.launch {
            mainViewModel.productList.collect { productList ->
                productAdapter.submitList(productList)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigate_to_cart -> {
                val directions = ProductFragmentDirections.actionProductFragmentToCartFragment()
                findNavController().navigate(directions)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

}