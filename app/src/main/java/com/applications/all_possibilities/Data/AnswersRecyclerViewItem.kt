package com.applications.all_possibilities.Data

data  class AnswersRecyclerViewItem(var value1 : String = "X", var value2: String  = "X", var value3: String = "X",
                                    var result: String = "X", var sign1String: String = "X", var sign2String: String = "X", var isGoodAnswer: Boolean = false){

    override fun equals(other: Any?): Boolean {

        if(other != null && other is AnswersRecyclerViewItem)
            return  other.value1 == value1 &&
                    other.value2 == value2 &&
                    other.value3 == value3 &&
                    other.result == result &&
                    other.sign1String == sign1String &&
                    other.sign2String == sign2String


        return super.equals(other)
    }
}
