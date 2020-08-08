package com.example.core.singleton

open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i = instance
            if (i != null) {
                i
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}