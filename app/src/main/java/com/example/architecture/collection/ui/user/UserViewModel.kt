package com.example.architecture.collection.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.domain.core.viewmodel.ComponentViewModel
import com.example.domain.feature.LoadUserByName

class UserViewModel @ViewModelInject constructor(
    private val loadUserByName: LoadUserByName,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ComponentViewModel() {

    private val _loading = MutableLiveData<Boolean>(
        false
    )
    val loading: LiveData<Boolean> get() = _loading

    fun loadUserByName() = loadUserByName(
        LoadUserByName.Params(
            savedStateHandle.getLiveData(QUERY)
        )
    )

    fun searchByUserName(query: String) {
        savedStateHandle.set(QUERY, query)
    }

    companion object {
        const val QUERY = "query"
    }
}