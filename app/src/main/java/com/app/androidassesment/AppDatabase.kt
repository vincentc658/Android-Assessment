package com.app.androidassesment

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.androidassesment.data.CartItem
import com.app.androidassesment.network.CartDao

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
