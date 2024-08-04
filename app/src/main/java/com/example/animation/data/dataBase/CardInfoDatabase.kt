package com.example.animation.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animation.data.dataBase.dao.LocalDataSource
import com.example.animation.data.model.CardListItem
import com.example.animation.data.model.CardSummary



@Database(entities = [CardListItem::class], version = 1, exportSchema = false)
abstract class CardInfoDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDataSource
}