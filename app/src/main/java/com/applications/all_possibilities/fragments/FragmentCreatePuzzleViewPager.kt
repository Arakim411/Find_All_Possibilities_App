package com.applications.all_possibilities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.applications.all_possibilities.Data.Puzzle
import com.applications.all_possibilities.R
import com.applications.all_possibilities.adapters.ViewPagerAdapter
import com.applications.all_possibilities.databinding.FragmentCreatePuzzleViewPagerBinding
import com.applications.all_possibilities.interfaces.FragmentCreatePuzzleViewPagerEvents
import com.applications.all_possibilities.viewModels.FragmentCreatePuzzleViewPagerViewModel


@Suppress("DEPRECATION")
class FragmentCreatePuzzleViewPager : Fragment(), FragmentCreatePuzzleViewPagerEvents {
    private lateinit var binding: FragmentCreatePuzzleViewPagerBinding
    private lateinit var viewModel: FragmentCreatePuzzleViewPagerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_puzzle_view_pager,
            container,
            false
        )
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(FragmentCreatePuzzleViewPagerViewModel::class.java)

        removeFragments()

        val viewPager = binding.viewPager
        val adapter = ViewPagerAdapter(requireFragmentManager())
        viewPager.adapter = adapter
        viewModel.viewPagerItems.forEach {
           adapter.addFragment(it)
        }

        binding.tabLayout.setupWithViewPager(viewPager)

        //observers
        viewModel.currentItem.observe(viewLifecycleOwner){ position ->

            viewPager.currentItem = position
        }

        return binding.root
    }

    override fun navigateToFragmentAction(puzzle: Puzzle) {
           val  action = FragmentCreatePuzzleViewPagerDirections.actionFragmentCreatePuzzleViewPagerToFragmentActions(Puzzle = puzzle)
            removeFragments()
            findNavController().navigate(action)

    }

    override fun navigateToFragmentAction2(time: Long, count: Int) {
        val action = FragmentCreatePuzzleViewPagerDirections.actionFragmentCreatePuzzleViewPagerToFragmentAction2(time = time, count = count)
        removeFragments()
        findNavController().navigate(action)
    }

    private  fun removeFragments(){
        // there is bug with nested fragments, when user click back he won't see FragmentCreator1,FragmentCreator2 in ViewPager, so i must remove all fragments
        //and they will be added again, user won't lose data because it is keeping in sharedPreferences

        viewModel.viewPagerItems.forEach {
            fragmentManager?.beginTransaction()?.remove(it.fragment)?.commit()
        }
    }

}

