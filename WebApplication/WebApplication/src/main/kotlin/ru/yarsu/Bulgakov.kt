package ru.yarsu
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.core.ContentType
import org.http4k.core.Request
import org.http4k.core.RequestContexts
import org.http4k.core.cookie.Cookie
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.BiDiLens
import org.http4k.lens.Cookies
import org.http4k.lens.RequestContextKey
import org.http4k.lens.RequestContextLens
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.yarsu.config.readConfiguration
import ru.yarsu.domain.Permisson
import ru.yarsu.generators.DataGeneration
import ru.yarsu.operations.PasswordCheckerOperation
import ru.yarsu.web.router
import java.io.File
import ru.yarsu.domain.Proect
import ru.yarsu.storages.ProectsStorage
import ru.yarsu.generators.SaltGenerator
import ru.yarsu.storages.SaltStorage
import ru.yarsu.domain.User
import ru.yarsu.operations.JwtTools
import ru.yarsu.operations.UserRegistrationOperation
import ru.yarsu.storages.PermissonStorage
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.errors.AuthFilter
import ru.yarsu.web.errors.Filter
import ru.yarsu.web.templates.ContextAwarePebbleTemplates
import ru.yarsu.web.templates.ContextAwareViewRender
import kotlin.concurrent.thread


fun main() {
    val objectMapper = jacksonObjectMapper()
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.registerModule(JavaTimeModule())
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    val data = DataGeneration()

    if (!File("Data.json").exists()) {
        data.generation()
    }

    val listUsers: MutableList<User> = objectMapper.readValue(File("Users.json").readText())
    val listProects: MutableList<Proect> = objectMapper.readValue(File("Data.json").readText())
    val mapSalt: MutableMap<String, String> = objectMapper.readValue(File("Salt.json").readText())
    val list = ProectsStorage(listProects)
    val saltGenerator = SaltGenerator()
    val saltStorage = SaltStorage(mapSalt)
    val userStorage = UserStorage(listUsers)
    val userRegistration = UserRegistrationOperation(userStorage, saltStorage, saltGenerator)
    val userPasswordChecker = PasswordCheckerOperation(userStorage, saltStorage)

    val config = readConfiguration()
    val jwtConfig = config.jwtConfig
    val jwtTools = JwtTools(
        jwtConfig.secret,
        jwtConfig.organization,
        jwtConfig.tokenValidity
    )
    val contexts = RequestContexts()
    val cookieLens: BiDiLens<Request, Cookie?> = Cookies.optional("auth")
    val userLens = RequestContextKey.optional<User>(contexts, "user")
    val permissionLens: RequestContextLens<Permisson?> = RequestContextKey.optional(contexts, "permissions")

    val renderer = ContextAwarePebbleTemplates().HotReload("src/main/resources")

    val htmlView = ContextAwareViewRender.withContentType(renderer, ContentType.TEXT_HTML)
        .associateContextLens("user", userLens)
        .associateContextLens("permissions", permissionLens)


    val authFilter = AuthFilter(jwtTools, userLens, cookieLens,userStorage, permissionLens)

    val filter = Filter(htmlView)
    val appWithStaticResources = routes(
        static(ResourceLoader.Classpath("/ru/yarsu/public")),
        router(htmlView, list, userRegistration, userPasswordChecker, userStorage, permissionLens, userLens),
    )

    val server =  ServerFilters.InitialiseRequestContext(contexts).then(filter).then(authFilter).then(appWithStaticResources).asServer(Netty(config.webConfig.webPort)).start()





    println("Server started on http://localhost:" + server.port())
    println("Press enter to exit application.")
    val hook = thread(start = false) {
        val fileSpecialistsWrite = File("Data.json")
        val userWrite = File("Users.json")
        val stringSpecialists = objectMapper
            .writeValueAsString(list.proects)
        fileSpecialistsWrite
            .writeText(stringSpecialists, Charsets.UTF_8)
        val userString = objectMapper
            .writeValueAsString(userStorage.users)
        userWrite
            .writeText(userString, Charsets.UTF_8)
        saltStorage.saveToFile("Salt.json")
    }

    Runtime.getRuntime()
        .addShutdownHook(hook)
    readln()
    server.stop()
}
