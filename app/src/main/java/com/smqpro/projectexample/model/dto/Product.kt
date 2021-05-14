package com.smqpro.projectexample.model.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smqpro.projectexample.R
import kotlin.random.Random

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    val id: Long,
    val title: String,
    @DrawableRes
    val image: Int,
    var count: Int,
    val type: ProductType,
    var inCart: Boolean = false,
    ) {
    enum class ProductType {
        WEIGHT,
        NUMBER
    }

    companion object {
        private val titleList = listOf(
            "Рис",
            "Молоко",
            "Гречка",
            "Сметана",
            "Лимон",
            "Доширак",
            "Сыр",
            "Диван",
            "Огурец"
        )
        private val imageList = listOf(
            R.drawable.rice,
            R.drawable.milk,
            R.drawable.buckwheat,
            R.drawable.sour_cream,
            R.drawable.lemon,
            R.drawable.doshirak,
            R.drawable.cheese,
            R.drawable.couch,
            R.drawable.cucumber
        )

        fun generateData(size: Int = titleList.size): List<Product> {
            val list = mutableListOf<Product>()
            for (id in 0 until size) {
                val type = if (id % 2 == 0) ProductType.WEIGHT else ProductType.NUMBER
                list.add(Product(id.toLong(), titleList[id], imageList[id], 0, type))
            }
            return list
        }
    }

}
