package com.bifos.password.config

object DatabaseContextHolder {

    private val context = ThreadLocal<String?>()

    fun getShardId(): String? {
        return context.get()
    }

    fun useShardDB(shardId: Int) {
        return context.set(shardId.toString())
    }
}