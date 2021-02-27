package com.applications.all_possibilities.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.applications.all_possibilities.R
import com.applications.all_possibilities.databinding.FragmentPuzzleCreator1Binding
import com.applications.all_possibilities.interfaces.FragmentCreatePuzzleViewPagerEvents
import com.applications.all_possibilities.viewModels.*
import kotlinx.android.synthetic.main.activity_main.*


class FragmentPuzzleCreator1 : Fragment() {
    private lateinit var binding: FragmentPuzzleCreator1Binding
    private lateinit var mViewModel: FragmentPuzzleCreator1ViewModel
    private var navigationEvents: FragmentCreatePuzzleViewPagerEvents? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_puzzle_creator1, container, false)
        binding.lifecycleOwner = this

        mViewModel = ViewModelProvider(this).get(FragmentPuzzleCreator1ViewModel::class.java)
        binding.viewModel = mViewModel
        // observers
        mViewModel.goToActionFragment.observe(viewLifecycleOwner) { puzzle ->
            if (puzzle != null) {
                navigationEvents?.navigateToFragmentAction(puzzle)
               mViewModel.onGoToActionFragmentFinished()
            }
        }

        mViewModel.showToast.observe(viewLifecycleOwner){ message ->
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

        mViewModel.errorText.observe(viewLifecycleOwner) { notCorrect ->

            binding.maxResult.isErrorEnabled = false
            binding.maxValue1.isErrorEnabled = false
            binding.maxValue2.isErrorEnabled = false
            binding.maxValue3.isErrorEnabled = false

            Log.d("puzzle_creator_1", notCorrect.toString())

            notCorrect.forEach {
                when (it) {
                    MESSAGE_RESULT -> {
                        binding.maxResult.error = " "
                    }
                    MESSAGE_VALUE1 -> {
                        binding.maxValue1.error = " "
                    }
                    MESSAGE_VALUE2 -> {
                        binding.maxValue2.error = " "
                    }
                    MESSAGE_VALUE3 -> {
                        binding.maxValue3.error = " "
                    }
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