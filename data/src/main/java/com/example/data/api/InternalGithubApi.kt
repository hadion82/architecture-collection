package com.example.data.api

import javax.inject.Inject

internal class InternalGithubApi @Inject constructor(
    private val retrofit: InternalGithubRetrofit
) {
    operator fun invoke(): GithubApi =
        retrofit().create(GithubApi::class.java)
}