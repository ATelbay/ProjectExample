package com.smqpro.projectexample.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.smqpro.projectexample.model.dao.ProductDao
import com.smqpro.projectexample.model.dto.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Database(
    entities = [Product::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            )
                .addCallback(object : Callback() {
                    var job: Job? = null
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        job = CoroutineScope(Dispatchers.IO).launch {
                            getDatabase(context).productDao().insertList(Product.generateData())
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }

}