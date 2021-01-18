package com.example.pamproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pamproject.dao.CurrencyDAO
import com.example.pamproject.entity.Currency


@Database(entities = [Currency::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDAO(): CurrencyDAO

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val DB_NAME = "currencies"

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return this.INSTANCE!!
        }
    }
}