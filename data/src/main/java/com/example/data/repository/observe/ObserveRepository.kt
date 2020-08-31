package com.example.data.repository.observe

import androidx.lifecycle.LiveData
import com.example.core.functional.FlowResult
import com.example.data.core.Failure
import com.example.data.entity.UserEntity

interface ObserveRepository {

    fun observeUsers(query: String): LiveData<FlowResult<List<UserEntity>, Failure>>
}