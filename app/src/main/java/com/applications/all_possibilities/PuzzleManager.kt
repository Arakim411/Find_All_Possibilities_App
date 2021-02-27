package com.applications.all_possibilities

import android.os.Parcelable
import android.util.Log
import com.applications.all_possibilities.Data.Answer
import com.applications.all_possibilities.Data.Puzzle
import kotlinx.android.parcel.Parcelize


private const val TAG = "puzzle_manager"
private const val MAX_RESULT_NOT_DEFINED = -34124404

class PuzzleManager {

    companion object {

        /**
         * @param value1
         * @param value2
         * @param value3
         * @param _maxResult max result of  [value1], [value2], [value3] if is was not given in parameters equals value1*value2*value3
         *
         * Function creates every math possibility of [value1], [value2], [value3] where max  result is [_maxResult]
         *
         * @return [Puzzle], [null]
         *
         * [null] when passed values don't let create any possibility
         */
        fun getNewPuzzle(
            value1: Int,
            value2: Int,
            value3: Int,
            _maxResult: Int = MAX_RESULT_NOT_DEFINED
        ): Puzzle? {
            val goodAnswers = ArrayList<Answer>()
            val signs = arrayListOf(SIGN.PLUS, SIGN.MINUS, SIGN.Multiplication, SIGN.DIVISION)
            Log.i(TAG, "creating new puzzle")

            val maxResult =
                if (_maxResult == MAX_RESULT_NOT_DEFINED) value1 * value2 * value3
                else _maxResult

            if (value1 <= 0 || value2 <= 0 || value3 <= 0 || maxResult < 0) {
                throw error(
                    " CREATING PUZZLE ERROR maxValue1, maxValue2, maxValue 3 must be greater than 0 but is: $value1, $value1, $value1 and maxResult must be greater than -1 but is $maxResult"
                )
            }


            for (sign1 in signs.iterator()) {
                for (sign2 in signs.iterator()) {
                    for (i in 0 until maxResult+1){
                        if(isActionCorrect(value1,value2,value3,sign1,sign2,i)){
                            val answer = Answer(i,sign1, sign2)
                            goodAnswers.add(answer)
                        }
                    }
                }
            }

            if(goodAnswers.size == 0)
                return null

            Log.d(TAG, "Answers: ${goodAnswers.size}")
            goodAnswers.shuffle()
            val puzzle = Puzzle(value1,value2,value3,goodAnswers,_maxResult)
            puzzle.info(TAG)

            return puzzle
        }


        private fun isActionCorrect(
            value1: Int,
            value2: Int,
            value3: Int,
            sign1: SIGN,
            sign2: SIGN,
            score: Int
        ): Boolean {

            if ((sign1 == SIGN.DIVISION && value1.rem(value2) != 0) || (sign2 == SIGN.DIVISION && value2.rem(
                    value3
                ) != 0)
            )
                return false
            // we don't allow to pass values like 5 / 2 = 2
            // we pass examples only without rest


            val result: Int = if (sign2 == SIGN.DIVISION || sign2 == SIGN.Multiplication) {
                // for 2,3 values and 1
                (value2.countWithSign(sign2, value3)).countWithSign(sign1, value1)
            } else {
                // for 1,2 values and 3
                (value1.countWithSign(sign1, value2)).countWithSign(sign2, value3)
            }

            return result == score

        }

        fun getSignString(sign: SIGN): String =
            when (sign) {
                SIGN.PLUS -> "+"
                SIGN.MINUS -> "-"
                SIGN.DIVISION -> "\\"
                SIGN.Multiplication -> "*"
            }


    }


}

@Parcelize
enum class SIGN: Parcelable {
    PLUS,
    MINUS,
    DIVISION,
    Multiplication

}
