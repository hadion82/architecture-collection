/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.core.interactor

import com.example.core.functional.Result
import kotlinx.coroutines.*

abstract class UseCase<out Type, out Failure: Exception, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type, Failure>

    operator fun invoke(
        scope: CoroutineScope = GlobalScope,
        params: Params,
        onResult: (Result<Type, Failure>) -> Unit = {}) {
        val job = scope.async(Dispatchers.IO) { run(params) }
        scope.launch(Dispatchers.Main) {
            onResult(job.await())
        }
    }

    class None
}
