package com.applications.all_possibilities.Data

import android.os.Parcelable
import android.util.Log
import com.applications.all_possibilities.PuzzleManager
import kotlinx.android.parcel.Parcelize

/**
 * @param value1
 * @param value2
 * @param value3
 * @param goodAnswers  keeps all proper Results of [value1]  sign [value2] sign [value3] = result
 * it means keeps all possibilities which we can create with these three values and any of 4 signs(* / + -) and are not greater than [maxResult]
 * @param maxResult
 *
 */
@Parcelize
data class Puzzle(
    val value1: Int,
    val value2: Int,
    val value3: Int,
    val goodAnswers: ArrayList<Answer>,
    val maxResult: Int
    ):Parcelable {

    fun info(tag: String) {
        goodAnswers.forEach {
            Log.i(tag,"$value1" +
                    " ${PuzzleManager.getSignString(it.sign1)}" +
                    " $value2 ${PuzzleManager.getSignString(it.sign2)}" +
                    " $value3" +
                    " = ${it.result}")
        }
        Log.i(tag, "answers: ${goodAnswers.size}")
    }


}
