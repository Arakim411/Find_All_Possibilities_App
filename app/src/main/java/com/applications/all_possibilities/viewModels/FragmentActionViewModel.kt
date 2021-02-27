package com.applications.all_possibilities.viewModels

import android.app.Application
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Chronometer
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.all_possibilities.Data.Answer
import com.applications.all_possibilities.Data.AnswersRecyclerViewItem
import com.applications.all_possibilities.Data.Puzzle
import com.applications.all_possibilities.PuzzleManager
import com.applications.all_possibilities.R
import com.applications.all_possibilities.SIGN
import java.lang.Exception
import kotlin.collections.ArrayList

private const val TAG = "FragmentActionViewModel"

class FragmentActionViewModel(application: Application, val puzzle: Puzzle) :
    AndroidViewModel(application) {

    //arrays must be associated
    val signSpinnerItems = arrayListOf("+", "-", "*", "/")
    val signs = arrayListOf(SIGN.PLUS, SIGN.MINUS, SIGN.Multiplication, SIGN.DIVISION)

    val frame = application.resources.getDrawable(R.drawable.frame)
    val frameError = application.resources.getDrawable(R.drawable.frame_error)


    private var timerCurrentTime: Long = 0

    private var sign1: SIGN = signs[0]
    private var sign2: SIGN = signs[0]

    private val goodAnswers = puzzle.goodAnswers

    // MutableLiveData
    val result = MutableLiveData<String>()

    private val _value1 = MutableLiveData<String>()
    val value1: LiveData<String>
        get() = _value1

    private val _value2 = MutableLiveData<String>()
    val value2: LiveData<String>
        get() = _value2

    private val _value3 = MutableLiveData<String>()
    val value3: LiveData<String>
        get() = _value3

    private val _isResultSyntaxProper = MutableLiveData<Boolean>()
    val isResultProper: LiveData<Boolean>
        get() = _isResultSyntaxProper

    private val _left = MutableLiveData<Int>()
    val left: LiveData<Int>
        get() = _left

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
    get() = _toast

    private val _puzzleEnd = MutableLiveData<Boolean>()
    val puzzleEnd: LiveData<Boolean>
    get() = _puzzleEnd

    private val _goToHomeFragment = MutableLiveData<Boolean>()
    val goToHomeFragment: LiveData<Boolean>
    get() = _goToHomeFragment

    private val _maxResult = MutableLiveData<Int>()
    val maxResult: LiveData<Int>
    get() = _maxResult

    // timer MutableLiveData
    private val _timerSetTime = MutableLiveData<Long>()
    val timerSetTime: LiveData<Long>
        get() = _timerSetTime

    private val _isTimerCounting = MutableLiveData<Boolean>()
    val isTimerCounting: LiveData<Boolean>
        get() = _isTimerCounting



    private var userAnswerList = ArrayList<AnswersRecyclerViewItem>()

    //RecyclerViewLiveData
    private val _userAnswers = MutableLiveData<ArrayList<AnswersRecyclerViewItem>>()
    val userAnswers: LiveData<ArrayList<AnswersRecyclerViewItem>>
    get() = _userAnswers

    init {
        result.observeForever { _isResultSyntaxProper.value = true }
        _value1.value = puzzle.value1.toString()
        _value2.value = puzzle.value2.toString()
        _value3.value = puzzle.value3.toString()
        _left.value = goodAnswers.size
        _maxResult.value = puzzle.maxResult

        _timerSetTime.value = SystemClock.elapsedRealtime()
        _isTimerCounting.value = true
        _userAnswers.value = ArrayList()
    }

   private fun finishPuzzle(){
        _puzzleEnd.value = true
       _isTimerCounting.value = false
    }

     fun onToastCompleted(){
        _toast.value = ""
    }

    fun goToHomeFragment(){
        _goToHomeFragment.value = true
    }

    fun onCheckClick() {
        val resultValue = result.value
        val isGoodAnswer: Boolean

        if (!isResultSyntaxProper(resultValue)) {
            Log.d(TAG, "syntax is not proper")
            _isResultSyntaxProper.value = false
            return
        }
            if(_puzzleEnd.value == true)
                return

            Log.d(TAG, "syntax is  proper")
            _isResultSyntaxProper.value = true
            val answer = Answer(resultValue?.toInt()!!, sign1, sign2)

            if (goodAnswers.contains(answer)) {
                Log.d(TAG, "good answer")
                _left.value = _left.value?.minus(1)
                isGoodAnswer = true
                goodAnswers.remove(answer)
                result.value = ""

                if(_left.value!! <= 0){
                    finishPuzzle()
                }

            } else {
                Log.d(TAG, "bad answer or answer repeated")
                isGoodAnswer = false
            }

            val userAnswer = AnswersRecyclerViewItem().apply {
                value1 = puzzle.value1.toString()
                value2 = puzzle.value2.toString()
                value3 = puzzle.value3.toString()
                result = resultValue.toString()
                sign1String = PuzzleManager.getSignString(sign1)
                sign2String =  PuzzleManager.getSignString(sign2)
                this.isGoodAnswer = isGoodAnswer
            }
        if(!userAnswerList.contains(userAnswer)) {
            // add UserAnswer to RecyclerView
            userAnswerList.add(userAnswer)
            _userAnswers.value = userAnswerList
        }else{
                _toast.value = "you have already given such a combination once"
        }

    }

    fun onClearClick() {
        result.value = ""
    }

    private fun isResultSyntaxProper(result: String?):Boolean =
        (result != null && result.isNotEmpty() && result.isDigitsOnly() && result.length == result.toInt()
            .toString().length)

    fun getOnItemSelectedListener(number: SpinnerNumber): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                try {
                    when (number) {
                        SpinnerNumber.ONE -> {
                            sign1 = signs[position]
                            Log.i(TAG, "sign1 = $sign1")
                        }
                        SpinnerNumber.TWO -> {
                            sign2 = signs[position]
                            Log.i(TAG, "sign1 = $sign2")
                        }
                    }

                } catch (e: Exception) {
                    throw error("$signSpinnerItems and \n $signs \n must be associated ")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    fun getOnChronometerTickListener(): Chronometer.OnChronometerTickListener =
        Chronometer.OnChronometerTickListener { timer ->
            if (timer != null)
                timerCurrentTime = timer.base
        }

    enum class SpinnerNumber {
        ONE,
        TWO
    }
}