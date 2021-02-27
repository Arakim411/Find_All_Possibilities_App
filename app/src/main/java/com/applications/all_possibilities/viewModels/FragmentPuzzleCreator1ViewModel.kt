package com.applications.all_possibilities.viewModels

import android.app.Application
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.all_possibilities.Data.Puzzle
import com.applications.all_possibilities.PuzzleManager
import com.applications.all_possibilities.R
import com.applications.all_possibilities.SharedPreferencesManager
import com.applications.all_possibilities.generateRandomValue
import kotlin.math.pow

private const val TAG = "Creator1ViewModel"

const val MESSAGE_RESULT = "MESSAGE_RESULT"
const val MESSAGE_VALUE1 = "MESSAGE_VALUE1"
const val MESSAGE_VALUE2 = "MESSAGE_VALUE2"
const val MESSAGE_VALUE3 = "MESSAGE_VALUE3"


class FragmentPuzzleCreator1ViewModel(application: Application) : AndroidViewModel(application) {


    // MutableLiveData
    val _maxResult = MutableLiveData<String?>()
    val _maxValue1 = MutableLiveData<String>()
    val _maxValue2 = MutableLiveData<String>()
    val _maxValue3 = MutableLiveData<String>()

    //sharedPreferences
    private val creator1Manager = SharedPreferencesManager.getPuzzleCreator1Manager(application)

    private val _errorText = MutableLiveData<ArrayList<String>>()
    val errorText: LiveData<ArrayList<String>>
        get() = _errorText


    private val _goToActionFragment = MutableLiveData<Puzzle?>()
    val goToActionFragment: LiveData<Puzzle?>
        get() = _goToActionFragment

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
    get() = _showToast

    //Values
    private val resultMaxLength = application.resources.getInteger(R.integer.resultMaxLength)
    private val generatedValuesMaxLength = application.resources.getInteger(R.integer.generatedValuesMaxLength)

    private  val maxIntegerResultValue = 10.0.pow(resultMaxLength).toInt() - 1
    private val maxIntegerValues = 10.0.pow(generatedValuesMaxLength).toInt() - 1

    private val minIntegerResultValue = 1
    private val minIntegerValue = 1

    private val noResultMessage = application.getString(R.string.no_result)

    init {
        _maxResult.value = creator1Manager.getResult()
        _maxValue1.value = creator1Manager.getValue1()
        _maxValue2.value = creator1Manager.getValue2()
        _maxValue3.value = creator1Manager.getValue3()


             _maxResult.observeForever { value ->    creator1Manager.setResult(value ?: "")} //i don't know what is going on, but when _maxResult1 is not nullable, goToAuction Fragment.value can't equals null, but variables are not associated
            _maxValue1.observeForever { value1 -> creator1Manager.setValue1(value1) }
            _maxValue2.observeForever { value2 -> creator1Manager.setValue2(value2) }
            _maxValue3.observeForever { value3 -> creator1Manager.setValue3(value3) }


        _goToActionFragment.value = null
    }




    fun onStartClick() {

        val result = _maxResult.value
        val value1 = _maxValue1.value
        val value2 = _maxValue2.value
        val value3 = _maxValue3.value

        Log.d(TAG, result.toString())

        val notCorrect =
            checkPropriety(result, value1, value2, value3, maxIntegerResultValue, maxIntegerValues)
        Log.d(TAG, "notCorrect Size: ${notCorrect.size}")
        if (notCorrect.size == 0) {
            Log.d(TAG, "proper")
            // go to FragmentActions
            // in this state value1 ,value2, value3 ,result are not null
            val puzzle =  PuzzleManager.getNewPuzzle(value1?.toInt()!!, value2?.toInt()!!, value3?.toInt()!!, result?.toInt()!!)
            if(puzzle == null) {
                _showToast.value = noResultMessage
            }else{
                _goToActionFragment.value = puzzle
            }

        }
        _errorText.value = notCorrect

    }

    fun onRandomClick() {
        Log.i(TAG, "onRandomClick()")
        _maxResult.value = generateRandomValue(minIntegerResultValue,maxIntegerResultValue).toString()
        _maxValue1.value = generateRandomValue(minIntegerValue,maxIntegerValues).toString()
        _maxValue2.value = generateRandomValue(minIntegerValue,maxIntegerValues).toString()
        _maxValue3.value = generateRandomValue(minIntegerValue,maxIntegerValues).toString()
    }

    fun onGoToActionFragmentFinished() {
        _goToActionFragment.value = null
    }

    /**
     * return messages of values that are not correct
     *
     * value is correct when:
     *
     * is not null
     *
     * is not empty
     *
     * has only digits
     *
     * is in defined range
     *
     */
    companion object {
        fun checkPropriety(
            maxResult: String?,
            maxValue1: String?,
            maxValue2: String?,
            maxValue3: String?,
            resultMaxRange: Int,
            valuesMaxRange: Int
        ): ArrayList<String> {
            val notCorrect = ArrayList<String>()

            if (maxResult == null || maxResult.isEmpty() || !maxResult.isDigitsOnly() || maxResult.toInt() !in 1..resultMaxRange || maxResult.length != maxResult.toInt()
                    .toString().length
            )
                notCorrect.add(MESSAGE_RESULT)

            if (maxValue1 == null || maxValue1.isEmpty() || !maxValue1.isDigitsOnly() || maxValue1.toInt() !in 1..valuesMaxRange || maxValue1.length != maxValue1.toInt()
                    .toString().length
            )
                notCorrect.add(MESSAGE_VALUE1)

            if (maxValue2 == null || maxValue2.isEmpty() || !maxValue2.isDigitsOnly() || maxValue2.toInt() !in 1..valuesMaxRange || maxValue2.length != maxValue2.toInt()
                    .toString().length
            )
                notCorrect.add(MESSAGE_VALUE2)

            if (maxValue3 == null || maxValue3.isEmpty() || !maxValue3.isDigitsOnly() || maxValue3.toInt() !in 1..valuesMaxRange || maxValue3.length != maxValue3.toInt()
                    .toString().length
            )
                notCorrect.add(MESSAGE_VALUE3)

            return notCorrect
        }

    }
}