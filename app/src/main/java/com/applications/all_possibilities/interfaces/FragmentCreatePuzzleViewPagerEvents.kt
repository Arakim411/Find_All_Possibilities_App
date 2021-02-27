package com.applications.all_possibilities.interfaces

import com.applications.all_possibilities.Data.Puzzle

interface FragmentCreatePuzzleViewPagerEvents{
    fun navigateToFragmentAction(puzzle: Puzzle)

    /**
     * @param count  how many possibilities user must find in puzzle
     * @param time how many time user has to find possibilities
     */
    fun navigateToFragmentAction2(time: Long, count: Int)

}