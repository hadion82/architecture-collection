package com.example.data.repository.memory

import androidx.paging.PagingSource
import com.example.data.datasource.remote.UserRemoteDataSource
import com.example.data.entity.UserEntity
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.min

internal class UserMemoryPagingSource(
    private val remote: UserRemoteDataSource,
    private val query: String
): PagingSource<Int, UserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> = try {
        val response = remote.searchUser(
             query = query, page = params.key ?: START_PAGE
        )

        response.run {
            val data = body()
            val hasNext = headers()[HEADER_PAGE_KEY]?.contains(
                HEADER_PAGE_LAST
            )
            if(isSuccessful && data != null) {
                LoadResult.Page(
                    data = data.items,
                    prevKey = params.key?.let { min(it - 1,
                        START_PAGE
                    ) },
                    nextKey = if(hasNext == true) params.key?.let { it + 1 } else null
                )
            } else {
                LoadResult.Error(IOException("Value is empty"))
            }
        }

    } catch (e: IOException) {
        LoadResult.Error(e)
    } catch (e: HttpException) {
        LoadResult.Error(e)
    }

    companion object {
        const val START_PAGE = 1

        const val HEADER_PAGE_KEY = "link"
        const val HEADER_PAGE_LAST = "last"
    }
}