package com.applications.all_possibilities.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.applications.all_possibilities.R
import com.applications.all_possibilities.adapters.SignSpinnerAdapter
import com.applications.all_possibilities.databinding.FragmentActions2Binding
import com.applications.all_possibilities.viewModels.FragmentActionViewModel
import com.applications.all_possibilities.viewModels.FragmentActions2ViewModel
import com.applications.all_possibilities.viewModels.viewModelFactorys.FragmentActions2ViewModelFactory


class FragmentAction2 : Fragment() {
    private lateinit var binding: FragmentActions2Binding
    private lateinit var viewModel: FragmentActions2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_actions2,container,false)
        binding.lifecycleOwner = this

        val args: FragmentAction2Args by navArgs()
        val factory = FragmentActions2ViewModelFactory((context?.applicationContext as Application),args.time, args.count)
        viewModel = ViewModelProvider(this,factory).get(FragmentActions2ViewModel::class.java)
        binding.viewModel = viewModel


        val spinnerAdapter = SignSpinnerAdapter(viewModel.signSpinnerItems)
        binding.spinnerSign1.adapter = spinnerAdapter
        binding.spinnerSign2.adapter = spinnerAdapter

        binding.spinnerSign1.onItemSelectedListener = viewModel.getOnItemSelectedListener(FragmentActionViewModel.SpinnerNumber.ONE)
        binding.spinnerSign2.onItemSelectedListener = viewModel.getOnItemSelectedListener(FragmentActionViewModel.SpinnerNumber.TWO)

        viewModel.resultsToFind.observe(viewLifecycleOwner){ resultsToFind ->
                binding.resultsToFind.text = getString(R.string.results_to_find, resultsToFind)
        }

        viewModel.resetSigns.observe(viewLifecycleOwner){ value ->
            if(value){
               binding.spinnerSign1.setSelection(0)
                binding.spinnerSign2.setSelection(0)
                viewModel.onResetSignsComplete()
            }
        }

        viewModel.currentTime.observe(viewLifecycleOwner){time ->
            binding.timer.text = (time/1000).toString().format("yyyy.MM.dd HH:mm")
        }

        viewModel.resultError.observe(viewLifecycleOwner){ value ->
            if(value){
                binding.result.background = viewModel.frameError
            }else{
                binding.result.background = viewModel.frame
            }

        }

        viewModel.showGoodAnswerAnim.observe(viewLifecycleOwner){ value ->
            if(value){
                answerAnim(binding.showOnAnswer,R.drawable.ic_good_answer)
                viewModel.onShowGoodAnswerAnimComplete()
            }
        }

        viewModel.showOnBadAnswerAnim.observe(viewLifecycleOwner){ value ->
            if(value){
                answerAnim(binding.showOnAnswer,R.drawable.ic_bad_answer)
                viewModel.onShowBadAnswerAnimComplete()
            }
        }

        viewModel.timeUP.observe(viewLifecycleOwner){ value ->
            if(value){
                binding.timeUpLayout.visibility = View.VISIBLE
                binding.checkButton.isEnabled = false
                binding.puzzleItemsLayout.visibility = View.GONE
            }
        }

        viewModel.goToFragmentCreatePuzzleViewPager.observe(viewLifecycleOwner){ value ->
            if(value)
                findNavController().navigate(R.id.action_fragmentAction2_to_fragmentCreatePuzzleViewPager)

        }

        return binding.root
    }

   private fun answerAnim(imageView: ImageView, image: Int){
        imageView.setImageResource(image)
        val anim = AnimationUtils.loadAnimation(requireContext(),R.anim.answer_anim)
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                imageView.visibility = View.VISIBLE

            }

            override fun onAnimationEnd(p0: Animation?) {
                imageView.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        imageView.animation = anim
        imageView.animation.start()
    }

}

