package ru.yarsu.operations
import ru.yarsu.generators.SaltGenerator
import ru.yarsu.storages.SaltStorage
import ru.yarsu.domain.User
import ru.yarsu.storages.UserStorage
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.HexFormat

class UserRegistrationOperation(
    val userStorage: UserStorage,
    private val saltStorage: SaltStorage,
    private val saltGenerator: SaltGenerator
) {
    fun registerUser(username: String, password: String, dateTime: LocalDateTime, role: String) {
        val salt = saltGenerator.generateSalt()
        saltStorage.addSalt(username, salt)

        val saltedPassword = password + salt
        val passwordHash = hashPassword(saltedPassword)

        val user = User(username, passwordHash, dateTime,role )
        userStorage.addUser(user)
    }


    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return HexFormat.of().formatHex(digest)
    }
}