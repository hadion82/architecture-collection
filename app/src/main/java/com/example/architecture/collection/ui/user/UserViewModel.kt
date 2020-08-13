package com.example.architecture.collection.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.functional.complete
import com.example.domain.core.viewmodel.ComponentViewModel
import com.example.domain.feature.InsertUserUseCase
import com.example.domain.feature.LoadUserUseCase

class UserViewModel @ViewModelInject constructor(
    private val insertUser: InsertUserUseCase,
    private val loadUser: LoadUserUseCase
) : ComponentViewModel() {

    init {
        insertUser(
            InsertUserUseCase.Params(70),
            viewModelScope
        ) {
            it.complete({},{})
        }
    }

    fun loadUsers() = loadUser().cachedIn(viewModelScope)

}