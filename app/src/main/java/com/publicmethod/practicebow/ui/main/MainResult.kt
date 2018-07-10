package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import com.publicmethod.practicebow.Eric
import com.publicmethod.practicebow.MVCViewModel

sealed class MainResult : MVCViewModel.Result {
    data class GetEricResult(val ericOption: Option<Eric>) : MainResult()
}
