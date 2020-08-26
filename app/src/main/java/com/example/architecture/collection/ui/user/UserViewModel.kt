package com.example.architecture.collection.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.alias.BooleanLiveData
import com.example.core.alias.BooleanMutableLiveData
import com.example.domain.core.viewmodel.ComponentViewModel
import com.example.domain.feature.LoadUserByName

class UserViewModel @ViewModelInject constructor(
    private val loadUserByName: LoadUserByName,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ComponentViewModel() {

    private val _progress: BooleanMutableLiveData by lazy {
        BooleanMutableLiveData(
            false
        )
    }
    val progress: BooleanLiveData by lazy { _progress }

    private val _query = MutableLiveData<String>()

    fun loadUserByName() = loadUserByName(LoadUserByName.Params(_query))

    fun searchByUserName(query: String) {
        _query.value = query
    }
}