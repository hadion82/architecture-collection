package com.example.data.api

import javax.inject.Inject

internal class EncapsulationGithubApi @Inject constructor(
    private val encapsulation: EncapsulationGithubRetrofit
) {
    operator fun invoke(): GithubApi =
        encapsulation().create(GithubApi::class.java)
}