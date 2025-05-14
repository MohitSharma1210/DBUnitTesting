package com.example.dbunittesting

import org.junit.Test

class MapTest {

    @Test
    fun testUserMap() {

        val list = listOf("Mohit", "Riddhi", "Uttam", "Papa", "Mummy", "Mom")

        println(list.chunked(3))
        val userMap = mutableMapOf(
            1 to "Alice",
            2 to "Bob",
            3 to "Charlie"
        )


        for (name in userMap.keys) {
            println("User Name: $name")
        }
    }
}