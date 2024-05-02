package com.bifos.password.repository.common

import com.bifos.password.entity.common.User
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.time.LocalDateTime

@Repository
class JdbcUserRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun insertBatch(users: List<User>) {
        val sql = """
            INSERT INTO User
            (user_id, password, createdAt, updatedAt)
            VALUES
            (?, ?, ?, ?)
            """.trimIndent()

        jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
            override fun setValues(ps: PreparedStatement, i: Int) {
                ps.setString(1, users[i].userId)
                ps.setString(2, users[i].password)
                ps.setObject(3, LocalDateTime.now())
                ps.setObject(4, LocalDateTime.now())
            }

            override fun getBatchSize(): Int {
                return users.size
            }
        })
    }
}