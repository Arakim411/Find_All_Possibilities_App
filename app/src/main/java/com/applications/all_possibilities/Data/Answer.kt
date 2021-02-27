package com.applications.all_possibilities.Data

import android.os.Parcelable
import com.applications.all_possibilities.SIGN
import kotlinx.android.parcel.Parcelize

@Parcelize
data  class Answer (val result: Int,val sign1: SIGN, val sign2: SIGN): Parcelable{

    override fun toString(): String {
        return  "result: $result, sign1: $sign1, sign2: $sign2"
    }
}
