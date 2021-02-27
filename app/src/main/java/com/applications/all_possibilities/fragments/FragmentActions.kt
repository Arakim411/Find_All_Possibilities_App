package com.applications.all_possibilities.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.applications.all_possibilities.R
import com.applications.all_possibilities.adapters.AnswersRecyclerViewAdapter
import com.applications.all_possibilities.adapters.SignSpinnerAdapter
import com.applications.all_possibilities.databinding.FragmentActionsBinding
import com.applications.all_possibilities.viewModels.FragmentActionViewModel
import com.applications.all_possibilities.viewModels.viewModelFactorys.FragmentActionViewModelFactory

private const val TAG = "FragmentAction"

class FragmentActions : Fragment() {
    private lateinit var binding: FragmentActionsBinding
    private lateinit var viewModel: FragmentActionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_actions,container,false)
        binding.lifecycleOwner = this
        val timer = binding.timer


        val args: FragmentActionsArgs by navArgs()
        val puzzle = args.Puzzle
        puzzle.info(TAG)
        val viewModelFactory = FragmentActionViewModelFactory(context?.applicationContext as Application,puzzle)
        viewModel = ViewModelProvider(this,viewModelFactory).get(FragmentActionViewModel::class.java)
        binding.viewModel = viewModel
        timer.onChronometerTickListener = viewModel.getOnChronometerTickListener()



        val spinnerAdapter = SignSpinnerAdapter(viewModel.signSpinnerItems)
        binding.spinnerSign1.adapter = spinnerAdapter
        binding.spinnerSign2.adapter = spinnerAdapter

        binding.spinnerSign1.onItemSelectedListener = viewModel.getOnItemSelectedListener(FragmentActionViewModel.SpinnerNumber.ONE)
        binding.spinnerSign2.onItemSelectedListener = viewModel.getOnItemSelectedListener(FragmentActionViewModel.SpinnerNumber.TWO)

        viewModel.isResultProper.observe(viewLifecycleOwner){ value ->
            if(value){
                binding.result.background = viewModel.frame
            }else{
                binding.result.background = viewModel.frameError
            }
        }

        viewModel.left.observe(viewLifecycleOwner){ left ->
          binding.left.text = getString(R.string.left,left)
        }

     viewModel.timerSetTime.observe(viewLifecycleOwner){ newTime ->
         timer.base = newTime
     }

        viewModel.isTimerCounting.observe(viewLifecycleOwner){ value ->
            if(value)
                timer.start()
            else
                timer.stop()
        }

        val adapter = AnswersRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.userAnswers.observe(viewLifecycleOwner){ userAnswers ->
          adapter.setAnswers(userAnswers)
        }

        viewModel.toast.observe(viewLifecycleOwner){ message ->
            if(message.isNotEmpty()){
                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                viewModel.onToastCompleted()
            }
        }

        viewModel.puzzleEnd.observe(viewLifecycleOwner){ value ->
            if(value){
                binding.puzzleFinished.visibility = View.VISIBLE
                binding.puzzleItemsLayout.visibility = View.INVISIBLE
            }else{
                binding.puzzleFinished.visibility = View.GONE
                binding.puzzleItemsLayout.visibility = View.VISIBLE
            }
        }

        viewModel.goToHomeFragment.observe(viewLifecycleOwner){ value ->

            if(value){
                findNavController().navigate(R.id.action_fragmentActions_to_fragmentCreatePuzzleViewPager)
            }
        }

        viewModel.maxResult.observe(viewLifecycleOwner){ maxResult ->
            binding.maxResult.text = getString(R.string.maxResult,maxResult)
        }


        return binding.root
    }


}