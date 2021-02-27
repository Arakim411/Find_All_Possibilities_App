package com.applications.all_possibilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.applications.all_possibilities.databinding.ActivityMainBinding
import com.applications.all_possibilities.viewModels.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //get viewModel
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

    }
}


