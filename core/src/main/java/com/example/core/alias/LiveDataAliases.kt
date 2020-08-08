package com.example.core.alias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kiwiplus.app.core.functional.Event

/**
 * LiveData Aliases
 */
@SinceKotlin("1.1") public typealias IntLiveData = LiveData<Int>

@SinceKotlin("1.1") public typealias StringLiveData = LiveData<String>

@SinceKotlin("1.1") public typealias LongLiveData = LiveData<Long>

@SinceKotlin("1.1") public typealias DoubleLiveData = LiveData<Double>

@SinceKotlin("1.1") public typealias FloatLiveData = LiveData<Float>

@SinceKotlin("1.1") public typealias BooleanLiveData = LiveData<Boolean>

@SinceKotlin("1.1") public typealias EventLiveData<T> = LiveData<Event<T>>


/**
 * MutableLiveData Aliases
 */
@SinceKotlin("1.1") public typealias IntMutableLiveData = MutableLiveData<Int>

@SinceKotlin("1.1") public typealias StringMutableLiveData = MutableLiveData<String>

@SinceKotlin("1.1") public typealias LongMutableLiveData = MutableLiveData<Long>

@SinceKotlin("1.1") public typealias DoubleMutableLiveData = MutableLiveData<Double>

@SinceKotlin("1.1") public typealias FloatMutableLiveData = MutableLiveData<Float>

@SinceKotlin("1.1") public typealias BooleanMutableLiveData = MutableLiveData<Boolean>

@SinceKotlin("1.1") public typealias MutableEventLiveData<T> = MutableLiveData<Event<T>>