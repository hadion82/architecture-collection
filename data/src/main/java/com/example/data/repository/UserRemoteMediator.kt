package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.datasource.local.UserLocalDataSource
import com.example.data.datasource.local.query.QueryLocalDataSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.entity.QueryEntity
import com.example.data.entity.UserEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class UserRemoteMediator @Inject constructor(
    private val queryDataSource: QueryLocalDataSource,
    private val userDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    private val query: String,
    private val refresh: Boolean
) : RemoteMediator<Int, UserEntity>() {

    private var page: Int = queryDataSource.getPage(query) ?: START_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        try {
            val queryPage = when (loadType) {
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND ->
                    if (page != END_PAGE) ++page else
                        return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.REFRESH ->
                    if (refresh) START_PAGE else
                        return MediatorResult.Success(endOfPaginationReached = page == END_PAGE)
            }

            val response = remoteDataSource.searchUser(query, queryPage)

            response.run {
                if (isSuccessful) {
                    val isLastPage = headers()[HEADER_PAGE_KEY]?.contains(HEADER_PAGE_LAST) ?: false

                    body()?.let { response ->

                        val isRefresh = loadType == LoadType.REFRESH && refresh
                        if (isRefresh) {
                            userDataSource.deleteByQuery(query)
                        }
                        userDataSource.insert(response.items.map { user ->
                            user.apply { this.query = this@UserRemoteMediator.query }
                        })

                        queryDataSource.insert(
                            QueryEntity(
                                query, when {
                                    isLastPage -> END_PAGE
                                    isRefresh -> START_PAGE + 1
                                    else -> page
                                }
                            )
                        )
                    }
                    if (isLastPage)
                        return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            return MediatorResult.Success(
                endOfPaginationReached = response.body() == null
            )
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        const val START_PAGE = 1
        const val END_PAGE = -1

        const val HEADER_PAGE_KEY = "link"
        const val HEADER_PAGE_LAST = "last"
    }
}