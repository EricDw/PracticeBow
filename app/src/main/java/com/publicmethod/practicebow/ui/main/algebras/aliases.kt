package com.publicmethod.practicebow.ui.main.algebras

import arrow.core.Either
import com.publicmethod.data.Item
import com.publicmethod.data.Items
import com.publicmethod.practicebow.algerbras.ItemException

typealias EitherItems = Either<ItemException, Items>

typealias EitherItem = Either<ItemException, Item>
