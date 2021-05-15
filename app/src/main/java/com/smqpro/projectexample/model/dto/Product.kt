package com.smqpro.projectexample.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val type: ProductType,
    var count: Int = 0
): Serializable {

    val isInCart get() = count != 0

    enum class ProductType {
        WEIGHT,
        NUMBER
    }

    companion object {

        private var prodId = 0

        private val titleList = listOf(
            "Рис",
            "Молоко",
            "Гречка",
            "Сметана",
            "Лимон",
            "Доширак",
            "Сыр",
            "Диван",
            "Огурцы"
        )

        fun generateData(size: Int = titleList.size): List<Product> {
            val list = mutableListOf<Product>()
            for (i in 0 until size) {
                prodId = i
                val type = if (prodId % 2 == 0) ProductType.WEIGHT else ProductType.NUMBER
                list.add(Product(prodId, titleList[prodId], type))
            }
            return list
        }

        fun getItem(title: String, type: ProductType) = Product(prodId++, title, type)
    }

}
