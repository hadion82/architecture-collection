package com.example.architecture

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.UserRepository
import com.example.domain.core.viewmodel.ComponentViewModel

class DemoViewModel @ViewModelInject constructor(
    private val repository: UserRepository
):ComponentViewModel() {
    private val _userIdLiveData: MutableLiveData<Long> = MutableLiveData()
    val userIdLiveData: LiveData<Long> get() = _userIdLiveData

    private val _userName: MutableLiveData<String> = MutableLiveData()
    val userName: LiveData<String> get() = _userName
}