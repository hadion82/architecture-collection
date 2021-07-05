package com.example.core.list

class Submitter<T> {
    lateinit var list: List<T>

    var beforeSubmission: (list: List<T>) -> List<T> = { list }

    var comparator: Comparator<T>? = null

    var afterSubmission: (list: List<T>) -> Unit = { }

    fun beforeSubmission(method: (list: List<T>) -> List<T>) = apply {
        this.beforeSubmission = method
    }

    fun <R> sortSubmission(selector: (T) -> Comparable<R>) = apply {
        this.comparator = compareBy(selector)
    }

    fun afterSubmission(method: (list: List<T>) -> Unit) = apply {
        afterSubmission = method
    }
}