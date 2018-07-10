package com.publicmethod.practicebow.ui.main

import arrow.Kind
import arrow.core.ForId
import arrow.core.Option
import com.publicmethod.practicebow.Eric
import com.publicmethod.practicebow.MVCViewModel

sealed class MainResult : MVCViewModel.Result {
    data class GetEricResult(val ericOption: Kind<ForId, Option<Eric>>) : MainResult()
}
