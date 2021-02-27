package com.applications.all_possibilities


//fun FragmentActivity.replaceFragment(containerId: Int, fragment: androidx.fragment.app.Fragment) =
//    supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()

//fun getRandomSign(): SIGN =
//    when ((0..3).random()) {
//        0 -> SIGN.PLUS
//        1 -> SIGN.MINUS
//        2 -> SIGN.DIVISION
//        3 -> SIGN.Multiplication
//        else -> throw  error("Unknown value variable operates on range 0-3")
//    }


fun Int.countWithSign(sign: SIGN, nextInt: Int): Int =
    when (sign) {
        SIGN.PLUS -> plus(nextInt)
        SIGN.MINUS -> minus(nextInt)
        SIGN.DIVISION -> div(nextInt)
        SIGN.Multiplication -> times(nextInt)
    }

fun generateRandomValue(min: Int, max:Int): Int = (min..max).random()

// fun isStringToIntProper(result: String?): Boolean =
//    (result != null && result.isNotEmpty() && result.isDigitsOnly() && result.length == result.toInt()
//        .toString().length)


