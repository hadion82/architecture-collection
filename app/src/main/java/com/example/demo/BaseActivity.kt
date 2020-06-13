package com.example.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.Field
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<VDB : ViewDataBinding>: DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout())
        binding.lifecycleOwner = this
        bindViewModel(binding)
        onBind(savedInstanceState, binding)
    }

    private fun <VM: ViewModel> viewModel(_class: Class<VM>): VM =
        ViewModelProvider(this, factory).get(_class)

    @Suppress("UNCHECKED_CAST")
    private fun <B : Any> bindViewModel(t: B): List<Field> {
        val fieldList = ArrayList<Field>()
        var targetClass: Class<*>? = t::class.java
        while (targetClass != null) {
            targetClass.declaredFields.forEach {
                if (it.getAnnotation(Bindable::class.java) != null &&
                    ViewModel::class.java.isAssignableFrom(it.type)
                ) {
                    it.isAccessible = true
                    it.set(binding, viewModel(it.type as Class<out ViewModel>))
                }
            }
            targetClass = targetClass.superclass
        }
        return fieldList
    }

    inline fun <T, reified VH : RecyclerView.ViewHolder> LiveData<List<T>>.sync(
        recyclerView: RecyclerView,
        adapter: ListAdapter<T, VH>
    ): Mediator<T> {
        if(recyclerView.adapter == null)
            recyclerView.adapter = adapter
        return this.sync(adapter)
    }

    inline fun <T, reified VH : RecyclerView.ViewHolder> LiveData<List<T>>
            .sync(adapter: ListAdapter<T, VH>): Mediator<T> {
        val mediator = Mediator<T>()
        this.observe(this@BaseActivity, Observer {
            mediator.list = it
            var list = mediator.before(it)
            list = mediator.comparator?.run {
                list.sortedWith(this)
            } ?: list
            adapter.submitList(list) {
                mediator.after(list)
            }
        })
        return mediator
    }

    fun <T> LiveData<T>.collect(collect: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer { collect(it) })
    }

    open fun onBind(savedInstanceState: Bundle?, binding: VDB) {}

    abstract fun layout(): Int
}