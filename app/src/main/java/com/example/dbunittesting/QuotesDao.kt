package com.example.dbunittesting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert()
    suspend fun insertQuote(quote: Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Query("DELETE from quote")
    suspend fun delete()

    @Query("SELECT * from quote")
    fun getQuotes() : LiveData<List<Quote>>

    @Query("SELECT * from quote")
    fun getQuotesForFlow() : Flow<List<Quote>>

    @Query("SELECT * from quote where id =:quoteId")
    suspend fun getQuoteById(quoteId: Int):Quote

}

