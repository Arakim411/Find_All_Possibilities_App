package com.applications.all_possibilities.viewModels.viewModelFactorys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.applications.all_possibilities.Data.Puzzle
import com.applications.all_possibilities.viewModels.FragmentActionViewModel

@Suppress("UNCHECKED_CAST")
class FragmentActionViewModelFactory(val application: Application,val puzzle: Puzzle) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FragmentActionViewModel(application, puzzle) as T
    }

}