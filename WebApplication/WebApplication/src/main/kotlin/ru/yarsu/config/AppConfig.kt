package ru.yarsu.config

import org.http4k.cloudnative.env.Environment
import ru.yarsu.config.JwtConfig.Companion.readJwtConfig
import ru.yarsu.config.WebConfig.Companion.defaultEnv
import ru.yarsu.config.WebConfig.Companion.from

data class AppConfig(
    val webConfig: WebConfig,
    val jwtConfig: JwtConfig
)

val appEnv = Environment.fromResource("ru/yarsu/config/app.properties") overrides
        Environment.JVM_PROPERTIES overrides
        Environment.ENV overrides
        defaultEnv



fun readConfiguration(): AppConfig{
    val webConfig = from(appEnv)
    return AppConfig(webConfig, readJwtConfig(appEnv))
}