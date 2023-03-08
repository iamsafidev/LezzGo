package com.confiz.lezzgo.domain.interactor

import android.util.Log
import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class UseCase<in T, R> {

    private val coroutineContext = Dispatchers.Default + Job()
    private val coroutineScope = CoroutineScope(coroutineContext)

    abstract suspend fun execute(arg: T): Either<Exception, R>

    operator fun invoke(arg: T, onResult: (Either<Exception, R>) -> Unit) {
        coroutineScope.launch {
            onResult(execute(arg).tap {
                Log.d(javaClass.simpleName, it.toString())
            })
        }
    }

    operator fun invoke(arg: T, onException: (Exception) -> Unit, onResult: (R) -> Unit = {}) {
        coroutineScope.launch {
            execute(arg).tap {
                Log.d(javaClass.simpleName, it.toString())
            }.tapLeft {
                Log.e(javaClass.simpleName, it.localizedMessage ?: it::class.java.simpleName)
            }.fold(onException, onResult)
        }
    }

    
}