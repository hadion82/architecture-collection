package com.example.core.extensions

import androidx.lifecycle.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.core.list.Submitter

inline fun <T, reified VH : RecyclerView.ViewHolder> LiveData<List<T>>.sync(
    owner: LifecycleOwner,
    recyclerView: RecyclerView,
    adapter: ListAdapter<T, VH>
): Submitter<T> {
    if (recyclerView.adapter == null)
        recyclerView.adapter = adapter
    return this.sync(owner, adapter)
}

inline fun <T, reified VH : RecyclerView.ViewHolder> LiveData<List<T>>.sync(
    owner: LifecycleOwner,
    viewPager2: ViewPager2,
    adapter: ListAdapter<T, VH>
): Submitter<T> {
    if (viewPager2.adapter == null)
        viewPager2.adapter = adapter
    return this.sync(owner, adapter)
}

inline fun <T, reified VH : RecyclerView.ViewHolder> LiveData<List<T>>.sync(
    owner: LifecycleOwner,
    adapter: ListAdapter<T, VH>
): Submitter<T> {
    val submitter = Submitter<T>()
    this.observe(owner, Observer {
        submitter.list = it
        var list = submitter.beforeSubmission(it)
        list = submitter.comparator?.run {
            list.sortedWith(this)
        } ?: list
        adapter.submitList(list) {
            submitter.afterSubmission(list)
        }
    })
    return submitter
}

fun <A, B, Result> LiveData<A>.combine(
    other: LiveData<B>,
    combiner: (A, B) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other.value
        if (b != null) {
            result.postValue(combiner(a, b))
        }
    }
    result.addSource(other) { b ->
        val a = this@combine.value
        if (a != null) {
            result.postValue(combiner(a, b))
        }
    }
    return result
}

fun <A, B, C, Result> LiveData<A>.combine2(
    other1: LiveData<B>,
    other2: LiveData<C>,
    combiner: (A?, B?, C?) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other1.value
        val c = other2.value

        result.postValue(combiner(a, b, c))

    }
    result.addSource(other1) { b ->
        val a = this@combine2.value
        val c = other2.value

        result.postValue(combiner(a, b, c))

    }
    result.addSource(other2) { c ->
        val a = this@combine2.value
        val b = other1.value

        result.postValue(combiner(a, b, c))

    }
    return result
}


fun <A, B, C, Result> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    combiner: (A, B, C) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other1.value
        val c = other2.value
        if (b != null && c != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    result.addSource(other1) { b ->
        val a = this@combine.value
        val c = other2.value
        if (a != null && c != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    result.addSource(other2) { c ->
        val a = this@combine.value
        val b = other1.value
        if (a != null && b != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    return result
}