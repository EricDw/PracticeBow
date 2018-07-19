package com.publicmethod.data

interface DataSource<A, I> {
    fun saveItem(a: A)
    fun saveItems(a: List<A>)
    fun retrieveItem(id: I): A?
    fun retrieveItems(): List<A>
    fun isCached(id: I): Boolean
    fun isCached(): Boolean
}