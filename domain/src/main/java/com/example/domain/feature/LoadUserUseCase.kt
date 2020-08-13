package com.example.domain.feature

import androidx.paging.PagingData
import com.example.core.interactor.FlowCase
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import javax.inject.Inject

class LoadUserUseCase @Inject constructor(
    private val repository: UserRepository
): FlowCase<PagingData<UserEntity>>() {

    override fun run() = repository.loadUsers()
}