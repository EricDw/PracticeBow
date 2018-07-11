package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import com.publicmethod.data.Item
import com.publicmethod.practicebow.MVC.Result

sealed class MainResult : Result {
    data class GetItemResult(val itemOption: Option<Item>) : MainResult()
    data class GetItemsResult(val itemOption: Option<List<Item>>) : MainResult()
}
