package com.applications.all_possibilities.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.applications.all_possibilities.R
import com.applications.all_possibilities.databinding.FragmentPuzzleCreator2Binding
import com.applications.all_possibilities.interfaces.FragmentCreatePuzzleViewPagerEvents
import com.applications.all_possibilities.viewModels.FragmentPuzzleCreator2ViewModel
import com.applications.all_possibilities.viewModels.MESSAGE_COUNT
import com.applications.all_possibilities.viewModels.MESSAGE_TIME
import kotlinx.android.synthetic.main.activity_main.*


class FragmentPuzzleCreator2 : Fragment() {
    private lateinit var binding: FragmentPuzzleCreator2Binding
    private lateinit var viewModel: FragmentPuzzleCreator2ViewModel

    private var navigationEvents: FragmentCreatePuzzleViewPagerEvents? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_puzzle_creator2,container,false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(FragmentPuzzleCreator2ViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.startClick.observe(viewLifecycleOwner){ inputData ->
                navigationEvents?.navigateToFragmentAction2(inputData.time,inputData.count)
        }

        viewModel.errorText.observe(viewLifecycleOwner){ notCorrect ->
            binding.time.isErrorEnabled = false
            binding.count.isErrorEnabled = false

            notCorrect.forEach {
                when(it){
                    MESSAGE_TIME -> {binding.time.error = " "}
                    MESSAGE_COUNT -> {binding.count.error = " "}
                }
            }
        }
        
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when {
            context is FragmentCreatePuzzleViewPagerEvents -> {
                navigationEvents = context
            }
            childFragmentManager is FragmentCreatePuzzleViewPagerEvents -> {
                navigationEvents = childFragmentManager as FragmentCreatePuzzleViewPagerEvents
            }
            parentFragment is FragmentCreatePuzzleViewPagerEvents -> {
                navigationEvents = parentFragment as FragmentCreatePuzzleViewPagerEvents
            }
            fragment is FragmentCreatePuzzleViewPagerEvents -> {
                navigationEvents = fragment as FragmentCreatePuzzleViewPagerEvents
            }
        }
    }

}