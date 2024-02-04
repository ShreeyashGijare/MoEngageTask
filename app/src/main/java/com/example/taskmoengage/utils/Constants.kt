package com.example.taskmoengage.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

object Constants {

    const val BASE_URL =
        "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"


    fun convertStringToDate(date: String): Long {
        var timeInMilliseconds: Long = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            val mDate: Date = sdf.parse(date)
             timeInMilliseconds = mDate.time
            println("Date in milli :: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

}