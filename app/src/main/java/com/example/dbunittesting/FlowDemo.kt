package com.example.dbunittesting

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlowDemo {

    fun getFlow() = flow<Int> {
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
    }
}