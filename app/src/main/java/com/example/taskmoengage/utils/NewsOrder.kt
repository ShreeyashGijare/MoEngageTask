package com.example.taskmoengage.utils

sealed class NewsOrder(val orderType: OrderType) {
    class OrderBasedOnDateTime(orderType: OrderType) : NewsOrder(orderType)

}
