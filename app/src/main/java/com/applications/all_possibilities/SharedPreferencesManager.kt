package com.applications.all_possibilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


abstract class SharedPreferencesManager {

    companion object {
        fun getPuzzleCreator1Manager(context: Context): PuzzleCreator1Manager =
            PuzzleCreator1Manager(context)

        fun getPuzzleCreator2Manager(context: Context): PuzzleCreator2Manager =
            PuzzleCreator2Manager(context)
    }

    @SuppressLint("CommitPrefEdits")
    class PuzzleCreator1Manager(context: Context) {

        private val creator1SharedPreferencesKey = "creator1--shared--preferences"

        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(creator1SharedPreferencesKey, Context.MODE_PRIVATE)
        private val editor = sharedPreferences.edit()

        companion object {
            // Keys
            const val RESULT_KEY = "result_key"
            const val VALUE1_KEY = "value1_key1"
            const val VALUE2_KEY = "value2_key2"
            const val VALUE3_KEY = "value3_key3"

            //def values
            const val RESULT_DEFAULT = "9"
            const val VALUE1_DEFAULT = "9"
            const val VALUE2_DEFAULT = "9"
            const val VALUE3_DEFAULT = "9"
        }


        fun getResult(): String? = sharedPreferences.getString(RESULT_KEY, RESULT_DEFAULT)
        fun getValue1(): String? = sharedPreferences.getString(VALUE1_KEY, VALUE1_DEFAULT)
        fun getValue2(): String? = sharedPreferences.getString(VALUE2_KEY, VALUE2_DEFAULT)
        fun getValue3(): String? = sharedPreferences.getString(VALUE3_KEY, VALUE3_DEFAULT)

        fun setResult(result: String) = editor.putString(RESULT_KEY, result).commit()
        fun setValue1(value1: String) = editor.putString(VALUE1_KEY, value1).commit()
        fun setValue2(value2: String) = editor.putString(VALUE2_KEY, value2).commit()
        fun setValue3(value3: String) = editor.putString(VALUE3_KEY, value3).commit()

//        fun setSharedPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
//            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    }

    class  PuzzleCreator2Manager(context: Context){

        private val creator2SharedPreferencesKey = "creator2--shared--preferences"

        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(creator2SharedPreferencesKey, Context.MODE_PRIVATE)
        private val editor = sharedPreferences.edit()

        companion object{
            const val TIME_KEY = "TIM_KEY"
            const val COUNT_KEY ="COUNT_KEY"

            //i keep it in string because it is easier to manage
            const val TIME_DEFAULT = "20"
            const val COUNT_DEFAULT = "5"
        }

        fun getTime(): String? = sharedPreferences.getString(TIME_KEY, TIME_DEFAULT)
        fun getCount(): String? = sharedPreferences.getString(COUNT_KEY, COUNT_DEFAULT)


        fun setTime(newTime: String){ editor.putString(TIME_KEY,newTime).commit()}
        fun setCount(newCount: String){editor.putString(COUNT_KEY, newCount).commit()}


//        fun setSharedPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
//            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    }


}