package com.applications.all_possibilities.viewModels.viewModelFactorys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.applications.all_possibilities.viewModels.FragmentActions2ViewModel


@Suppress("UNCHECKED_CAST")
    class FragmentActions2ViewModelFactory(val application: Application,val time: Long, val count: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FragmentActions2ViewModel(application, time, count) as T
        }
}