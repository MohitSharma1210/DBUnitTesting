package com.example.dbunittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteDaoTestForFlow {

    @get:Rule
    val instantTaskExecutorRule =InstantTaskExecutorRule()

    lateinit var quoteDatabase: QuoteDatabase
    lateinit var quotesDao: QuotesDao

    @Before
    fun setUp(){
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()
        quotesDao = quoteDatabase.quoteDao()
    }

    @Test
    fun insertQuote_expectedSingleQuote() = runBlocking{
        val quote = Quote(1, "This is Mohit", "Radhe")
        val quote1 = Quote(2, "This is Mohit", "Radhe1")

        // Insert first quote
        quotesDao.insertQuote(quote)

        val job = launch {
            delay(500)
            quotesDao.insertQuote(quote1) // Insert second quote after delay
        }

        val result = quotesDao.getQuotesForFlow().test {
            val firstEmission = awaitItem()
            Assert.assertEquals(1, firstEmission.size)

            val secondEmission = awaitItem()
            Assert.assertEquals(2, secondEmission.size)

            cancel()
        }

        job.join()

    }


    @Test
    fun deleteQuote_expectedSingleQuote() = runBlocking{
        val quote = Quote(0, "This is Mohit", "Radhe")
        quotesDao.insertQuote(quote)
        quotesDao.delete()
        val result = quotesDao.getQuotes().getOrAwaitValue()
        Assert.assertEquals(0, result.size)
    }

    @After
    fun tearDown(){
        quoteDatabase.close()
    }
}