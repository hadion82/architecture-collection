package com.example.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E): Boolean = !isClosedForSend && try {
    offer(value)
} catch (e: CancellationException) {
    false
}

@ExperimentalCoroutinesApi
fun View.clicks(): Flow<View> {
    return callbackFlow {
        setOnClickListener { safeOffer(it) }
        awaitClose { setOnClickListener(null) }
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> =
    callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                safeOffer(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }


@ExperimentalCoroutinesApi
@CheckResult
fun SearchView.textChanges(): Flow<CharSequence?> =
    callbackFlow<CharSequence?> {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean = safeOffer(query)
        }
        isSubmitButtonEnabled = true
        setOnQueryTextListener(listener)
        awaitClose { setOnQueryTextListener(null) }
    }