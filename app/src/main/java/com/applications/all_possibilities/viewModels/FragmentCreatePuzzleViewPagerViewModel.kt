package com.applications.all_possibilities.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applications.all_possibilities.Data.ViewPagerItem
import com.applications.all_possibilities.fragments.FragmentPuzzleCreator1
import com.applications.all_possibilities.fragments.FragmentPuzzleCreator2

class FragmentCreatePuzzleViewPagerViewModel : ViewModel() {


    var viewPagerItems = arrayListOf(
        ViewPagerItem(FragmentPuzzleCreator1(), "Puzzle1"),
        ViewPagerItem(FragmentPuzzleCreator2(), "Puzzle2")
    )


    private val _currentItem = MutableLiveData<Int>()
    val currentItem: LiveData<Int>
        get() = _currentItem


}