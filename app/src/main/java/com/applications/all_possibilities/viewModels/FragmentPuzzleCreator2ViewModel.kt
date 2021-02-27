package com.applications.all_possibilities.viewModels

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.all_possibilities.Data.PuzzleCreator2InputData
import com.applications.all_possibilities.SharedPreferencesManager

const val MESSAGE_TIME ="MESSAGE_TIME"
const val MESSAGE_COUNT = "MESSAGE_COUNT"

class FragmentPuzzleCreator2ViewModel(application: Application): AndroidViewModel(application) {
  private  val manager = SharedPreferencesManager.getPuzzleCreator2Manager(application)

    private  val _startClick = MutableLiveData<PuzzleCreator2InputData>()
    val startClick: LiveData<PuzzleCreator2InputData>
    get() = _startClick

     val time = MutableLiveData<String>()
     val count = MutableLiveData<String>()

    private val _errorText = MutableLiveData<ArrayList<String>>()
    val errorText: LiveData<ArrayList<String>>
    get() = _errorText

    private val minTime = 1 // seconds
    private val minCount = 1
    private val maxCount = 20


    init {
        time.value = manager.getTime()
        count.value = manager.getCount()

        time.observeForever { newTime -> manager.setTime(newTime)}
        count.observeForever { newCount -> manager.setCount(newCount) }
    }

    fun onStartClick(){
       val time = time.value
        val count = count.value

        val notCorrect =  checkPropriety(time,count,minTime,minCount,maxCount)
        _errorText.value = notCorrect

        if(notCorrect.size == 0){
            val timeInMilliseconds = time?.toLong()!! * 1000
            _startClick.value = PuzzleCreator2InputData(timeInMilliseconds, count?.toInt()!!)
        }

    }

    companion object {
        fun checkPropriety(time: String?, count: String?, minTime: Int, minCount: Int, maxCount:Int): ArrayList<String> {
            val notCorrect = ArrayList<String>()

            if (time == null || time.isEmpty() || !time.isDigitsOnly() || time.length != time.toLong()
                    .toString().length || time.toInt() < minTime
            )
                notCorrect.add(MESSAGE_TIME)

            if (count == null || count.isEmpty() || !count.isDigitsOnly() || count.length != count.toLong()
                    .toString().length || count.toInt() !in (minCount..maxCount)
            )
                notCorrect.add(MESSAGE_COUNT)

            return notCorrect
        }
    }
}