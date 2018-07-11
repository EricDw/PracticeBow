package com.publicmethod.data

interface DataSource<A> {
    fun saveItem(a: A)
    fun saveItems(a: List<A>)
    fun <I> retrieveItem(id: I): A?
    fun retrieveItems(): List<A>
    fun <I> isCached(id: I): Boolean
}