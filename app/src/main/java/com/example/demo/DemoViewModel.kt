package com.example.demo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _userIdLiveData: MutableLiveData<Long> = MutableLiveData()
    val userIdLiveData: LiveData<Long> get() = _userIdLiveData

    private val _userName: MutableLiveData<String> = MutableLiveData()
    val userName: LiveData<String> get() = _userName

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = mutableListOf<User>()
            for(i in 1 .. 10) {
                users.add(User(i.toLong(), "demo$i"))
            }
            userDataSource.insert(users)
        }
    }

    fun setId(id: Long) {
        _userIdLiveData.value = id
    }

    fun getUsers() =
        userDataSource.getUsers()

    fun getUser(id: Long) =
        userDataSource.getUser(id)

    fun findUser(id: Long?) {
        _userIdLiveData.value = id
    }

    fun setUserName(name: String?) {
        name?.run {
            _userName.value = this
        }
    }
}