package com.applications.all_possibilities.viewModels

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.all_possibilities.Data.Answer
import com.applications.all_possibilities.Data.Puzzle
import com.applications.all_possibilities.PuzzleManager
import com.applications.all_possibilities.R
import com.applications.all_possibilities.SIGN
import com.applications.all_possibilities.generateRandomValue
import java.lang.Exception

private const val TAG =  "FragmentAction2ViewModel"
private const val ONE_SECOND = 1000L

class FragmentActions2ViewModel(application: Application, val time: Long, val count: Int) :
    AndroidViewModel(application)  {

    //arrays must be associated
    val signSpinnerItems = arrayListOf("+", "-", "*", "/")
    val signs = arrayListOf(SIGN.PLUS, SIGN.MINUS, SIGN.Multiplication, SIGN.DIVISION)


    val frame = application.resources.getDrawable(R.drawable.frame)
    val frameError = application.resources.getDrawable(R.drawable.frame_error)


    private var sign1: SIGN = signs[0]
    private var sign2: SIGN = signs[0]

    private val puzzle = MutableLiveData<Puzzle>()

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

    private val _resultsToFind = MutableLiveData<Int>()
    val resultsToFind: LiveData<Int>
        get() = _resultsToFind

    private val _resetSigns = MutableLiveData<Boolean>()
    val resetSigns: LiveData<Boolean>
    get() = _resetSigns

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime

    private val _resultError = MutableLiveData<Boolean>()
    val resultError: LiveData<Boolean>
    get() = _resultError

    private val _showGoodAnswerAnim = MutableLiveData<Boolean>()
    val showGoodAnswerAnim: LiveData<Boolean>
        get() = _showGoodAnswerAnim

    private val _showOnBadAnswerAnim = MutableLiveData<Boolean>()
    val showOnBadAnswerAnim: LiveData<Boolean>
        get() = _showOnBadAnswerAnim

    private val _goToFragmentCreatePuzzleViewPager = MutableLiveData<Boolean>()
    val goToFragmentCreatePuzzleViewPager: LiveData<Boolean>
        get() = _goToFragmentCreatePuzzleViewPager

    private val _timeUP = MutableLiveData<Boolean>()
    val timeUP: LiveData<Boolean>
        get() = _timeUP

    private  val timer = object : CountDownTimer((time+1000), ONE_SECOND){
        override fun onTick(p0: Long) {
                _currentTime.value = p0
        }

        override fun onFinish() {
        timeEnd()
        }

    }

    init {
        Log.i(TAG,"FragmentAction2VieModel: Init")
        _resultsToFind.value = count
        _resultError.value = false

        puzzle.observeForever { newPuzzle ->
            _value1.value = newPuzzle.value1.toString()
            _value2.value = newPuzzle.value2.toString()
            _value3.value = newPuzzle.value3.toString()
        }

        result.observeForever { _resultError.value = false }
        timer.start()
        loadNewPuzzle()
    }

    fun getOnItemSelectedListener(number: FragmentActionViewModel.SpinnerNumber): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                try {
                    when (number) {
                        FragmentActionViewModel.SpinnerNumber.ONE -> {
                            sign1 = signs[position]
                            Log.i(TAG, "sign1 = $sign1")
                        }
                        FragmentActionViewModel.SpinnerNumber.TWO -> {
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

    fun goToFragmentCreatePuzzleViewPager(){
        _goToFragmentCreatePuzzleViewPager.value = true
    }

    fun onShowGoodAnswerAnimComplete(){
        _showGoodAnswerAnim.value = false
    }
    fun onShowBadAnswerAnimComplete(){
        _showOnBadAnswerAnim.value = false
    }

    private fun restTimer(){
        timer.cancel()
        timer.start()
    }

    fun checkClicked() {
        Log.d(TAG,"CheckClicked")
        if (!isResultSyntaxProper(result.value)) {
            Log.d(TAG,"Result syntac not proper")
            _resultError.value = true
            return
        }

        val currentPuzzle = puzzle.value!!
        val resultInt = result.value?.toInt()!!
        val answer = Answer(resultInt, sign1, sign2)

        if (currentPuzzle.goodAnswers.contains(answer)) {
            currentPuzzle.goodAnswers.remove(answer)
            onGoodAnswer()
        }
        else
            onBadAnswer()
    }

    fun timeEnd(){
        _timeUP.value = true
    }


  private  fun next(){
      Log.d(TAG,"next()")
        //when user solve puzzle
        loadNewPuzzle()
        restTimer()
        _resultsToFind.value = count
        _resetSigns.value = true

    }

   private fun onGoodAnswer(){
       Log.d(TAG,"onGoodAnswer()")
      _resultsToFind.value =  _resultsToFind.value?.minus(1)
       Log.d(TAG,"${resultsToFind.value}")

        if(resultsToFind.value!! <= 0){
            next()
        }else{
            _showGoodAnswerAnim.value = true
        }
    }

   private fun onBadAnswer(){
        _showOnBadAnswerAnim.value = true
    }

   private fun loadNewPuzzle(){
       Log.d(TAG,"loadNewPuzzle()")
        puzzle.value = getNewPuzzle(count)
        result.value = ""
    }

    fun onResetSignsComplete(){
        _resetSigns.value = false
    }

  private  fun getNewPuzzle(minPossibilities: Int): Puzzle{
        var goodAnswerCount = 0
        var puzzle: Puzzle? = null

        while (goodAnswerCount < minPossibilities || puzzle == null){
            puzzle = PuzzleManager.getNewPuzzle(generateRandomValue(1,99), generateRandomValue(1,99),
                generateRandomValue(1,99))
            goodAnswerCount = puzzle?.goodAnswers?.size ?: 0
        }
      puzzle.info(TAG)
        return  puzzle
    }


    private fun isResultSyntaxProper(result: String?):Boolean =
        (result != null && result.isNotEmpty() && result.isDigitsOnly() && result.length == result.toInt()
            .toString().length)

}