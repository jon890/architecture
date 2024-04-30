package com.bifos.password.config.database

object DatabaseContextHolder {

    private val context = ThreadLocal<String?>()

    fun getShardId(): String? {
        return context.get()
    }

    fun useShardDB(shardId: Int) {
        return context.set(shardId.toString())
    }
}