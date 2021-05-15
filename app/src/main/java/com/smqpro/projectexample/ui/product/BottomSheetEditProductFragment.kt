package com.smqpro.projectexample.ui.product

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.smqpro.projectexample.databinding.FragmentAddProductBinding
import com.smqpro.projectexample.model.dto.Product
import com.smqpro.projectexample.ui.main.MainActivity
import com.smqpro.projectexample.util.showSnackbar

class BottomSheetEditProductFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by lazy { (activity as MainActivity).mainViewModel }
    private var type = Product.ProductType.NUMBER
    private val product by lazy { navArgs<BottomSheetEditProductFragmentArgs>().value.product }

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
                    val product = Product(product.id, title, type, product.count)
                    mainViewModel.insertProduct(product)
                    showSnackbar("Продукт изменен")
                    findNavController().navigateUp()
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