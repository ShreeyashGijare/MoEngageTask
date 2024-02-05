package com.example.taskmoengage.utils

import android.os.Parcel
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName
import java.util.LinkedList

data class KeyValuePairImpl(
    var key: OrderType? = null,
    var value: String? = null
)
