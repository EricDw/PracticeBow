package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import arrow.core.toOption
import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.ReaderFun
import arrow.data.flatMap
import com.publicmethod.practicebow.Eric
import com.publicmethod.practicebow.MVCViewModel

sealed class MainAction : MVCViewModel.Action {
    data class GetEricAction(
            val reader: Reader<GetEricDependencies, Option<Eric>>,
            val ericDependencies: GetEricDependencies) : MainAction()
}

