package com.smqpro.projectexample.ui.product

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.smqpro.projectexample.databinding.FragmentAddProductBinding
import com.smqpro.projectexample.model.dto.Product
import com.smqpro.projectexample.ui.main.MainActivity
import com.smqpro.projectexample.util.showSnackbar

class AddProductFragment : Fragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by lazy { (activity as MainActivity).mainViewModel }
    private var type = Product.ProductType.NUMBER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        setUi()
        return binding.root
    }

    private fun setUi() {
        setViews()
        setListeners()
    }

    private fun setViews() = binding.apply {
        productNameEt.requestFocus()
    }

    private fun setListeners() = binding.apply {
        productNameEt.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val title = productNameEt.text.trim().toString()
                if (title.isEmpty()) {
                    productNameEt.error = "Введите название"
                } else {
                    mainViewModel.insertProduct(Product.getItem(title, type))
                    showSnackbar("Продукт добавлен")
                    productNameEt.text = null
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        numberTypeButton.setOnClickListener {
            type = Product.ProductType.NUMBER
            weightTypeButton.isChecked = false
        }
        weightTypeButton.setOnClickListener {
            type = Product.ProductType.WEIGHT
            numberTypeButton.isChecked = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}