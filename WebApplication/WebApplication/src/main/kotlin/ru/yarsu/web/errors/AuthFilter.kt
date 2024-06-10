package ru.yarsu.web.errors

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.cookie.Cookie
import org.http4k.lens.BiDiLens
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.Permisson
import ru.yarsu.domain.User
import ru.yarsu.operations.JwtTools
import ru.yarsu.storages.UserStorage
import ru.yarsu.storages.PermissonStorage

class AuthFilter(
    private val jwtTools: JwtTools,
    private val userLens: RequestContextLens<User?>,
    private val cookieLens: BiDiLens<Request, Cookie?>,
    private val userStorage: UserStorage,
    private val permissionLens: RequestContextLens<Permisson?>

) : Filter {
    override fun invoke(next: HttpHandler): HttpHandler {
        return { request: Request ->
            val authCookie = cookieLens(request)
            if (authCookie != null) {
                val token = authCookie.value
                val username = jwtTools.checkJwtToken(token)
                if (username != null) {
                    val user = userStorage.findUser(username)
                    if (user != null) {
                        userLens(user, request)

                        val permissionStorage = PermissonStorage()
                        val permissions = when (user.role) {
                            "admin" -> permissionStorage.admin
                            "cityAdmin" -> permissionStorage.adminOfCity
                            "citizen" -> permissionStorage.citizen
                            else -> permissionStorage.unauthorized
                        }
                        permissionLens(permissions, request)
                    }
                }
            }
            next(request)
        }
    }
}
