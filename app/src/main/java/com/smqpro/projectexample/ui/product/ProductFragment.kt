package com.smqpro.projectexample.ui.product

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.smqpro.projectexample.R
import com.smqpro.projectexample.databinding.FragmentProductBinding
import com.smqpro.projectexample.ui.MainActivity
import com.smqpro.projectexample.ui.adapter.ProductAdapter
import com.smqpro.projectexample.util.DataState
import com.smqpro.projectexample.util.MarginItemDecoration
import kotlinx.coroutines.flow.collect

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val productAdapter = ProductAdapter()
    private val mainViewModel by lazy { (activity as MainActivity).mainViewModel }

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
            Toast.makeText(context, "$product", Toast.LENGTH_SHORT).show()
        }
        productAdapter.setOnCartClickListener { _, product ->
            mainViewModel.insertCartItem(product)
        }
    }

    private fun collectFlow() = lifecycleScope.launchWhenCreated {
        mainViewModel.productList.collect {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is DataState.Success -> {
                    productAdapter.submitList(it.data)
                    binding.progressBar.isVisible = false
                }
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
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

}