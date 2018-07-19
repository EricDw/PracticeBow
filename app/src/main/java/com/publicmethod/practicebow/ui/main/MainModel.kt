package com.publicmethod.practicebow.ui.main

import arrow.core.None
import arrow.core.Option
import com.publicmethod.data.Item
import com.publicmethod.data.Items
import com.publicmethod.practicebow.MVC.Model

data class MainModel(val item: Option<Item> = None,
                     val items: Option<Items> = None,
                     val errorMessage: Option<ErrorMessage> = None,
                     val currentLoadItemClickAmount: LoadItemClickAmount = "0",
                     val currentLoadItemsClickAmount: LoadItemsClickAmount = "0") : Model