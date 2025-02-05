package com.example.state.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.state.product.domain.CreateProductUseCase
import com.example.state.product.domain.GetProductsUseCase

class ProductViewModelFactory(
    private val createProductUseCase: CreateProductUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(createProductUseCase, getProductsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
