package com.example.state.product.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.product.data.model.CreateProductRequest
import com.example.state.product.data.model.ProductDTO
import com.example.state.product.domain.CreateProductUseCase
import com.example.state.product.domain.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductViewModel(
    private val createProductUseCase: CreateProductUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductDTO>>()
    val products: LiveData<List<ProductDTO>> get() = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadProducts()
    }

    // Función privada para cargar productos
    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val result = getProductsUseCase.invoke()
                result.onSuccess { productList ->
                    _products.value = productList
                }.onFailure { exception ->
                    _error.value = "Error al cargar los productos: ${exception.message}"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar los productos: ${e.message}"
            }
        }
    }

    // Agregar un nuevo producto
    fun addProduct(productRequest: CreateProductRequest) {
        viewModelScope.launch {
            try {
                val result = createProductUseCase.invoke(productRequest)
                result.onSuccess {
                    loadProducts() // Recargamos los productos después de agregar uno nuevo
                }.onFailure { exception ->
                    _error.value = "Error al agregar el producto: ${exception.message}"
                }
            } catch (e: Exception) {
                _error.value = "Error al agregar el producto: ${e.message}"
            }
        }
    }
}


