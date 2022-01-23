package com.sumfi.playground.core.config.r2dbc

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableR2dbcRepositories("com.sumfi.playground.core.app")
@EntityScan("com.sumfi.playground.core.app")
@EnableTransactionManagement
class R2dbcConfig(
    private val masterConnectionFactory: ConnectionFactory,
    private val slaveConnectionFactory: ConnectionFactory,
): AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val routingConnectionFactory = SumfiConnectionRouterFactory()

        val factories = HashMap<String, ConnectionFactory>()
        factories[SumfiConnectionRouterFactory.MASTER] = masterConnectionFactory
        factories[SumfiConnectionRouterFactory.SLAVE] = slaveConnectionFactory

        routingConnectionFactory.setDefaultTargetConnectionFactory(masterConnectionFactory)
        routingConnectionFactory.setTargetConnectionFactories(factories)
        return routingConnectionFactory
    }

}
