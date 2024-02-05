package com.example.taskmoengage.utils

sealed class OrderType {

    object LatestFirst : OrderType()

    object OldestFirst : OrderType()
    object Title : OrderType()

}